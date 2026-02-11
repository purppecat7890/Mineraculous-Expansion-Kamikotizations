package dev.thomasglasser.mineraculouskamikotizations.network;

import com.google.common.collect.ImmutableList;
import dev.thomasglasser.mineraculous.api.MineraculousConstants;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.network.NeoForgeNetworkUtils;
import dev.thomasglasser.tommylib.api.network.PayloadInfo;
import java.util.List;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class MineraculousKamikotizationsPayloads {
    public static List<PayloadInfo<?>> PAYLOADS = ImmutableList.of(
            // Serverbound

            // Clientbound
            new PayloadInfo<>(ClientBoundOpenMiraculousLooksCustomizationScreenPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientBoundOpenMiraculousLooksCustomizationScreenPayload.CODEC));

    public static void onRegisterPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MineraculousConstants.MOD_ID);
        PAYLOADS.forEach((info) -> NeoForgeNetworkUtils.register(registrar, info));
    }
}
