package dev.thomasglasser.mineraculouskamikotizations.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.Pigeon;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PigeonRenderer<T extends Pigeon> extends GeoEntityRenderer<T> {
    public PigeonRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(MineraculousKamikotizations.modLoc("pigeon")));
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return animatable.getVariant().value().texture();
    }
}
