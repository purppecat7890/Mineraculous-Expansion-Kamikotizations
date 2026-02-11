package dev.thomasglasser.mineraculouskamikotizations.data.tags;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsItemTags;
import dev.thomasglasser.mineraculouskamikotizations.world.item.MineraculousKamikotizationsItems;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedItemTagsProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MineraculousKamikotizationsItemTagsProvider extends ExtendedItemTagsProvider {
    public MineraculousKamikotizationsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, future, blockTagsProvider, MineraculousKamikotizations.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Mod Specific
        tag(MineraculousKamikotizationsItemTags.PARASOLS)
                .add(MineraculousKamikotizationsItems.PARASOLS.values().toArray(new DeferredItem[0]));

        // Common
        tag(MineraculousKamikotizationsItemTags.UMBRELLA_TOOLS)
                .addTag(MineraculousKamikotizationsItemTags.PARASOLS)
                .add(MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL.get());

        tag(ConventionalItemTags.TOOLS)
                .addTag(MineraculousKamikotizationsItemTags.UMBRELLA_TOOLS);
    }
}
