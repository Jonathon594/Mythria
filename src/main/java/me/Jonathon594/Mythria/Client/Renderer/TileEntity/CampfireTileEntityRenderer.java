package me.Jonathon594.Mythria.Client.Renderer.TileEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.Jonathon594.Mythria.Client.Model.BurningLogOverlay;
import me.Jonathon594.Mythria.Client.Model.LogModel;
import me.Jonathon594.Mythria.TileEntity.CampfireTileEntity;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
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
import net.minecraftforge.client.ForgeHooksClient;

public class CampfireTileEntityRenderer extends TileEntityRenderer<CampfireTileEntity> {
    private final LogModel logModel = new LogModel();
    private final BurningLogOverlay burningOverlayTop = new BurningLogOverlay(true);
    private final BurningLogOverlay burningOverlayBottom = new BurningLogOverlay(false);

    public CampfireTileEntityRenderer(TileEntityRendererDispatcher p_i226007_1_) {
        super(p_i226007_1_);
    }

    public void render(CampfireTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = tileEntityIn.getBlockState().get(CampfireBlock.FACING);
        NonNullList<ItemStack> cookingInventory = tileEntityIn.getCookingInventory();

        for (int i = 0; i < cookingInventory.size(); ++i) {
            ItemStack itemstack = cookingInventory.get(i);
            if (itemstack != ItemStack.EMPTY) {
                matrixStackIn.push();
                matrixStackIn.translate(0.5D, 0.44921875D, 0.5D);
                Direction direction1 = Direction.byHorizontalIndex((i + direction.getHorizontalIndex()) % 4);
                float f = -direction1.getHorizontalAngle();
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                matrixStackIn.translate(-0.3125D, -0.3125D, 0.0D);
                matrixStackIn.scale(0.375F, 0.375F, 0.375F);
                Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }

        NonNullList<ItemStack> logInventory = tileEntityIn.getLogInventory();
        for (int i = 0; i < logInventory.size(); ++i) {
            ItemStack itemstack = logInventory.get(i);
            if (itemstack != ItemStack.EMPTY) {
                matrixStackIn.push();
                matrixStackIn.translate(0.5D, 0, 0.5D);
                boolean layer2 = i > 1;
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-direction.getHorizontalAngle() + (i % 2 == 0 ? 0 : 180) + (layer2 ? 90 : 0)));
                matrixStackIn.translate(-0.5D, layer2 ? 3f / 16f : 0, -0.5D);
                matrixStackIn.translate(1f / 16f, 0, 0);

                IVertexBuilder logVertexBuilder = ForgeHooksClient.getBlockMaterial(
                        new MythriaResourceLocation("blocks/campfire/" +
                                itemstack.getItem().getRegistryName().getPath()))
                        .getBuffer(bufferIn, logModel::getRenderType);

                logModel.render(matrixStackIn, logVertexBuilder, combinedLightIn, combinedOverlayIn,
                        1, 1, 1, 1);

                MythriaResourceLocation loc = tileEntityIn.isSoulfire() ?
                        new MythriaResourceLocation("blocks/campfire_burning_soul") :
                        new MythriaResourceLocation("blocks/campfire_burning");
                IVertexBuilder burningOverlayVertexBuilder = ForgeHooksClient.getBlockMaterial(loc)
                        .getBuffer(bufferIn, burningOverlayBottom::getRenderType);

                if (tileEntityIn.isLit()) {
                    if (i < 2) {
                        burningOverlayBottom.render(matrixStackIn, burningOverlayVertexBuilder, combinedLightIn, combinedOverlayIn,
                                1, 1, 1, 1);
                    } else {
                        burningOverlayTop.render(matrixStackIn, burningOverlayVertexBuilder, combinedLightIn, combinedOverlayIn,
                                1, 1, 1, 1);
                    }
                }

                matrixStackIn.pop();
            }
        }
    }
}
