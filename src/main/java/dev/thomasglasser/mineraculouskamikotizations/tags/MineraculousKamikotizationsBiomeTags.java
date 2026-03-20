package dev.thomasglasser.mineraculouskamikotizations.tags;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MineraculousKamikotizationsBiomeTags {
    public static final TagKey<Biome> SPAWNS_PIGEONS = create("spawns_pigeons");
    public static final TagKey<Biome> SPAWNS_COLD_VARIANT_PIGEONS = create("spawns_cold_variant_pigeons");
    public static final TagKey<Biome> SPAWNS_WARM_VARIANT_PIGEONS = create("spawns_warm_variant_pigeons");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, MineraculousKamikotizations.modLoc(name));
    }
}
