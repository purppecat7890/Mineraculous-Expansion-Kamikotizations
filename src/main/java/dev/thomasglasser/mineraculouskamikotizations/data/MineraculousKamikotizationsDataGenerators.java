package dev.thomasglasser.mineraculouskamikotizations.data;

import dev.thomasglasser.mineraculous.api.core.registries.MineraculousRegistries;
import dev.thomasglasser.mineraculous.api.world.ability.Abilities;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.data.advancements.MineraculousKamikotizationsAdvancementProvider;
import dev.thomasglasser.mineraculouskamikotizations.data.datamaps.MineraculousKamikotizationsDataMapProvider;
import dev.thomasglasser.mineraculouskamikotizations.data.lang.MineraculousKamikotizationsEnUsLanguageProvider;
import dev.thomasglasser.mineraculouskamikotizations.data.looks.MineraculousKamikotizationsLookProvider;
import dev.thomasglasser.mineraculouskamikotizations.data.loot.MineraculousKamikotizationsLootTables;
import dev.thomasglasser.mineraculouskamikotizations.data.models.MineraculousKamikotizationsItemModelProvider;
import dev.thomasglasser.mineraculouskamikotizations.data.modonomicons.MineraculousKamikotizationsBookProvider;
import dev.thomasglasser.mineraculouskamikotizations.data.recipes.MineraculousKamikotizationsRecipeProvider;
import dev.thomasglasser.mineraculouskamikotizations.data.tags.MineraculousKamikotizationsItemTagsProvider;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.kamikotization.MineraculousKamikotizationsKamikotizations;
import dev.thomasglasser.tommylib.api.data.DataGenerationUtils;
import dev.thomasglasser.tommylib.api.data.tags.EmptyBlockTagsProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class MineraculousKamikotizationsDataGenerators {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(MineraculousRegistries.ABILITY, Abilities::bootstrap)
            .add(MineraculousRegistries.KAMIKOTIZATION, MineraculousKamikotizationsKamikotizations::bootstrap);

    public static void onGatherData(GatherDataEvent event) {
        // Server
        event.createDatapackRegistryObjects(BUILDER);
        DataGenerationUtils.createRegistryDumpReport(event, MineraculousKamikotizations.MOD_ID);
        DataGenerationUtils.createBlockAndItemTags(event, EmptyBlockTagsProvider::new, MineraculousKamikotizationsItemTagsProvider::new);
        event.createProvider(MineraculousKamikotizationsDataMapProvider::new);
        event.createProvider(MineraculousKamikotizationsLootTables::new);
        event.createProvider(MineraculousKamikotizationsRecipeProvider::new);

        // Common
        DataGenerationUtils.createLangDependent(event, MineraculousKamikotizationsEnUsLanguageProvider::new, MineraculousKamikotizationsAdvancementProvider::new, MineraculousKamikotizationsBookProvider::new);

        // Client
        DataGenerationUtils.createProvider(event, MineraculousKamikotizationsItemModelProvider::new);
        DataGenerationUtils.createProvider(event, (packOutput, lookupProvider, existingFileHelper) -> new MineraculousKamikotizationsLookProvider(packOutput, lookupProvider));
    }
}
