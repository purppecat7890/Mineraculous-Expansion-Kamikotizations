package dev.thomasglasser.mineraculouskamikotizations.world.entity.animal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.mineraculouskamikotizations.core.registries.MineraculousKamikotizationsRegistries;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

/** Stores a texture to use when a {@link Pigeon} is spawned in the provided biomes. */
public final class PigeonVariant {
    public static final Codec<PigeonVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(variant -> variant.texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes", HolderSet.empty()).forGetter(PigeonVariant::biomes))
            .apply(instance, PigeonVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PigeonVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            PigeonVariant::texture,
            ByteBufCodecs.holderSet(Registries.BIOME),
            PigeonVariant::biomes,
            PigeonVariant::new);
    public static final Codec<Holder<PigeonVariant>> CODEC = RegistryFileCodec.create(MineraculousKamikotizationsRegistries.PIGEON_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PigeonVariant>> STREAM_CODEC = ByteBufCodecs.holder(
            MineraculousKamikotizationsRegistries.PIGEON_VARIANT, DIRECT_STREAM_CODEC);
    private final ResourceLocation texture;
    private final ResourceLocation textureFull;
    private final HolderSet<Biome> biomes;

    public PigeonVariant(ResourceLocation texture, HolderSet<Biome> biomes) {
        this.texture = texture;
        this.textureFull = fullTextureId(texture);
        this.biomes = biomes;
    }

    private static ResourceLocation fullTextureId(ResourceLocation texture) {
        return texture.withPath(path -> "textures/" + path + ".png");
    }

    @ApiStatus.Internal
    public ResourceLocation texture() {
        return this.textureFull;
    }

    @ApiStatus.Internal
    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            return other instanceof PigeonVariant pigeonVariant
                    && Objects.equals(this.texture, pigeonVariant.texture)
                    && Objects.equals(this.biomes, pigeonVariant.biomes);
        }
    }

    @Override
    public int hashCode() {
        int i = 1;
        i = 31 * i + this.texture.hashCode();
        return 31 * i + this.biomes.hashCode();
    }
}
