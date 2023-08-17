package net.henrycmoss.bb.entity.client;

import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.entity.custom.HatManEntity;
import net.henrycmoss.bb.entity.custom.TntCannonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HatManModel extends GeoModel<HatManEntity> {

    @Override
    public ResourceLocation getModelResource(HatManEntity animatable) {
        return new ResourceLocation(Bb.MODID, "geo/geo.hat_man.json");
    }

    @Override
    public ResourceLocation getTextureResource(HatManEntity animatable) {
        return new ResourceLocation(Bb.MODID, "textures/entity/hat_man.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HatManEntity animatable) {
        return new ResourceLocation(Bb.MODID, "animations/hat_man.animation.json");
    }
}
