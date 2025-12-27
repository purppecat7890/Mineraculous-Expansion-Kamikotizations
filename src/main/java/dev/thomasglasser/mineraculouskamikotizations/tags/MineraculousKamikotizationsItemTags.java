package dev.thomasglasser.mineraculouskamikotizations.tags;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.tommylib.api.tags.TagUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MineraculousKamikotizationsItemTags {
    // Mod Specific
    public static final TagKey<Item> PARASOLS = create("parasols");
    public static final TagKey<Item> BUBBLE_WANDS = create("bubble_wands");

    // Common
    public static final TagKey<Item> UMBRELLA_TOOLS = createC("tools/umbrella");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, MineraculousKamikotizations.modLoc(name));
    }

    private static TagKey<Item> createC(String name) {
        return TagUtils.createConventional(Registries.ITEM, name);
    }
}
