package dev.thomasglasser.mineraculouskamikotizations.world.entity.animal;

import dev.thomasglasser.mineraculous.api.tags.MineraculousItemTags;
import dev.thomasglasser.mineraculouskamikotizations.core.registries.MineraculousKamikotizationsRegistries;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsBiomeTags;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsItemTags;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityDataSerializers;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Panic;
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

public class Pigeon extends AbstractFlockingBird implements SmartBrainOwner<Pigeon>, GeoEntity, FlyingAnimal {
    private static final EntityDataAccessor<Holder<PigeonVariant>> DATA_VARIANT = SynchedEntityData.defineId(Pigeon.class, MineraculousKamikotizationsEntityDataSerializers.PIGEON_VARIANT.get());
    private static final EntityDataAccessor<Boolean> DATA_IS_RESTING = SynchedEntityData.defineId(Pigeon.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation WALK = RawAnimation.begin().then("walk", Animation.LoopType.LOOP);
    private static final RawAnimation FLY = RawAnimation.begin().then("fly", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE = RawAnimation.begin().then("idle", Animation.LoopType.LOOP);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean isFlock = true;

    public Pigeon(EntityType<? extends Pigeon> entityType, Level level) {
        super(entityType, level);
        moveControl = new FlyingMoveControl(this, 20, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FLYING_SPEED, 1)
                .add(Attributes.GRAVITY, 0);
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        Registry<PigeonVariant> registry = this.registryAccess().registryOrThrow(MineraculousKamikotizationsRegistries.PIGEON_VARIANT);
        builder.define(DATA_VARIANT, registry.getHolder(PigeonVariants.TEMPERATE).or(registry::getAny).orElseThrow());
        builder.define(DATA_IS_RESTING, false);
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
        return stack.is(MineraculousKamikotizationsItemTags.PIGEON_FOOD);
    }

    @Override
    public boolean isMaxGroupSizeReached(int size) {
        return !this.isFlock;
    }

    static class PigeonGroupData extends FlockSpawnGroupData {
        final PigeonVariant variant;

        PigeonGroupData(Pigeon leader, PigeonVariant variant) {
            super(leader);
            this.variant = variant;
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        RandomSource randomsource = level.getRandom();
        Holder<PigeonVariant> pigeon$variant;
        if (spawnGroupData instanceof PigeonGroupData pigeon$pigeongroupdata) {
            pigeon$variant = new Holder.Direct<>(pigeon$pigeongroupdata.variant);
            this.startFollowing(((AbstractFlockingBird.FlockSpawnGroupData) spawnGroupData).leader);
        } else if ((double) randomsource.nextFloat() < 0.9) {
            pigeon$variant = PigeonVariants.getSpawnVariant(registryAccess(), this.level().getBiome(blockPosition()));
            spawnGroupData = new PigeonGroupData(this, pigeon$variant.value());
        } else {
            this.isFlock = false;
            pigeon$variant = PigeonVariants.getSpawnVariant(registryAccess(), this.level().getBiome(blockPosition()));

        }
        this.setVariant(this.random.nextBoolean() ? this.getVariant() : pigeon$variant);
        return spawnGroupData;
    }

    public static boolean checkPigeonSpawnRules(
            EntityType<Pigeon> pigeon, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getFluidState(pos.below()).isEmpty()
                && level.getBlockState(pos.above()).is(BlockTags.LEAVES)
                && (level.getBiome(pos).is(MineraculousKamikotizationsBiomeTags.SPAWNS_PIGEONS)
                        || Animal.checkAnimalSpawnRules(pigeon, level, spawnType, pos, random));
    }

    protected boolean shouldRest() {
        return this.getIdleTasks().getActivity() == Activity.IDLE && (this.getBlockStateOn().is(BlockTags.LEAVES) || this.getLightLevelDependentMagicValue() <= 7);
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
                if (!isResting() && level().getBlockState(blockPosition().below()).isSolid()) {
                    setResting(true);
                } else if (isResting()) {
                    setResting(false);
                }
            }
            if (isResting()) {
                Player player = level().getNearestPlayer(this, 12.0);
                if (player != null && player.isSprinting()) {
                    setResting(false);
                    goalSelector.setControlFlag(Goal.Flag.MOVE, true);
                }
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
                new ItemTemptingSensor<Pigeon>().temptedWith((pigeon, stack) -> stack.is(MineraculousKamikotizationsItemTags.PIGEON_FOOD)));
    }

    @Override
    public BrainActivityGroup<? extends Pigeon> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new Panic<>());
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
        return 1.1F;
    }

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
}
