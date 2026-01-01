package dev.thomasglasser.mineraculouskamikotizations.data.looks;

import dev.thomasglasser.mineraculous.api.data.look.LookProvider;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.kamikotization.MineraculousKamikotizationsKamikotizations;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class MineraculousKamikotizationsLookProvider extends LookProvider {
    public MineraculousKamikotizationsLookProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, MineraculousKamikotizations.MOD_ID, lookupProvider);
    }

    @Override
    protected void registerLooks(HolderLookup.Provider provider) {
        kamikotizationLookNoAnims(MineraculousKamikotizationsKamikotizations.CAT_MIRACULOUS_REPLICATION);
    }
}
