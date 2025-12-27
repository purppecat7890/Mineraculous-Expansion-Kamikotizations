package dev.thomasglasser.mineraculouskamikotizations.data.modonomicons.mineraculous.wiki.kamikotizations;

import com.klikli_dev.modonomicon.api.datagen.AddToCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import dev.thomasglasser.mineraculous.impl.data.modonomicons.wiki.kamikotizations.KamikotizationsCategoryProvider;

public class AddToKamikotizationsCategoryProvider extends AddToCategoryProvider {
    public AddToKamikotizationsCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    public String targetCategoryId() {
        return KamikotizationsCategoryProvider.ID;
    }

    // Unused in Index Mode
    @Override
    protected String[] generateEntryMap() {
        return new String[0];
    }

    @Override
    protected void generateEntries() {
        add(new WeatherControlEntryProvider(this).generate());
    }
}
