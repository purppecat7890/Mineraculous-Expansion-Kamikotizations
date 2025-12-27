package dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking;

import dev.thomasglasser.mineraculous.api.world.level.storage.EntityReversionData;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class GriefTrackingWindCharge extends WindCharge {
    public GriefTrackingWindCharge(EntityType<? extends AbstractWindCharge> entityType, Level level) {
        super(entityType, level);
    }

    public GriefTrackingWindCharge(Player player, Level level, double x, double y, double z) {
        super(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_WIND_CHARGE.get(), level);
        setOwner(player);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
    }

    public GriefTrackingWindCharge(Level level, double x, double y, double z, Vec3 movement) {
        super(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_WIND_CHARGE.get(), level);
        setPos(x, y, z);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        this.setDeltaMovement(movement.normalize().scale(accelerationPower));
        this.hasImpulse = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (level() instanceof ServerLevel level && getOwner() != null) {
            EntityReversionData.get(level).putRevertible(getOwner().getUUID(), result.getEntity());
        }
        super.onHitEntity(result);
    }
}
