package dev.thomasglasser.mineraculouskamikotizations.data.datamaps;

import dev.thomasglasser.mineraculous.api.datamaps.LuckyCharms;
import dev.thomasglasser.mineraculous.api.datamaps.MineraculousDataMaps;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.kamikotization.MineraculousKamikotizationsKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.level.storage.loot.MineraculousKamikotizationsLuckyCharmLootKeys;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

public class MineraculousKamikotizationsDataMapProvider extends DataMapProvider {
    public MineraculousKamikotizationsDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(MineraculousDataMaps.KAMIKOTIZATION_LUCKY_CHARMS)
                .add(MineraculousKamikotizationsKamikotizations.WEATHER_CONTROL, new LuckyCharms(MineraculousKamikotizationsLuckyCharmLootKeys.WEATHER_CONTROL), false)
                .build();
    }
}
