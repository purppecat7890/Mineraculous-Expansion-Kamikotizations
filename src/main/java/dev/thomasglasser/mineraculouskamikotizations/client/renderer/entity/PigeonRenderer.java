package dev.thomasglasser.mineraculouskamikotizations.client.renderer.entity;

import dev.thomasglasser.mineraculouskamikotizations.MineraculousKamikotizations;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.Pigeon;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PigeonRenderer<T extends Pigeon> extends GeoEntityRenderer<T> {
    public PigeonRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(MineraculousKamikotizations.modLoc("pigeon")));
        withScale(1F);
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return animatable.getVariant().value().texture();
    }

}
