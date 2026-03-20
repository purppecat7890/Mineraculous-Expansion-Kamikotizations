package dev.thomasglasser.mineraculouskamikotizations.core.registries;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.PigeonVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MineraculousKamikotizationsRegistries {
    /// Data-driven registry holding {@link PigeonVariant}s for use in {@link Pigeon} visuals.
    public static final ResourceKey<Registry<PigeonVariant>> PIGEON_VARIANT = create("pigeon_variant");

    private static <T> ResourceKey<Registry<T>> create(String name) {
        return ResourceKey.createRegistryKey(MineraculousKamikotizations.modLoc(name));
    }
}
