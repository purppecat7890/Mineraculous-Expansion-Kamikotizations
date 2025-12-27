package dev.thomasglasser.mineraculouskamikotizations.world.entity.projectile;

import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class IceCharge extends AbstractHurtingProjectile implements ItemSupplier {
    public IceCharge(EntityType<? extends IceCharge> entityType, Level level) {
        super(entityType, level);
        this.accelerationPower = 0.0;
    }

    public IceCharge(EntityType<? extends IceCharge> entityType, Entity owner, double x, double y, double z, Level level) {
        super(entityType, x, y, z, level);
        this.setOwner(owner);
        this.accelerationPower = 0.0;
    }

    public IceCharge(Entity owner, double x, double y, double z, Level level) {
        this(MineraculousKamikotizationsEntityTypes.ICE_CHARGE.get(), owner, x, y, z, level);
    }

    public IceCharge(double x, double y, double z, Vec3 movement, Level level) {
        super(MineraculousKamikotizationsEntityTypes.ICE_CHARGE.get(), x, y, z, movement, level);
        this.accelerationPower = 0.0;
    }

    @Override
    protected AABB makeBoundingBox() {
        float f = this.getType().getDimensions().width() / 2.0F;
        float f1 = this.getType().getDimensions().height();
        return new AABB(
                this.position().x - (double) f,
                this.position().y - 0.15F,
                this.position().z - (double) f,
                this.position().x + (double) f,
                this.position().y - 0.15F + (double) f1,
                this.position().z + (double) f);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return !(entity instanceof IceCharge) && super.canCollideWith(entity);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        if (target instanceof IceCharge) {
            return false;
        } else {
            return target.getType() != EntityType.END_CRYSTAL && super.canHitEntity(target);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            LivingEntity livingentity = this.getOwner() instanceof LivingEntity livingentity1 ? livingentity1 : null;
            Entity entity = result.getEntity();
            if (livingentity != null) {
                livingentity.setLastHurtMob(entity);
            }

            DamageSource damagesource = this.damageSources().windCharge(this, livingentity);
            if (entity.hurt(damagesource, 1.0F) && entity instanceof LivingEntity livingentity2) {
                EnchantmentHelper.doPostAttackEffects((ServerLevel) this.level(), livingentity2, damagesource);
            }

            entity.setTicksFrozen(Integer.MAX_VALUE);
            entity.moveTo(entity.blockPosition().getBottomCenter());
            entity.setDeltaMovement(0, 0, 0);
            Set<BlockPos> inside = getInsidePos(entity);
            int blocksWide = Mth.ceil(entity.getBbWidth());
            int blocksHigh = Mth.ceil(entity.getBbHeight());
            for (int i = -blocksWide; i <= blocksWide; i++) {
                for (int j = -1; j <= blocksHigh; j++) {
                    for (int k = -blocksWide; k <= blocksWide; k++) {
                        BlockPos blockpos = entity.blockPosition().offset(i, j, k);
                        BlockState oldState = level().getBlockState(blockpos);
                        if (!inside.contains(blockpos) && oldState.canBeReplaced()) {
                            level().setBlockAndUpdate(blockpos, Blocks.ICE.defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    public static Set<BlockPos> getInsidePos(Entity target) {
        AABB aabb = target.getBoundingBox();
        BlockPos startPos = BlockPos.containing(aabb.minX + 1.0E-7, aabb.minY + 1.0E-7, aabb.minZ + 1.0E-7);
        BlockPos endPos = BlockPos.containing(aabb.maxX - 1.0E-7, aabb.maxY - 1.0E-7, aabb.maxZ - 1.0E-7);
        Set<BlockPos> inside = new ReferenceOpenHashSet<>();
        for (BlockPos pos : BlockPos.betweenClosed(startPos, endPos)) {
            inside.add(pos);
        }
        return inside;
    }

    @Override
    public void push(double x, double y, double z) {}

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            BlockPos pos = result.getBlockPos();
            for (int i = -1; i <= 1; i++) {
                for (int k = -1; k <= 1; k++) {
                    BlockPos blockpos = pos.offset(i, 0, k).above();
                    if (level().canSeeSky(blockpos))
                        blockpos = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos.offset(i, 0, k));
                    if (level().getBlockState(blockpos).canBeReplaced() && !level().getBlockState(blockpos.below()).is(Blocks.ICE)) {
                        level().setBlockAndUpdate(blockpos, Blocks.ICE.defaultBlockState());
                    }
                }
            }
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected float getInertia() {
        return 1.0F;
    }

    @Override
    protected float getLiquidInertia() {
        return this.getInertia();
    }

    @Nullable
    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.discard();
        } else {
            super.tick();
        }
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public ItemStack getItem() {
        return Items.ICE.getDefaultInstance();
    }
}
