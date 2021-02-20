package me.Jonathon594.Mythria.Client.Renderer.Items;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public class HatchetItemRenderer extends MythriaToolItemRenderer {
    @Override
    protected void renderHead(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ItemStack toolHead, ItemRenderer itemRenderer, boolean enchanted) {
        matrixStackIn.translate(3 / 16.0, 3 / 16.0, 0);
        matrixStackIn.scale(1.0f, 1.0f, 1.5f);
        renderItemStack(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, toolHead, itemRenderer, enchanted);
    }
}
