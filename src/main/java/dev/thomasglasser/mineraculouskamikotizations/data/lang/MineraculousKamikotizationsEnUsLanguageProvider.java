package dev.thomasglasser.mineraculouskamikotizations.data.lang;

import dev.thomasglasser.mineraculous.impl.data.lang.MineraculousEnUsLanguageProvider;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.kamikotization.MineraculousKamikotizationsKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.item.MineraculousKamikotizationsCreativeModeTabs;
import dev.thomasglasser.mineraculouskamikotizations.world.item.MineraculousKamikotizationsItems;
import dev.thomasglasser.mineraculouskamikotizations.world.item.WeatherControlParasolItem;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;

public class MineraculousKamikotizationsEnUsLanguageProvider extends MineraculousEnUsLanguageProvider {
    public MineraculousKamikotizationsEnUsLanguageProvider(PackOutput output) {
        super(output, MineraculousKamikotizations.MOD_ID);
    }

    @Override
    protected void addTranslations() {
        addItems();
        addEntities();
        addTabs();
        addKamikotizations();
    }

    protected void addItems() {
        // Kamikotizables
        for (DyeColor color : DyeColor.values()) {
            add(MineraculousKamikotizationsItems.PARASOLS.get(color).get(), capitalize(color.getName()) + " Parasol");
        }

        // Kamikotization Tools
        add(MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL.get(), "Weather Control Parasol");
        add(WeatherControlParasolItem.Mode.ICE, "Ice");
        add(WeatherControlParasolItem.Mode.LIGHTNING, "Lightning");
        add(WeatherControlParasolItem.Mode.WEATHER, "Weather");
        add(WeatherControlParasolItem.Mode.WIND, "Wind");
    }

    protected void addEntities() {
        add(MineraculousKamikotizationsEntityTypes.ICE_CHARGE.get(), "Ice Charge");
        add(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_ICE_CHARGE.get(), "Ice Charge");
        add(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_LIGHTNING_BOLT.get(), "Lightning Bolt");
        add(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_WIND_CHARGE.get(), "Wind Charge");
    }

    protected void addTabs() {
        add(MineraculousKamikotizationsCreativeModeTabs.MINERACULOUS_KAMIKOTIZATIONS.get(), "Mineraculous Expansion: Kamikotizations");
    }

    protected void addKamikotizations() {
        add(MineraculousKamikotizationsKamikotizations.WEATHER_CONTROL, "Weather Control");
        add(MineraculousKamikotizationsKamikotizations.CAT_MIRACULOUS_REPLICATION, "Cat Miraculous Replication");
    }
}
