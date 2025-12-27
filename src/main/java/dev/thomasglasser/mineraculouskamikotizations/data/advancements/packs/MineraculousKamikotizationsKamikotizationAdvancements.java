package dev.thomasglasser.mineraculouskamikotizations.data.advancements.packs;

import dev.thomasglasser.mineraculous.api.advancements.critereon.TransformedKamikotizationTrigger;
import dev.thomasglasser.mineraculous.api.world.item.armor.MineraculousArmors;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.kamikotization.MineraculousKamikotizationsKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.item.MineraculousKamikotizationsItems;
import dev.thomasglasser.tommylib.api.data.advancements.ExtendedAdvancementGenerator;
import java.util.function.BiConsumer;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;

public class MineraculousKamikotizationsKamikotizationAdvancements extends ExtendedAdvancementGenerator {
    public MineraculousKamikotizationsKamikotizationAdvancements(BiConsumer<String, String> lang) {
        super(MineraculousKamikotizations.MOD_ID, "kamikotization", lang);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        AdvancementHolder root = builder("root", MineraculousArmors.KAMIKOTIZATION.head().toStack(), "Kamikotizations", "Canon kamikotizations")
                .background(MineraculousKamikotizations.modLoc("textures/gui/advancements/backgrounds/kamikotization.png"))
                .toast(false)
                .announce(false)
                .trigger("get_kamikotized", TransformedKamikotizationTrigger.TriggerInstance.transformed())
                .build();

        AdvancementHolder transformWeatherControl = builder("transform_weather_control", MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL.toStack(), "Grab an Umbrella", "Show the world who the best weather player really is...")
                .parent(root)
                .trigger("transform_weather_control", TransformedKamikotizationTrigger.TriggerInstance.transformed(MineraculousKamikotizationsKamikotizations.WEATHER_CONTROL))
                .build();
    }
}
