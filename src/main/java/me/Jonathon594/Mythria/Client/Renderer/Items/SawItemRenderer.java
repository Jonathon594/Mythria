package me.Jonathon594.Mythria.Client.Renderer.Items;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public class SawItemRenderer extends MythriaToolItemRenderer {
    @Override
    protected void renderHandle(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ItemStack toolHandle, ItemRenderer itemRenderer) {
        matrixStackIn.translate(3 / 16.0, -2 / 16.0, 0);
        matrixStackIn.scale(1, 1, 1.5f);
        super.renderHandle(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, toolHandle, itemRenderer);
    }

    @Override
    protected void renderHead(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ItemStack toolHead, ItemRenderer itemRenderer, boolean enchanted) {
        matrixStackIn.translate(-2.0 / 16.0, 3 / 16.0, 0);
        matrixStackIn.scale(1, 1, 0.75f);
        renderItemStack(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, toolHead, itemRenderer, enchanted);
    }
}
