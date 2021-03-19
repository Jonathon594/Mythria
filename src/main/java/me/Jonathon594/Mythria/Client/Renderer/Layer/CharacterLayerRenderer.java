package me.Jonathon594.Mythria.Client.Renderer.Layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.Jonathon594.Mythria.Client.Model.CharacterModel;
import me.Jonathon594.Mythria.Client.Renderer.CharacterRenderer;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class CharacterLayerRenderer extends LayerRenderer<LivingEntity, CharacterModel<LivingEntity>> {

    private final SkinPart.Type eyes;

    public CharacterLayerRenderer(CharacterRenderer characterRenderer, SkinPart.Type skinPart) {
        super(characterRenderer);
        this.eyes = skinPart;
    }

    public ResourceLocation getTextureLocation(LivingEntity entity) {
        return CharacterRenderer.getCharacterLayeredTexture(entity, eyes);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation resourcelocation = getTextureLocation(entitylivingbaseIn);
        if (resourcelocation != null && !entitylivingbaseIn.isInvisible()) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(resourcelocation));
            this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
