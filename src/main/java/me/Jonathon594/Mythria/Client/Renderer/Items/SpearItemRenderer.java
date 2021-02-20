package me.Jonathon594.Mythria.Client.Renderer.Items;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.Jonathon594.Mythria.Client.Model.SpearModel;
import me.Jonathon594.Mythria.Items.SpearItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

public class SpearItemRenderer extends ItemStackTileEntityRenderer {
    private final SpearModel spearModel = new SpearModel();

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (stack.getItem() instanceof SpearItem) {
            IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(buffer, this.spearModel
                    .getRenderType(((SpearItem) stack.getItem()).getThrownEntityTexture()), false, stack.hasEffect());
            matrixStack.push();
            this.spearModel.render(matrixStack, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}
