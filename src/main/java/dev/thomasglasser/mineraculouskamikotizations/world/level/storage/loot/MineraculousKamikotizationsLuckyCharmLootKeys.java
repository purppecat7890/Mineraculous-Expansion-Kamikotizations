package dev.thomasglasser.mineraculouskamikotizations.world.level.storage.loot;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class MineraculousKamikotizationsLuckyCharmLootKeys {
    public static final ResourceKey<LootTable> WEATHER_CONTROL = kamikotization("weather_control");

    private static ResourceKey<LootTable> kamikotization(String name) {
        return create("kamikotization/" + name);
    }

    private static ResourceKey<LootTable> create(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, MineraculousKamikotizations.modLoc("gameplay/lucky_charm/" + name));
    }
}
