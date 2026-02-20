package dev.thomasglasser.mineraculouskamikotizations.world.entity;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.PigeonVariant;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.ApiStatus;

public class MineraculousKamikotizationsEntityDataSerializers {
    private static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, MineraculousKamikotizations.MOD_ID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<PigeonVariant>>> PIGEON_VARIANT = ENTITY_DATA_SERIALIZERS.register("pigeon_variant", () -> EntityDataSerializer.forValueType(PigeonVariant.STREAM_CODEC));

    @ApiStatus.Internal
    public static void init() {}
}
