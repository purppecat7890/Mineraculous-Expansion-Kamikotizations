package dev.thomasglasser.mineraculouskamikotizations.world.entity.animal;

import dev.thomasglasser.mineraculouskamikotizations.core.registries.MineraculousKamikotizationsRegistries;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityDataSerializers;
import java.util.Optional;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.navigation.SmoothFlyingPathNavigation;
import org.jetbrains.annotations.Nullable;

public class FlockingFlyingAnimal extends Animal implements FlyingAnimal {
    private static final EntityDataAccessor<Holder<PigeonVariant>> DATA_PIGEON_VARIANT = SynchedEntityData.defineId(FlockingFlyingAnimal.class, MineraculousKamikotizationsEntityDataSerializers.PIGEON_VARIANT.get());
    public boolean isFlock = true;

    @Nullable
    public FlockingFlyingAnimal leader;

    protected FlockingFlyingAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 2, false);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return otherParent.getBreedOffspring(level, this);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        Registry<PigeonVariant> registry = this.registryAccess().registryOrThrow(MineraculousKamikotizationsRegistries.PIGEON_VARIANT);
        builder.define(DATA_PIGEON_VARIANT, registry.getHolder(PigeonVariants.TEMPERATE).or(registry::getAny).orElseThrow());
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
    public boolean isFood(ItemStack stack) {
        return false;
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public FlockingFlyingAnimal startFollowing(FlockingFlyingAnimal leader) {
        this.leader = leader;
        return leader;
    }

    public FlockingFlyingAnimal isLeader() {
        return this.leader;
    }

    public Holder<PigeonVariant> getVariant() {
        return this.entityData.get(DATA_PIGEON_VARIANT);
    }

    public void setVariant(Holder<PigeonVariant> variant) {
        this.entityData.set(DATA_PIGEON_VARIANT, variant);
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothFlyingPathNavigation(this, level);
    }

    public static class FlockingSpawnGroupData extends AgeableMobGroupData implements SpawnGroupData {
        public final FlockingFlyingAnimal leader;

        public FlockingSpawnGroupData(boolean shouldSpawnBaby, FlockingFlyingAnimal leader) {
            super(shouldSpawnBaby);
            this.leader = leader;
        }
    }
}
