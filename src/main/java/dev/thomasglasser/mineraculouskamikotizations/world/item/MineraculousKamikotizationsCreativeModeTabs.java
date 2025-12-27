package dev.thomasglasser.mineraculouskamikotizations.world.item;

import dev.thomasglasser.mineraculous.impl.world.item.MineraculousCreativeModeTabs;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;

public class MineraculousKamikotizationsCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MineraculousKamikotizations.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MINERACULOUS_KAMIKOTIZATIONS = TABS.register(MineraculousKamikotizations.MOD_ID, () -> TommyLibServices.CLIENT.tabBuilder()
            .title(Component.translatable(MineraculousKamikotizations.modLoc(MineraculousKamikotizations.MOD_ID).toLanguageKey("item_group")))
            .hideTitle()
            .icon(MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL::toStack)
            .type(CreativeModeTab.Type.SEARCH)
            .displayItems((parameters, output) -> {
                Set<ItemStack> set = ItemStackLinkedSet.createTypeAndComponentsSet();

                for (CreativeModeTab creativemodetab : parameters.holders().lookupOrThrow(Registries.CREATIVE_MODE_TAB).listElements().map(Holder::value).toList()) {
                    if (creativemodetab.getType() != CreativeModeTab.Type.SEARCH) {
                        for (ItemStack stack : creativemodetab.getSearchTabDisplayItems()) {
                            if (BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals(MineraculousKamikotizations.MOD_ID)) {
                                set.add(stack);
                            }
                        }
                    }
                }

                output.acceptAll(set);
            })
            .withTabsBefore(MineraculousCreativeModeTabs.MINERACULOUS.getKey()).build());

    public static void init() {}
}
