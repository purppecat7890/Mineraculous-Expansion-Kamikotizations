package dev.thomasglasser.mineraculouskamikotizations.world.ability;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.mineraculous.api.world.ability.Ability;
import dev.thomasglasser.mineraculous.api.world.ability.AbilityData;
import dev.thomasglasser.mineraculous.api.world.ability.context.AbilityContext;
import dev.thomasglasser.mineraculous.api.world.ability.handler.AbilityHandler;
import dev.thomasglasser.mineraculous.api.world.entity.MineraculousEntityUtils;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculous;
import dev.thomasglasser.mineraculouskamikotizations.network.ClientBoundOpenMiraculousLooksCustomizationScreenPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public record ReplicateLooksAbility(Holder<Miraculous> miraculous) implements Ability {
    public static final MapCodec<ReplicateLooksAbility> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Miraculous.CODEC.fieldOf("miraculous").forGetter(ReplicateLooksAbility::miraculous)).apply(instance, ReplicateLooksAbility::new));

    @Override
    public State perform(AbilityData data, ServerLevel level, LivingEntity performer, AbilityHandler handler, @Nullable AbilityContext context) {
        return State.PASS;
    }

    @Override
    public void transform(AbilityData data, ServerLevel level, LivingEntity performer) {
        if (performer instanceof ServerPlayer serverPlayer) {
            TommyLibServices.NETWORK.sendToClient(new ClientBoundOpenMiraculousLooksCustomizationScreenPayload(miraculous), serverPlayer);
        }
    }

    @Override
    public void detransform(AbilityData data, ServerLevel level, LivingEntity performer) {
        if (performer instanceof ServerPlayer player) {
            MineraculousEntityUtils.refreshAndSyncDisplayName(player);
        }
    }

    @Override
    public MapCodec<? extends Ability> codec() {
        return MineraculousKamikotizationsAbilitySerializers.REPLICATE_LOOKS.get();
    }
}
