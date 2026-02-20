package dev.thomasglasser.mineraculouskamikotizations.core;

import dev.thomasglasser.mineraculouskamikotizations.core.registries.MineraculousKamikotizationsRegistries;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.PigeonVariant;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class MineraculousKamikotizationsCoreEvents {
    public static void onNewDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MineraculousKamikotizationsRegistries.PIGEON_VARIANT, PigeonVariant.DIRECT_CODEC, PigeonVariant.DIRECT_CODEC);
    }
}
