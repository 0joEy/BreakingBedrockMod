package net.henrycmoss.bb.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.entity.custom.TntCannonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TntCannonRenderer extends GeoEntityRenderer<TntCannonEntity> {
    public TntCannonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TntCannonModel());
    }

    @Override
    public ResourceLocation getTextureLocation(TntCannonEntity animatable) {
        return new ResourceLocation(Bb.MODID, "textures/entity/tnt_cannon.png");
    }

    @Override
    public void render(TntCannonEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
