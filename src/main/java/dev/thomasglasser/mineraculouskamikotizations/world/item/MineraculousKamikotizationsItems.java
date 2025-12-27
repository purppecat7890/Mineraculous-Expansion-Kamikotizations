package dev.thomasglasser.mineraculouskamikotizations.world.item;

import com.google.common.collect.ImmutableMap;
import dev.thomasglasser.mineraculous.api.core.component.MineraculousDataComponents;
import dev.thomasglasser.mineraculous.impl.world.item.component.Active;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.NeoForgeMod;

public class MineraculousKamikotizationsItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MineraculousKamikotizations.MOD_ID);

    private static final Supplier<Item.Properties> PARASOL_PROPERTIES = () -> new Item.Properties().stacksTo(1).component(MineraculousDataComponents.ACTIVE, Active.DEFAULT);

    // Kamikotizables
    public static final ImmutableMap<DyeColor, DeferredItem<Item>> PARASOLS = parasols();
    public static final ImmutableMap<DyeColor, DeferredItem<BubbleWandItem>> BUBBLE_WANDS = bubbleWands();

    // Kamikotization Tools
    public static final DeferredItem<WeatherControlParasolItem> WEATHER_CONTROL_PARASOL = ITEMS.register("weather_control_parasol", () -> new WeatherControlParasolItem(PARASOL_PROPERTIES.get()
            .attributes(ItemAttributeModifiers.builder()
                    .add(NeoForgeMod.CREATIVE_FLIGHT, new AttributeModifier(MineraculousKamikotizations.modLoc("parasol_flight"), 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ANY)
                    .build())));
    public static final DeferredItem<BubbleSwordItem> BUBBLE_SWORD = ITEMS.register("bubble_sword", () -> new BubbleSwordItem(new Item.Properties().stacksTo(1).attributes(ItemAttributeModifiers.builder().add(NeoForgeMod.CREATIVE_FLIGHT, BubbleSwordItem.FLIGHT_MODIFIER, EquipmentSlotGroup.ANY).build())));

    private static ImmutableMap<DyeColor, DeferredItem<Item>> parasols() {
        ImmutableMap.Builder<DyeColor, DeferredItem<Item>> parasols = new ImmutableMap.Builder<>();
        for (DyeColor color : DyeColor.values()) {
            parasols.put(color, ITEMS.register(color.getName() + "_parasol", () -> new Item(PARASOL_PROPERTIES.get())));
        }
        return parasols.build();
    }

    private static ImmutableMap<DyeColor, DeferredItem<BubbleWandItem>> bubbleWands() {
        ImmutableMap.Builder<DyeColor, DeferredItem<BubbleWandItem>> bubbleWands = new ImmutableMap.Builder<>();
        for (DyeColor color : DyeColor.values()) {
            bubbleWands.put(color, ITEMS.register(color.getName() + "_bubble_wand", () -> new BubbleWandItem(new Item.Properties().stacksTo(1).durability(100))));
        }
        return bubbleWands.build();
    }

    public static void init() {}
}
