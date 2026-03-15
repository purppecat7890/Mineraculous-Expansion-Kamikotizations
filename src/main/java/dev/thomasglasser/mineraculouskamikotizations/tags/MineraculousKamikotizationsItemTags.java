package dev.thomasglasser.mineraculouskamikotizations.tags;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.tommylib.api.tags.TagUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MineraculousKamikotizationsItemTags {
    // Mod Specific
    public static final TagKey<Item> PARASOLS = create("parasols");

    // Common
    public static final TagKey<Item> UMBRELLA_TOOLS = createC("tools/umbrella");

    /// Items that can feed {@link dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.Pigeon}s.
    public static final TagKey<Item> PIGEON_FOOD = create("pigeon_food");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, MineraculousKamikotizations.modLoc(name));
    }

    private static TagKey<Item> createC(String name) {
        return TagUtils.createConventional(Registries.ITEM, name);
    }
}
