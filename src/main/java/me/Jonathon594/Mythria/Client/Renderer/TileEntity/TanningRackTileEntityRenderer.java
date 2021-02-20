package me.Jonathon594.Mythria.Client.Renderer.TileEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.TileEntity.TanningRackTileEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3f;

public class TanningRackTileEntityRenderer extends TileEntityRenderer<TanningRackTileEntity> {
    public TanningRackTileEntityRenderer(TileEntityRendererDispatcher p_i226007_1_) {
        super(p_i226007_1_);
    }

    public void render(TanningRackTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = tileEntityIn.getBlockState().get(CampfireBlock.FACING);
        NonNullList<ItemStack> nonnulllist = tileEntityIn.getInventory();

        ItemStack itemstack = nonnulllist.get(0);
        if (itemstack != ItemStack.EMPTY) {
            matrixStackIn.push();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            Direction direction1 = Direction.byHorizontalIndex((0 + direction.getHorizontalIndex()) % 4);
            float f = -direction1.getHorizontalAngle();
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
            matrixStackIn.translate(0D, 0D, -0.07D);
            Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }

    }
}
