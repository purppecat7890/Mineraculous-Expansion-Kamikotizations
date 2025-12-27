package dev.thomasglasser.mineraculouskamikotizations.data.modonomicons;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.data.modonomicons.mineraculous.wiki.AddToWikiBookSubProvider;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class MineraculousKamikotizationsBookProvider extends BookProvider {
    public MineraculousKamikotizationsBookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, BiConsumer<String, String> lang) {
        super(packOutput, registries, MineraculousKamikotizations.MOD_ID, List.of(
                new AddToWikiBookSubProvider(lang)));
    }
}
