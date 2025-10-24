package dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking;

import dev.thomasglasser.mineraculous.api.world.level.storage.AbilityReversionBlockData;
import dev.thomasglasser.mineraculous.api.world.level.storage.AbilityReversionEntityData;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

public class GriefTrackingLightningBolt extends LightningBolt {
    public GriefTrackingLightningBolt(EntityType<? extends LightningBolt> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        if (!visualOnly && level() instanceof ServerLevel level && getCause() != null) {
            List<Entity> hit = EntityRetrievalUtil.getEntities(
                    level(),
                    new AABB(this.getX() - 3.0, this.getY() - 3.0, this.getZ() - 3.0, this.getX() + 3.0, this.getY() + 6.0 + 3.0, this.getZ() + 3.0),
                    Entity::isAlive);
            for (Entity entity : hit)
                AbilityReversionEntityData.get(level).putRevertible(getCause().getUUID(), entity);
        }
        super.tick();
    }

    @Override
    protected void spawnFire(int extraIgnitions) {
        if (!this.visualOnly && level() instanceof ServerLevel level && getCause() != null && this.level().getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            Map<BlockPos, BlockState> alteredBlocks = new Reference2ReferenceOpenHashMap<>();
            BlockPos blockpos = this.blockPosition();
            BlockState oldState = this.level().getBlockState(blockpos);
            BlockState blockstate = BaseFireBlock.getState(this.level(), blockpos);
            if (oldState.isAir() && blockstate.canSurvive(this.level(), blockpos)) {
                this.level().setBlockAndUpdate(blockpos, blockstate);
                this.blocksSetOnFire++;
                alteredBlocks.put(blockpos, oldState);
            }

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (int i = 0; i < extraIgnitions; i++) {
                mutable.setWithOffset(blockpos, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
                oldState = this.level().getBlockState(mutable);
                blockstate = BaseFireBlock.getState(this.level(), mutable);
                if (oldState.isAir() && blockstate.canSurvive(this.level(), mutable)) {
                    this.level().setBlockAndUpdate(mutable, blockstate);
                    this.blocksSetOnFire++;
                    alteredBlocks.put(mutable, blockstate);
                }
            }
            AbilityReversionBlockData.get(level).putRevertible(getCause().getUUID(), level.dimension(), alteredBlocks);
        }
    }
}
