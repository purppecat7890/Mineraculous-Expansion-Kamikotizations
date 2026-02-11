package dev.thomasglasser.mineraculouskamikotizations.data.looks;

import dev.thomasglasser.mineraculous.api.MineraculousConstants;
import dev.thomasglasser.mineraculous.api.client.look.asset.LookAssetTypes;
import dev.thomasglasser.mineraculous.api.core.look.LookUtils;
import dev.thomasglasser.mineraculous.api.core.look.context.LookContexts;
import dev.thomasglasser.mineraculous.api.core.look.metadata.LookMetadataTypes;
import dev.thomasglasser.mineraculous.api.core.registries.MineraculousRegistries;
import dev.thomasglasser.mineraculous.api.data.look.LookProvider;
import dev.thomasglasser.mineraculous.api.world.kamikotization.Kamikotization;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculous;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculouses;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.kamikotization.MineraculousKamikotizationsKamikotizations;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class MineraculousKamikotizationsLookProvider extends LookProvider {
    public MineraculousKamikotizationsLookProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, MineraculousKamikotizations.MOD_ID, lookupProvider);
    }

    @Override
    protected void registerLooks(HolderLookup.Provider provider) {
        HolderGetter<Miraculous> miraculouses = provider.lookupOrThrow(MineraculousRegistries.MIRACULOUS);
        withExistingMiraculousParentNoAnims(MineraculousKamikotizationsKamikotizations.CAT_MIRACULOUS_MIMICRY, miraculouses.getOrThrow(Miraculouses.CAT).getKey());
    }

    protected Builder withExistingMiraculousParent(ResourceKey<Kamikotization> kamikotization, ResourceKey<Miraculous> miraculous) {
        String id = miraculous.location().getPath();
        return look(LookUtils.getDefaultLookId(kamikotization).getPath())
                .metadata(LookMetadataTypes.VALID_KAMIKOTIZATIONS, ObjectOpenHashSet.of(kamikotization))
                .assets(LookContexts.KAMIKOTIZATION_SUIT, assets()
                        .add(LookAssetTypes.TEXTURE, mainModString("textures/entity/equipment/humanoid/miraculous/" + id + ".png"))
                        .add(LookAssetTypes.GECKOLIB_MODEL, mainModString("geo/item/armor/miraculous/" + id + ".geo.json"))
                        .add(LookAssetTypes.GECKOLIB_ANIMATIONS, mainModString("animations/item/armor/miraculous/" + id + ".animation.json")));
    }

    protected Builder withExistingMiraculousParentNoAnims(ResourceKey<Kamikotization> kamikotization, ResourceKey<Miraculous> miraculous) {
        return withExistingMiraculousParent(kamikotization, miraculous)
                .remove(LookContexts.KAMIKOTIZATION_SUIT, LookAssetTypes.GECKOLIB_ANIMATIONS);
    }

    /**
     * Creates a string with the main mod id and the provided path.
     *
     * @param path The path of the {@link ResourceLocation}
     * @return A string with the main mod id and the provided path
     */
    protected String mainModString(String path) {
        return MineraculousConstants.modLoc(path).toString();
    }
}
