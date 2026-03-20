package dev.thomasglasser.mineraculouskamikotizations.data.tags;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.tags.MineraculousKamikotizationsBlockTags;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedBlockTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MineraculousKamikotizationsBlockTagsProvider extends ExtendedBlockTagsProvider {
    public MineraculousKamikotizationsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MineraculousKamikotizations.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addSpawnBlocks();
    }

    private void addSpawnBlocks() {
        tag(MineraculousKamikotizationsBlockTags.SPAWNS_PIGEON_BLOCKS)
                .addTag(BlockTags.LEAVES)
                .add(Blocks.SNOW)
                .add(Blocks.ANDESITE)
                .add(Blocks.GRASS_BLOCK);

        tag(MineraculousKamikotizationsBlockTags.PIGEON_REST_BLOCKS)
                .addTag(MineraculousKamikotizationsBlockTags.SPAWNS_PIGEON_BLOCKS)
                .addTag(BlockTags.WOOL_CARPETS)
                .add(Blocks.ANDESITE);
    }
}
