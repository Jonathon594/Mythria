package me.Jonathon594.Mythria.Client.Renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Quaternion;

public class QuaternionModelRenderer extends ModelRenderer {
    public Quaternion quaternion = Quaternion.ONE.copy();

    public QuaternionModelRenderer(int textureWidthIn, int textureHeightIn, int textureOffsetXIn, int textureOffsetYIn) {
        super(textureWidthIn, textureHeightIn, textureOffsetXIn, textureOffsetYIn);
    }

    @Override
    public void translateRotate(MatrixStack matrixStackIn) {
        matrixStackIn.translate(this.rotationPointX / 16.0, this.rotationPointY / 16.0, this.rotationPointZ / 16.0);
        matrixStackIn.rotate(quaternion);
    }
}
