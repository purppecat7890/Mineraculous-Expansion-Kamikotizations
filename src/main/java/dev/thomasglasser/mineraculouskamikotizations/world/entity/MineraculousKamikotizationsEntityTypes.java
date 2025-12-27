package dev.thomasglasser.mineraculouskamikotizations.world.entity;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking.GriefTrackingIceCharge;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking.GriefTrackingLightningBolt;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking.GriefTrackingWindCharge;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.projectile.IceCharge;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class MineraculousKamikotizationsEntityTypes {
    private static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(MineraculousKamikotizations.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<IceCharge>> ICE_CHARGE = ENTITY_TYPES.register("ice_charge", IceCharge::new, MobCategory.MISC, builder -> builder
            .sized(0.3125F, 0.3125F));
    public static final DeferredHolder<EntityType<?>, EntityType<BubblePrison>> BUBBLE_PRISON = ENTITY_TYPES.register("bubble_prison", BubblePrison::new, MobCategory.MISC, builder -> builder
            .sized(4F, 4F));

    // Grief Tracking
    public static final DeferredHolder<EntityType<?>, EntityType<GriefTrackingIceCharge>> GRIEF_TRACKING_ICE_CHARGE = ENTITY_TYPES.register("grief_tracking_ice_charge", GriefTrackingIceCharge::new, MobCategory.MISC, builder -> builder
            .sized(0.3125F, 0.3125F));
    public static final DeferredHolder<EntityType<?>, EntityType<GriefTrackingLightningBolt>> GRIEF_TRACKING_LIGHTNING_BOLT = ENTITY_TYPES.register("grief_tracking_lightning_bolt", GriefTrackingLightningBolt::new, MobCategory.MISC, builder -> builder
            .noSave()
            .sized(0.0F, 0.0F));
    public static final DeferredHolder<EntityType<?>, EntityType<GriefTrackingWindCharge>> GRIEF_TRACKING_WIND_CHARGE = ENTITY_TYPES.register("grief_tracking_wind_charge", GriefTrackingWindCharge::new, MobCategory.MISC, builder -> builder
            .sized(0.3125F, 0.3125F));

    public static void init() {}
}
