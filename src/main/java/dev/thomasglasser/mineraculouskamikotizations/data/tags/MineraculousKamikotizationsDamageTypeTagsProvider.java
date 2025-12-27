package dev.thomasglasser.mineraculouskamikotizations.data.tags;

import dev.thomasglasser.mineraculous.api.world.damagesource.MineraculousDamageTypes;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsDamageTypeTags;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MineraculousKamikotizationsDamageTypeTagsProvider extends ExtendedTagsProvider<DamageType> {
    public MineraculousKamikotizationsDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, MineraculousKamikotizations.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MineraculousKamikotizationsDamageTypeTags.BREAKS_BUBBLE_PRISONS)
                .addOptional(MineraculousDamageTypes.CATACLYSM.location());
    }
}
