package me.Jonathon594.Mythria.Client.Renderer.Items;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.Jonathon594.Mythria.Capability.Tool.Tool;
import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Interface.IModularTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class MythriaToolItemRenderer extends ItemStackTileEntityRenderer {

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Item item = stack.getItem();
        if (!(item instanceof IModularTool)) return;
        IModularTool modularTool = (IModularTool) item;
        Tool tool = ToolProvider.getTool(stack);
        if (tool == null) return;
        ItemStack toolHead = new ItemStack(modularTool.getToolHeadItem(), 1);
        ItemStack toolHandle = tool.getInventory().getStackInSlot(0);
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        //Handle
        matrixStack.push();
        if (!toolHandle.isEmpty())
            renderHandle(matrixStack, buffer, combinedLight, combinedOverlay, toolHandle, itemRenderer);
        matrixStack.pop();

        //Head
        matrixStack.push();
        if (transformType != ItemCameraTransforms.TransformType.GUI) {
            matrixStack.scale(1.01f, 1.01f, 1.01f);
        }
        renderHead(matrixStack, buffer, combinedLight, combinedOverlay, toolHead, itemRenderer, stack.hasEffect());
        matrixStack.pop();
        matrixStack.pop();
    }

    protected void renderHandle(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay, ItemStack toolHandle, ItemRenderer itemRenderer) {
        itemRenderer.renderItem(toolHandle, ItemCameraTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStack, buffer);
    }

    protected abstract void renderHead(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ItemStack toolHead, ItemRenderer itemRenderer, boolean enchanted);

    protected void renderItemStack(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ItemStack stack, ItemRenderer itemRenderer, boolean enchanted) {
        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
        IBakedModel model = itemRenderer.getItemModelWithOverrides(stack, null, null);
        RenderType rendertype = RenderTypeLookup.func_239219_a_(stack, true);
        IVertexBuilder ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(bufferIn, rendertype, true, enchanted);
        itemRenderer.renderModel(model, stack, combinedLightIn, combinedOverlayIn, matrixStackIn, ivertexbuilder);
    }
}
