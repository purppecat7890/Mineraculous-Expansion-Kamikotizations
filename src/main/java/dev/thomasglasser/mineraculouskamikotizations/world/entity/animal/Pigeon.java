package dev.thomasglasser.mineraculouskamikotizations.world.entity.animal;

import dev.thomasglasser.mineraculous.api.tags.MineraculousItemTags;
import dev.thomasglasser.mineraculouskamikotizations.core.registries.MineraculousKamikotizationsRegistries;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityDataSerializers;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowTemptation;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomFlyingTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.navigation.SmoothFlyingPathNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.ItemTemptingSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Pigeon extends Animal implements SmartBrainOwner<Pigeon>, GeoEntity, FlyingAnimal {
    private static final EntityDataAccessor<Holder<PigeonVariant>> DATA_VARIANT = SynchedEntityData.defineId(Pigeon.class, MineraculousKamikotizationsEntityDataSerializers.PIGEON_VARIANT.get());
    private static final EntityDataAccessor<Boolean> DATA_IS_RESTING = SynchedEntityData.defineId(Pigeon.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation WALK = RawAnimation.begin().then("walk", Animation.LoopType.LOOP);
    private static final RawAnimation FLY = RawAnimation.begin().then("fly", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE = RawAnimation.begin().then("idle", Animation.LoopType.LOOP);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean isFlock = true;

    public Pigeon(EntityType<? extends Pigeon> entityType, Level level) {
        super(entityType, level);
        moveControl = new FlyingMoveControl(this, 2, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FLYING_SPEED, 1)
                .add(Attributes.GRAVITY, 0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        Registry<PigeonVariant> registry = this.registryAccess().registryOrThrow(MineraculousKamikotizationsRegistries.PIGEON_VARIANT);
        builder.define(DATA_VARIANT, registry.getHolder(PigeonVariants.TEMPERATE).or(registry::getAny).orElseThrow());
        builder.define(DATA_IS_RESTING, false);
    }

    public Holder<PigeonVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(Holder<PigeonVariant> variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public boolean isResting() {
        return entityData.get(DATA_IS_RESTING);
    }

    public void setResting(boolean resting) {
        entityData.set(DATA_IS_RESTING, resting);
    }

    @Override
    public boolean isFlying() {
        return !isResting();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothFlyingPathNavigation(this, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(MineraculousItemTags.CHEESES_FOODS);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        Pigeon baby = MineraculousKamikotizationsEntityTypes.PIGEON.get().create(level);
        if (baby != null && otherParent instanceof Pigeon partner) {
            baby.setVariant(this.random.nextBoolean() ? this.getVariant() : partner.getVariant());
        }
        return baby;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setVariant(PigeonVariants.getSpawnVariant(this.registryAccess(), level.getBiome(this.blockPosition())));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    protected boolean shouldRest() {
        return this.onGround();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBrain(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            if (shouldRest()) {
                if (!isResting() && level().getBlockState(blockPosition().below()).isSolid())
                    setResting(true);
            } else if (isResting()) {
                setResting(false);
            }
        }
        setNoGravity(!isResting());
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends Pigeon>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new ItemTemptingSensor<Pigeon>().temptedWith((pigeon, stack) -> stack.is(MineraculousItemTags.CHEESES_FOODS)));
    }

    @Override
    public BrainActivityGroup<? extends Pigeon> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<Pigeon>().whenStopping(this::onMoveToWalkTargetStopping));
    }

    protected void onMoveToWalkTargetStopping(Pigeon pigeon) {}

    protected boolean hasReachedTarget(Entity entity, WalkTarget target) {
        return target != null && target.getTarget().currentBlockPosition().distManhattan(entity.blockPosition()) <= target.getCloseEnoughDist();
    }

    @Override
    public BrainActivityGroup<? extends Pigeon> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new FirstApplicableBehaviour<Pigeon>(
                                new BreedWithPartner<>(),
                                new FollowTemptation<>(),
                                new SetRandomFlyingTarget<>()).startCondition(pigeon -> !pigeon.shouldRest()),
                        new SetRandomWalkTarget<>().startCondition(pigeon -> !isResting() && !level().getBlockState(pigeon.blockPosition().below()).isSolid())));
    }

    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {}

    @Override
    protected void pushEntities() {}

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 0, state -> {
            if (isResting() && onGround() && getSpeed() == 0.0F) {
                return state.setAndContinue(IDLE);
            } else if (!isResting() && onGround() && !(getSpeed() == 0.0F)) {
                return state.setAndContinue(WALK);
            }
            return state.setAndContinue(FLY);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.getVariant().unwrapKey().ifPresent(key -> compound.putString("variant", key.location().toString()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString("variant")))
                .map(loc -> ResourceKey.create(MineraculousKamikotizationsRegistries.PIGEON_VARIANT, loc))
                .flatMap(key -> this.registryAccess().registryOrThrow(MineraculousKamikotizationsRegistries.PIGEON_VARIANT).getHolder(key))
                .ifPresent(this::setVariant);
    }

    public Holder<PigeonVariant> getVariant(Pigeon pigeon) {
        return pigeon.getVariant();
    }
}
