package dev.thomasglasser.mineraculouskamikotizations.client;

import dev.thomasglasser.mineraculous.api.core.look.context.LookContextSets;
import dev.thomasglasser.mineraculous.api.core.look.metadata.LookMetadataTypes;
import dev.thomasglasser.mineraculous.api.world.attachment.MineraculousAttachmentTypes;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculous;
import dev.thomasglasser.mineraculous.impl.network.ServerboundSetMiraculousLookDataPayload;
import dev.thomasglasser.mineraculouskamikotizations.client.gui.screens.look.MimicLookCustomizationScreen;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;

public class MineraculousKamikotizationsClientUtils {
    public static void openMiraculousLooksCustomizationScreen(Holder<Miraculous> selected) {
        Minecraft.getInstance().setScreen(new MimicLookCustomizationScreen<>(LookContextSets.MIRACULOUS, LookMetadataTypes.VALID_MIRACULOUSES, selected, player -> player.getData(MineraculousAttachmentTypes.MIRACULOUSES).get(selected).lookData(),
                (player, lookData) -> player.getData(MineraculousAttachmentTypes.MIRACULOUSES).get(selected).withLookData(lookData).save(selected, player),
                (player, lookData) -> TommyLibServices.NETWORK.sendToServer(new ServerboundSetMiraculousLookDataPayload(selected, lookData))));
    }
}
