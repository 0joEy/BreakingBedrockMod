package net.henrycmoss.bb.entity.client;

import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.entity.custom.TntCannonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TntCannonModel extends GeoModel<TntCannonEntity> {
    @Override
    public ResourceLocation getModelResource(TntCannonEntity animatable) {
        return new ResourceLocation(Bb.MODID, "geo/geo.tnt_cannon.json");
    }

    @Override
    public ResourceLocation getTextureResource(TntCannonEntity animatable) {
        return new ResourceLocation(Bb.MODID, "textures/entity/tnt_cannon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TntCannonEntity animatable) {
        return new ResourceLocation(Bb.MODID, "animations/tnt_cannon.animation.json");
    }
}
