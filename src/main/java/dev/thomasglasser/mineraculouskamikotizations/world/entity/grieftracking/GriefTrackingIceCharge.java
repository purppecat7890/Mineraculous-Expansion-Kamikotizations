package dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking;

import dev.thomasglasser.mineraculous.api.world.level.storage.BlockReversionData;
import dev.thomasglasser.mineraculous.api.world.level.storage.EntityReversionData;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.projectile.IceCharge;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class GriefTrackingIceCharge extends IceCharge {
    public GriefTrackingIceCharge(EntityType<? extends IceCharge> entityType, Level level) {
        super(entityType, level);
    }

    public GriefTrackingIceCharge(EntityType<? extends IceCharge> entityType, Entity owner, double x, double y, double z, Level level) {
        super(entityType, owner, x, y, z, level);
    }

    public GriefTrackingIceCharge(Entity owner, double x, double y, double z, Level level) {
        super(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_ICE_CHARGE.get(), owner, x, y, z, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (level() instanceof ServerLevel level && getOwner() != null) {
            Entity entity = result.getEntity();
            UUID cause = getOwner().getUUID();
            EntityReversionData.get(level).putRevertible(cause, entity);
            Set<BlockPos> inside = getInsidePos(entity);
            int blocksWide = Mth.ceil(entity.getBbWidth());
            int blocksHigh = Mth.ceil(entity.getBbHeight());
            BlockReversionData blockReversionData = BlockReversionData.get(level);
            ResourceKey<Level> dimension = level.dimension();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (int i = -blocksWide; i <= blocksWide; i++) {
                for (int j = -1; j <= blocksHigh; j++) {
                    for (int k = -blocksWide; k <= blocksWide; k++) {
                        pos.setWithOffset(entity.blockPosition(), i, j, k);
                        BlockState state = level().getBlockState(pos);
                        if (!inside.contains(pos) && state.canBeReplaced()) {
                            blockReversionData.putRevertible(cause, dimension, pos, state);
                        }
                    }
                }
            }
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (level() instanceof ServerLevel level && getOwner() != null) {
            BlockReversionData blockReversionData = BlockReversionData.get(level);
            UUID cause = getOwner().getUUID();
            ResourceKey<Level> dimension = level().dimension();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (int i = -1; i <= 1; i++) {
                for (int k = -1; k <= 1; k++) {
                    pos.setWithOffset(result.getBlockPos(), i, 1, k);
                    if (level().canSeeSky(pos))
                        pos.setY(level().getHeight(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ()));
                    BlockState state = level().getBlockState(pos);
                    if (state.canBeReplaced() && !level().getBlockState(pos.below()).is(Blocks.ICE)) {
                        blockReversionData.putRevertible(cause, dimension, pos, state);
                    }
                }
            }
        }
        super.onHitBlock(result);
    }
}
