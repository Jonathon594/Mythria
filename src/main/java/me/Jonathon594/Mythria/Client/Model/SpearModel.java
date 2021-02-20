package me.Jonathon594.Mythria.Client.Model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SpearModel extends Model {
    private final ModelRenderer modelRenderer = new ModelRenderer(32, 32, 0, 0);
    private final ModelSpearTip modelSpearTip = new ModelSpearTip(4, 0, -0.5f, 0.0f, -0.5f, 1.0f, 4.0f, 1.0f, 0.0f, 0.0f, 0.0f, false, 32, 32);

    public SpearModel() {
        super(RenderType::getEntitySolid);
        this.modelRenderer.addBox(-0.5F, -27.0F, -0.5F, 1.0F, 27.0F, 1.0F, 0.0F);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.push();
        this.modelSpearTip.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }
}
