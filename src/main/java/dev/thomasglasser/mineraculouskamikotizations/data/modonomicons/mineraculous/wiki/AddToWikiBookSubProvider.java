package dev.thomasglasser.mineraculouskamikotizations.data.modonomicons.mineraculous.wiki;

import com.klikli_dev.modonomicon.api.datagen.AddToBookSubProvider;
import dev.thomasglasser.mineraculous.api.MineraculousConstants;
import dev.thomasglasser.mineraculous.impl.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.data.modonomicons.mineraculous.wiki.kamikotizations.AddToKamikotizationsCategoryProvider;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;

public class AddToWikiBookSubProvider extends AddToBookSubProvider {
    public AddToWikiBookSubProvider(BiConsumer<String, String> defaultLang) {
        super(MineraculousConstants.modLoc(WikiBookSubProvider.ID), defaultLang);
    }

    // Unused in Index Mode
    @Override
    protected void registerDefaultMacros() {}

    @Override
    protected void generateCategories() {
        add(new AddToKamikotizationsCategoryProvider(this).generate());
    }

    public static ResourceLocation wikiTexture(String path) {
        return MineraculousKamikotizations.modLoc("textures/modonomicon/wiki/" + path);
    }
}
