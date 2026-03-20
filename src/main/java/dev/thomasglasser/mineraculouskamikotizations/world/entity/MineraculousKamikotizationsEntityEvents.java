package dev.thomasglasser.mineraculouskamikotizations.world.entity;

import dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.Pigeon;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class MineraculousKamikotizationsEntityEvents {
    /// Registration
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(MineraculousKamikotizationsEntityTypes.PIGEON.get(), Pigeon.createAttributes().build());
    }

    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(MineraculousKamikotizationsEntityTypes.PIGEON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pigeon::checkPigeonSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
