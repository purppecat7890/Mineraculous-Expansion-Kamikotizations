package dev.thomasglasser.mineraculouskamikotizations.network;

import dev.thomasglasser.mineraculous.api.MineraculousConstants;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculous;
import dev.thomasglasser.mineraculouskamikotizations.client.MineraculousKamikotizationsClientUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientBoundOpenMiraculousLooksCustomizationScreenPayload(Holder<Miraculous> miraculous) implements ExtendedPacketPayload {
    public static final Type<ClientBoundOpenMiraculousLooksCustomizationScreenPayload> TYPE = new Type<>(MineraculousConstants.modLoc("clientbound_open_looks_customization_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientBoundOpenMiraculousLooksCustomizationScreenPayload> CODEC = StreamCodec.composite(
            Miraculous.STREAM_CODEC, ClientBoundOpenMiraculousLooksCustomizationScreenPayload::miraculous,
            ClientBoundOpenMiraculousLooksCustomizationScreenPayload::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        MineraculousKamikotizationsClientUtils.openMiraculousLooksCustomizationScreen(miraculous);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
