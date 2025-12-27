package dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking;

import dev.thomasglasser.mineraculous.api.world.level.storage.BlockReversionData;
import dev.thomasglasser.mineraculous.api.world.level.storage.EntityReversionData;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class GriefTrackingLightningBolt extends LightningBolt {
    public GriefTrackingLightningBolt(EntityType<? extends LightningBolt> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        if (!visualOnly && level() instanceof ServerLevel level && getCause() != null) {
            List<Entity> hit = level
                    .getEntities(
                            this,
                            new AABB(this.getX() - 3.0, this.getY() - 3.0, this.getZ() - 3.0, this.getX() + 3.0, this.getY() + 6.0 + 3.0, this.getZ() + 3.0),
                            Entity::isAlive);
            EntityReversionData entityReversionData = EntityReversionData.get(level);
            UUID cause = getCause().getUUID();
            for (Entity entity : hit) {
                entityReversionData.putRevertible(cause, entity);
            }
        }
        super.tick();
    }

    @Override
    protected void spawnFire(int extraIgnitions) {
        if (!this.visualOnly && level() instanceof ServerLevel level && getCause() != null && this.level().getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            BlockReversionData blockReversionData = BlockReversionData.get(level);
            UUID cause = getCause().getUUID();
            ResourceKey<Level> dimension = level.dimension();
            BlockPos blockpos = this.blockPosition();
            BlockState oldState = this.level().getBlockState(blockpos);
            BlockState blockstate = BaseFireBlock.getState(this.level(), blockpos);
            if (oldState.isAir() && blockstate.canSurvive(this.level(), blockpos)) {
                this.level().setBlockAndUpdate(blockpos, blockstate);
                this.blocksSetOnFire++;
                blockReversionData.putRevertible(cause, dimension, blockpos, oldState);
            }

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (int i = 0; i < extraIgnitions; i++) {
                mutable.setWithOffset(blockpos, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
                oldState = this.level().getBlockState(mutable);
                blockstate = BaseFireBlock.getState(this.level(), mutable);
                if (oldState.isAir() && blockstate.canSurvive(this.level(), mutable)) {
                    this.level().setBlockAndUpdate(mutable, blockstate);
                    this.blocksSetOnFire++;
                    blockReversionData.putRevertible(cause, dimension, mutable, oldState);
                }
            }
        }
    }
}
