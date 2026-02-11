package dev.thomasglasser.mineraculouskamikotizations.world.ability;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.mineraculous.api.core.registries.MineraculousRegistries;
import dev.thomasglasser.mineraculous.api.world.ability.Ability;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

public class MineraculousKamikotizationsAbilitySerializers {
    private static final DeferredRegister<MapCodec<? extends Ability>> ABILITIES = DeferredRegister.create(MineraculousRegistries.ABILITY_SERIALIZER, MineraculousKamikotizations.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Ability>, MapCodec<ExtraToolAbility>> EXTRA_TOOL = ABILITIES.register("extra_tool", () -> ExtraToolAbility.CODEC);
    public static final DeferredHolder<MapCodec<? extends Ability>, MapCodec<ReplicateLooksAbility>> REPLICATE_LOOKS = ABILITIES.register("replicate_looks", () -> ReplicateLooksAbility.CODEC);

    @ApiStatus.Internal
    public static void init() {}
}
