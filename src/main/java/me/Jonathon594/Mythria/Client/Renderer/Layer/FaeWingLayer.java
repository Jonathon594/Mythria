package me.Jonathon594.Mythria.Client.Renderer.Layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Client.Model.CharacterModel;
import me.Jonathon594.Mythria.Client.Model.FaeWingModel;
import me.Jonathon594.Mythria.Client.Renderer.CharacterRenderer;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class FaeWingLayer extends LayerRenderer<LivingEntity, CharacterModel<LivingEntity>> {
    private final CharacterRenderer renderer;
    private FaeWingModel wingModel;

    public FaeWingLayer(CharacterRenderer renderer) {
        super(renderer);
        this.renderer = renderer;
        wingModel = new FaeWingModel(this.renderer);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entityIn, float limbSwing,
                       float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!shouldRenderWings(entityIn)) return;
        matrixStackIn.rotate(Vector3f.YP.rotation(renderer.getEntityModel().bipedBody.rotateAngleY));

        matrixStackIn.translate(0, entityIn.isCrouching() ? 3.2 / 16.0 : 0, 0);
        matrixStackIn.rotate(Vector3f.XP.rotation(renderer.getEntityModel().bipedBody.rotateAngleX));
        matrixStackIn.translate(0, 0, 0.125D);
        matrixStackIn.push();
        this.getEntityModel().copyModelAttributesTo(this.wingModel);
        this.wingModel.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.wingModel.getRenderType(getWingTexture(entityIn)), false, false);
        wingModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0.9f);
        matrixStackIn.pop();
    }

    private boolean shouldRenderWings(LivingEntity entityIn) {
        return !getWingTextureName(entityIn).isEmpty();
    }

    private String getWingTextureName(LivingEntity entityIn) {
        return MythriaPlayerProvider.getMythriaPlayer(entityIn).getSkinPart(SkinPart.Type.WINGS);
    }

    private ResourceLocation getWingTexture(LivingEntity entity) {
        return new ResourceLocation(getWingTextureName(entity));
    }
}
