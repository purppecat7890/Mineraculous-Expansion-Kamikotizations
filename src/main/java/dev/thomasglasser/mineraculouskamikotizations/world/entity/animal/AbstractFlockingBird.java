package dev.thomasglasser.mineraculouskamikotizations.world.entity.animal;

import dev.thomasglasser.mineraculouskamikotizations.core.registries.MineraculousKamikotizationsRegistries;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityDataSerializers;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.ai.goal.FollowFlockPigeonLeaderGoal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AbstractFlockingBird extends Animal implements FlyingAnimal {
    private static final EntityDataAccessor<Holder<PigeonVariant>> DATA_VARIANT = SynchedEntityData.defineId(AbstractFlockingBird.class, MineraculousKamikotizationsEntityDataSerializers.PIGEON_VARIANT.get());

    @Nullable
    private AbstractFlockingBird leader;
    private int schoolSize = 1;

    public AbstractFlockingBird(EntityType<? extends AbstractFlockingBird> entityType, Level level) {
        super(entityType, level);
        moveControl = new FlyingMoveControl(this, 2, false);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        Registry<PigeonVariant> registry = this.registryAccess().registryOrThrow(MineraculousKamikotizationsRegistries.PIGEON_VARIANT);
        builder.define(DATA_VARIANT, registry.getHolder(PigeonVariants.TEMPERATE).or(registry::getAny).orElseThrow());
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

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FollowFlockPigeonLeaderGoal(this));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    protected boolean canRandomFly() {
        return !this.isFollower();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public AbstractFlockingBird startFollowing(AbstractFlockingBird leader) {
        this.leader = leader;
        leader.addFollower();
        return leader;
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        this.schoolSize++;
    }

    private void removeFollower() {
        this.schoolSize--;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends Pigeon> list = this.level()
                    .getEntitiesOfClass((Class<? extends Pigeon>) this.getClass(), this.getBoundingBox().inflate(8.0, 8.0, 8.0));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }
    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 61.0;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            this.getNavigation().moveTo(this.leader, 1.0);
        }
    }

    public void addFollowers(Stream<? extends AbstractFlockingBird> followers) {
        followers.limit((long) (this.getMaxSchoolSize() - this.schoolSize))
                .filter(bird -> bird != this)
                .forEach(flockingBird -> flockingBird.startFollowing(this));
    }

    public Holder<PigeonVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(Holder<PigeonVariant> variant) {
        this.entityData.set(DATA_VARIANT, variant);
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
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    public static class FlockSpawnGroupData implements SpawnGroupData {
        public final AbstractFlockingBird leader;

        public FlockSpawnGroupData(AbstractFlockingBird leader) {
            this.leader = leader;
        }
    }
}
