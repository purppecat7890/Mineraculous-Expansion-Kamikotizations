package dev.thomasglasser.mineraculouskamikotizations.data.tags;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsBiomeTags;
import dev.thomasglasser.tommylib.api.tags.ConventionalBiomeTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MineraculousKamikotizationsBiomeTagsProvider extends BiomeTagsProvider {
    public MineraculousKamikotizationsBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, MineraculousKamikotizations.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MineraculousKamikotizationsBiomeTags.SPAWNS_COLD_VARIANT_PIGEONS)
                .addTag(ConventionalBiomeTags.IS_SNOWY_PLAINS)
                .addTag(ConventionalBiomeTags.IS_TAIGA);

        tag(MineraculousKamikotizationsBiomeTags.SPAWNS_PIGEONS)
                .addTag(MineraculousKamikotizationsBiomeTags.SPAWNS_COLD_VARIANT_PIGEONS)
                .addTag(ConventionalBiomeTags.IS_PLAINS)
                .addTag(ConventionalBiomeTags.IS_FOREST)
                .addTag(ConventionalBiomeTags.IS_FLORAL);
    }
}
