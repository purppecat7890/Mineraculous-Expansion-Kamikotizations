package dev.thomasglasser.mineraculouskamikotizations.data.tags;

import dev.thomasglasser.mineraculous.api.MineraculousConstants;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.data.curios.MineraculousKamikotizationsCuriosProvider;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsItemTags;
import dev.thomasglasser.mineraculouskamikotizations.world.item.MineraculousKamikotizationsItems;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedItemTagsProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
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

        tag(MineraculousKamikotizationsItemTags.BUBBLE_WANDS)
                .add(MineraculousKamikotizationsItems.BUBBLE_WANDS.values().toArray(new DeferredItem[0]));

        // Common
        tag(MineraculousKamikotizationsItemTags.UMBRELLA_TOOLS)
                .addTag(MineraculousKamikotizationsItemTags.PARASOLS)
                .add(MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL.get());

        tag(ConventionalItemTags.TOOLS)
                .addTag(MineraculousKamikotizationsItemTags.UMBRELLA_TOOLS);

        curios(MineraculousKamikotizationsCuriosProvider.SLOT_BACK,
                MineraculousKamikotizationsItems.BUBBLE_SWORD.get());
    }

    protected void curios(String slot, Item... items) {
        IntrinsicTagAppender<Item> curios = tag(TagKey.create(Registries.ITEM, MineraculousConstants.Dependencies.CURIOS.modLoc(slot)));

        for (Item item : items) {
            curios.add(item);
        }
    }
}
