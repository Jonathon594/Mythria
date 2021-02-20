package me.Jonathon594.Mythria.Client.Renderer.TileEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Blocks.BlockGroundCover;
import me.Jonathon594.Mythria.TileEntity.GroundCoverTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3f;

public class GroundCoverTileEntityRenderer extends TileEntityRenderer<GroundCoverTileEntity> {
    private final ItemRenderer itemRenderer;

    public GroundCoverTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(GroundCoverTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockState = tileEntityIn.getBlockState();
        BlockGroundCover block = (BlockGroundCover) blockState.getBlock();
        Item item = block.getDisplayItem();
        if (item == null) item = Items.AIR;
        int rotation = blockState.get(BlockGroundCover.ROTATION);

        matrixStackIn.push();
        matrixStackIn.translate(0.5D, 0.025, 0.5D);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90f));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(360f / (float) rotation));

        matrixStackIn.scale(0.75F, 0.75F, 0.75F);

        itemRenderer.renderItem(new ItemStack(item, 1), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
        matrixStackIn.pop();
    }
}
