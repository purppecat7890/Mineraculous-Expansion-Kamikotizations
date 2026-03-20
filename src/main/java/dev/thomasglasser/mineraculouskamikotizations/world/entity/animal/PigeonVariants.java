package dev.thomasglasser.mineraculouskamikotizations.world.entity.animal;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.core.registries.MineraculousKamikotizationsRegistries;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

public class PigeonVariants {
    /// The default.
    public static final ResourceKey<PigeonVariant> TEMPERATE = create("temperate");
    public static final ResourceKey<PigeonVariant> COLD = create("cold");
    public static final ResourceKey<PigeonVariant> WARM = create("warm");

    private static ResourceKey<PigeonVariant> create(String name) {
        return ResourceKey.create(MineraculousKamikotizationsRegistries.PIGEON_VARIANT, MineraculousKamikotizations.modLoc(name));
    }

    @ApiStatus.Internal
    public static Holder<PigeonVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> biome) {
        Registry<PigeonVariant> registry = registryAccess.registryOrThrow(MineraculousKamikotizationsRegistries.PIGEON_VARIANT);
        return registry.holders()
                .filter(variant -> variant.value().biomes().contains(biome))
                .findFirst()
                .or(() -> registry.getHolder(TEMPERATE))
                .or(registry::getAny)
                .orElseThrow();
    }

    @ApiStatus.Internal
    public static void bootstrap(BootstrapContext<PigeonVariant> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        register(context, TEMPERATE, HolderSet.empty());
        register(context, COLD, biomes.getOrThrow(MineraculousKamikotizationsBiomeTags.SPAWNS_COLD_VARIANT_PIGEONS));
        register(context, WARM, biomes.getOrThrow(MineraculousKamikotizationsBiomeTags.SPAWNS_WARM_VARIANT_PIGEONS));
    }

    /**
     * Registers the provided variant to the provided context for the provided biomes.
     * 
     * @param context The context to register the variant to
     * @param variant The variant to register
     * @param biomes  The biomes the variant should spawn in
     */
    public static void register(BootstrapContext<PigeonVariant> context, ResourceKey<PigeonVariant> variant, HolderSet<Biome> biomes) {
        context.register(variant, new PigeonVariant(variant.location().withPath(path -> "entity/pigeon/" + path), biomes));
    }
}
