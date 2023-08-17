package net.henrycmoss.bb.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.entity.custom.HatManEntity;
import net.henrycmoss.bb.entity.custom.TntCannonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HatManRenderer extends GeoEntityRenderer<HatManEntity> {

    public HatManRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HatManModel());
    }

    @Override
    public ResourceLocation getTextureLocation(HatManEntity animatable) {
        return new ResourceLocation(Bb.MODID, "textures/entity/hat_man.png");
    }

    @Override
    public void render(HatManEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(10f, 2f, 2f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
