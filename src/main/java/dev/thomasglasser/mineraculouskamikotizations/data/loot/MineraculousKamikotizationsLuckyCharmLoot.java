package dev.thomasglasser.mineraculouskamikotizations.data.loot;

import dev.thomasglasser.mineraculous.api.world.level.storage.loot.providers.number.PowerLevelMultiplierGenerator;
import dev.thomasglasser.mineraculouskamikotizations.world.level.storage.loot.MineraculousKamikotizationsLuckyCharmLootKeys;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public record MineraculousKamikotizationsLuckyCharmLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(
                MineraculousKamikotizationsLuckyCharmLootKeys.WEATHER_CONTROL,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.WIND_CHARGE)
                                                        .apply(SetItemCountFunction.setCount(PowerLevelMultiplierGenerator.apply(UniformGenerator.between(1, 4)))))));
    }
}
