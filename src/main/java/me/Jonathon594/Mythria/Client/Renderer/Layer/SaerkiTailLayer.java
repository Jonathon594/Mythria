package me.Jonathon594.Mythria.Client.Renderer.Layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Client.Model.CharacterModel;
import me.Jonathon594.Mythria.Client.Model.SaerkiTailModel;
import me.Jonathon594.Mythria.Client.Renderer.CharacterRenderer;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class SaerkiTailLayer extends LayerRenderer<LivingEntity, CharacterModel<LivingEntity>> {
    private final CharacterRenderer renderer;
    private final SaerkiTailModel tailModel;

    public SaerkiTailLayer(CharacterRenderer renderer) {
        super(renderer);
        this.renderer = renderer;
        tailModel = new SaerkiTailModel();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entityIn, float limbSwing,
                       float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!shouldRenderTail(entityIn)) return;
        matrixStackIn.push();
        this.getEntityModel().copyModelAttributesTo(this.tailModel);
        this.tailModel.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        matrixStackIn.translate(0, 0.75, 0);
        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.tailModel.getRenderType(getTailTexture(entityIn)), false, false);

        matrixStackIn.rotate(Vector3f.YP.rotation(renderer.getEntityModel().bipedBody.rotateAngleY));
        tailModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0.9f);
        matrixStackIn.pop();
    }

    private ResourceLocation getTailTexture(LivingEntity entityIn) {
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(entityIn);
        SkinPart skinPart = mythriaPlayer.getSkinPart(SkinPart.Type.SAERKI_TAIL);
        if (skinPart == null) return null;
        return skinPart.getTextureLocation(mythriaPlayer.getGender());
    }

    private boolean shouldRenderTail(LivingEntity entityIn) {
        return getTailTexture(entityIn) != null;
    }
}
