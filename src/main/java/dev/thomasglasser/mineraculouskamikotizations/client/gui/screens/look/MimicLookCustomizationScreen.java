package dev.thomasglasser.mineraculouskamikotizations.client.gui.screens.look;

import com.google.common.collect.ImmutableSet;
import dev.thomasglasser.mineraculous.api.client.gui.screens.look.LookCustomizationScreen;
import dev.thomasglasser.mineraculous.api.core.look.LookData;
import dev.thomasglasser.mineraculous.api.core.look.context.LookContext;
import dev.thomasglasser.mineraculous.api.core.look.metadata.LookMetadataType;
import dev.thomasglasser.mineraculous.api.world.attachment.MineraculousAttachmentTypes;
import dev.thomasglasser.mineraculous.api.world.kamikotization.KamikotizationData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class MimicLookCustomizationScreen<T> extends LookCustomizationScreen<T> {
    public MimicLookCustomizationScreen(ImmutableSet<Holder<LookContext>> contextSet, LookMetadataType<Set<ResourceKey<T>>> metadataType, Holder<T> selected, Function<Player, LookData> lookDataGetter, BiConsumer<Player, LookData> lookDataSetter, BiConsumer<Player, LookData> onApply) {
        super(contextSet, metadataType, selected, lookDataGetter, lookDataSetter, onApply);
    }

    public MimicLookCustomizationScreen(ImmutableSet<Holder<LookContext>> contextSet, Supplier<LookMetadataType<Set<ResourceKey<T>>>> metadataType, Holder<T> selected, Function<Player, LookData> lookDataGetter, BiConsumer<Player, LookData> lookDataSetter, BiConsumer<Player, LookData> onApply) {
        this(contextSet, metadataType.get(), selected, lookDataGetter, lookDataSetter, onApply);
    }

    @Override
    protected void apply() {
        LocalPlayer player = (LocalPlayer) ClientUtils.getLocalPlayer();
        if (player == null)
            throw new IllegalStateException("Look Customization Screen has no player");
        Optional<KamikotizationData> kamikotizationData = player.getData(MineraculousAttachmentTypes.KAMIKOTIZATION);
        Optional<KamikotizationData> newKamikotizationData = Optional.of(new KamikotizationData(kamikotizationData.get().kamikotization(), kamikotizationData.get().kamikoData(), new LookData("test".describeConstable(), kamikotizationData.get().lookData().looks())));
        player.setData(MineraculousAttachmentTypes.KAMIKOTIZATION, newKamikotizationData);
    }
}
