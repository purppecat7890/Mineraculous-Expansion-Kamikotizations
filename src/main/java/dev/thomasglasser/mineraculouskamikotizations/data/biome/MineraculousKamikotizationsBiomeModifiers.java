package dev.thomasglasser.mineraculouskamikotizations.data.biome;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsBiomeTags;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MineraculousKamikotizationsBiomeModifiers {
    private static ResourceKey<BiomeModifier> create(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MineraculousKamikotizations.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        addSpawns(context, "spawn_pigeons", biomes.getOrThrow(MineraculousKamikotizationsBiomeTags.SPAWNS_PIGEONS), MineraculousKamikotizationsEntityTypes.PIGEON, 10, 5, 15);
    }

    private static void addSpawns(BootstrapContext<BiomeModifier> context, String name, HolderSet<Biome> biomes, Holder<EntityType<?>> type, int weight, int minCount, int maxCount) {
        context.register(create(name), BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(biomes, new MobSpawnSettings.SpawnerData(type.value(), weight, minCount, maxCount)));
    }
}
