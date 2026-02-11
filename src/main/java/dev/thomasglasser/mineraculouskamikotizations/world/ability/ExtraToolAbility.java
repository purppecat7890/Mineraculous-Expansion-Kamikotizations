package dev.thomasglasser.mineraculouskamikotizations.world.ability;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.mineraculous.api.core.component.MineraculousDataComponents;
import dev.thomasglasser.mineraculous.api.world.ability.Ability;
import dev.thomasglasser.mineraculous.api.world.ability.AbilityData;
import dev.thomasglasser.mineraculous.api.world.ability.context.AbilityContext;
import dev.thomasglasser.mineraculous.api.world.ability.handler.AbilityHandler;
import dev.thomasglasser.mineraculous.api.world.entity.MineraculousEntityUtils;
import dev.thomasglasser.mineraculous.api.world.entity.curios.CuriosUtils;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculous;
import dev.thomasglasser.mineraculous.impl.world.level.storage.ToolIdData;
import dev.thomasglasser.tommylib.api.world.entity.EntityUtils;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record ExtraToolAbility(Holder<Miraculous> miraculous) implements Ability {
    public static final MapCodec<ExtraToolAbility> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Miraculous.CODEC.fieldOf("miraculous").forGetter(ExtraToolAbility::miraculous)).apply(instance, ExtraToolAbility::new));

    @Override
    public State perform(AbilityData data, ServerLevel level, LivingEntity performer, AbilityHandler handler, @Nullable AbilityContext context) {
        return State.PASS;
    }

    @Override
    public void transform(AbilityData data, ServerLevel level, LivingEntity performer) {
        if (performer instanceof Player player) {
            Miraculous value = miraculous.value();
            ItemStack tool = value.tool();
            UUID miraculousId = performer.getUUID();
            int id = ToolIdData.get(level).incrementToolId(miraculousId);
            tool.set(MineraculousDataComponents.OWNER, player.getUUID());
            tool.set(MineraculousDataComponents.MIRACULOUS, miraculous);
            tool.set(MineraculousDataComponents.MIRACULOUS_ID, miraculousId);
            tool.set(MineraculousDataComponents.TOOL_ID, id);
            value.toolSlot().ifPresentOrElse(slot -> {
                boolean added = CuriosUtils.setStackInFirstValidSlot(player, slot, tool);
                if (!added) {
                    EntityUtils.addToInventoryOrDrop(player, tool);
                }
            }, () -> EntityUtils.addToInventoryOrDrop(player, tool));
        }
    }

    @Override
    public void detransform(AbilityData data, ServerLevel level, LivingEntity performer) {
        UUID miraculousId = performer.getUUID();
        for (ItemStack i : MineraculousEntityUtils.getInventoryAndCurios(performer)) {
            UUID stackId = i.get(MineraculousDataComponents.MIRACULOUS_ID);
            if (i.has(MineraculousDataComponents.TOOL_ID) && stackId != null && stackId.equals(miraculousId)) {
                i.setCount(0);
            }
        }
    }

    @Override
    public MapCodec<? extends Ability> codec() {
        return MineraculousKamikotizationsAbilitySerializers.EXTRA_TOOL.get();
    }
}
