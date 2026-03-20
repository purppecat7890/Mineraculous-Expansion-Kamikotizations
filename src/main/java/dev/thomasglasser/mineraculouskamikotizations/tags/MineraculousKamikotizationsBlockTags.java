package dev.thomasglasser.mineraculouskamikotizations.tags;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MineraculousKamikotizationsBlockTags {
    /// Blocks which allow {@link dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.Pigeon}s to spawn on.
    public static final TagKey<Block> SPAWNS_PIGEON_BLOCKS = create("spawns_pigeon_blocks");
    /// Blocks which allow {@link dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.Pigeon}s to rest on.
    public static final TagKey<Block> PIGEON_REST_BLOCKS = create("pigeon_rest_blocks");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, MineraculousKamikotizations.modLoc(name));
    }
}
