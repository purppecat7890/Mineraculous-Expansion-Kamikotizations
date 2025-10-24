package dev.thomasglasser.mineraculouskamikotizations.core.component;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.item.WeatherControlParasolItem;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class MineraculousKamikotizationsDataComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MineraculousKamikotizations.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WeatherControlParasolItem.Mode>> WEATHER_CONTROL_PARASOL_MODE = DATA_COMPONENTS.register("weather_control_parasol_mode", WeatherControlParasolItem.Mode.STREAM_CODEC, WeatherControlParasolItem.Mode.CODEC, true);

    public static void init() {}
}
