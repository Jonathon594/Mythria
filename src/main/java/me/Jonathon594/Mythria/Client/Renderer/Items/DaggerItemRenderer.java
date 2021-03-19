package me.Jonathon594.Mythria.Client.Renderer.Items;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public class DaggerItemRenderer extends MythriaToolItemRenderer {
    @Override
    protected void renderHandle(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ItemStack toolHandle, ItemRenderer itemRenderer) {
        matrixStackIn.translate(-4.0 / 16.0, -4.0 / 16.0, 0);
        super.renderHandle(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, toolHandle, itemRenderer);
    }

    @Override
    protected void renderHead(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ItemStack toolHead, ItemRenderer itemRenderer, boolean enchanted) {
        matrixStackIn.scale(1, 1, 1.25f);
        renderItemStack(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, toolHead, itemRenderer, enchanted);
    }
}
