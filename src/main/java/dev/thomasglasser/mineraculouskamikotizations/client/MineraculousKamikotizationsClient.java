package dev.thomasglasser.mineraculouskamikotizations.client;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = MineraculousKamikotizations.MOD_ID, dist = Dist.CLIENT)
public class MineraculousKamikotizationsClient {
    public MineraculousKamikotizationsClient(IEventBus bus) {
        bus.addListener(MineraculousKamikotizationsClientEvents::onFMLClientSetup);
        bus.addListener(MineraculousKamikotizationsClientEvents::onBuildCreativeModeTabContents);
        bus.addListener(MineraculousKamikotizationsClientEvents::onRegisterRenderers);
        bus.addListener(MineraculousKamikotizationsClientEvents::onRegisterParticleProviders);
    }
}
