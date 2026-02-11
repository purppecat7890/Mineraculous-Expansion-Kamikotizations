package dev.thomasglasser.mineraculouskamikotizations.world.ability;

import dev.thomasglasser.mineraculous.api.core.registries.MineraculousRegistries;
import dev.thomasglasser.mineraculous.api.world.ability.Abilities;
import dev.thomasglasser.mineraculous.api.world.ability.Ability;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculous;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculouses;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

public class MineraculousKamikotizationsAbilities {
    public static final ResourceKey<Ability> MIMIC_CAT_MIRACULOUS_TOOL = create("mimic_cat_miraculous_tool");
    public static final ResourceKey<Ability> MIMIC_CAT_MIRACULOUS_LOOKS = create("mimic_cat_miraculous_looks");

    private static ResourceKey<Ability> create(String name) {
        return ResourceKey.create(MineraculousRegistries.ABILITY, MineraculousKamikotizations.modLoc(name));
    }

    @ApiStatus.Internal
    public static void bootstrap(BootstrapContext<Ability> context) {
        HolderGetter<Miraculous> miraculouses = context.lookup(MineraculousRegistries.MIRACULOUS);
        Abilities.bootstrap(context);
        context.register(MIMIC_CAT_MIRACULOUS_TOOL, new ExtraToolAbility(miraculouses.getOrThrow(Miraculouses.CAT)));
        context.register(MIMIC_CAT_MIRACULOUS_LOOKS, new ReplicateLooksAbility(miraculouses.getOrThrow(Miraculouses.CAT)));
    }
}
