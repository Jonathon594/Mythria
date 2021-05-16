package me.Jonathon594.Mythria.Client.Renderer.TileEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.Jonathon594.Mythria.Blocks.PitFurnaceBlock;
import me.Jonathon594.Mythria.Client.Model.BurningLogOverlay;
import me.Jonathon594.Mythria.Client.Model.LogModel;
import me.Jonathon594.Mythria.Client.Model.PitFurnaceFillingModel;
import me.Jonathon594.Mythria.Client.Model.PitFurnaceTopOverlay;
import me.Jonathon594.Mythria.Interface.IPitFurnaceFilling;
import me.Jonathon594.Mythria.TileEntity.PitFurnaceTileEntity;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;

public class PitFurnaceTileEntityRenderer extends TileEntityRenderer<PitFurnaceTileEntity> {
    private final PitFurnaceFillingModel[] fillingModels = new PitFurnaceFillingModel[4];
    private final LogModel logModel;
    private final BurningLogOverlay burningOverlay = new BurningLogOverlay(true);
    private final PitFurnaceTopOverlay[] pitFurnaceTopOverlay = new PitFurnaceTopOverlay[4];

    public PitFurnaceTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);

        for (int i = 0; i < 4; i++) {
            fillingModels[i] = new PitFurnaceFillingModel(i * 2 + 2);
        }
        for (int i = 0; i < 2; i++) {
            this.pitFurnaceTopOverlay[i] = new PitFurnaceTopOverlay(i * 4, false);
            this.pitFurnaceTopOverlay[3 - i] = new PitFurnaceTopOverlay(i * 4, true);
        }
        logModel = new LogModel();
    }

    @Override
    public void render(PitFurnaceTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        for (int i = 0; i < 2; i++) {
            this.pitFurnaceTopOverlay[i] = new PitFurnaceTopOverlay(i * 4, false);
            this.pitFurnaceTopOverlay[3 - i] = new PitFurnaceTopOverlay(i * 4, true);
        }
        Direction direction = tileEntityIn.getBlockState().get(PitFurnaceBlock.FACING);
        matrixStackIn.translate(0.5D, 0, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-direction.getHorizontalAngle()));
        matrixStackIn.translate(-0.5D, 0, -0.5D);
        boolean lit = tileEntityIn.isLit();
        for (int i = 0; i < tileEntityIn.getFillingCount(); i++) {
            matrixStackIn.push();
            matrixStackIn.translate(0, i * 0.125, 0);
            PitFurnaceFillingModel model = fillingModels[i];
            ItemStack filling = tileEntityIn.getFuelInventory().get(i);
            if (!(filling.getItem() instanceof IPitFurnaceFilling)) continue;
            IVertexBuilder thatchVertexBuilder = ForgeHooksClient.getBlockMaterial(
                    ((IPitFurnaceFilling) filling.getItem()).getFillingTexture())
                    .getBuffer(bufferIn, model::getRenderType);
            model.render(matrixStackIn, thatchVertexBuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f);
            matrixStackIn.pop();
        }

        MythriaResourceLocation loc = tileEntityIn.isSoulfire() ?
                new MythriaResourceLocation("blocks/campfire_burning_soul") :
                new MythriaResourceLocation("blocks/campfire_burning");
        IVertexBuilder burningOverlayVertexBuilder = ForgeHooksClient.getBlockMaterial(loc)
                .getBuffer(bufferIn, burningOverlay::getRenderType);

        for (int i = 0; i < tileEntityIn.getLogCount(); i++) {
            ItemStack itemStack = tileEntityIn.getFuelInventory().get(i + 4);
            if (itemStack.isEmpty()) continue;
            matrixStackIn.push();
            matrixStackIn.translate(i * 0.25, 0.5, 0);
            IVertexBuilder logVertexBuilder = ForgeHooksClient.getBlockMaterial(
                    new MythriaResourceLocation("blocks/campfire/" +
                            itemStack.getItem().getRegistryName().getPath()))
                    .getBuffer(bufferIn, logModel::getRenderType);
            logModel.render(matrixStackIn, logVertexBuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f);

            if (lit) {
                burningOverlay.render(matrixStackIn, burningOverlayVertexBuilder, combinedLightIn, combinedOverlayIn,
                        1, 1, 1, 1);
                pitFurnaceTopOverlay[i].render(matrixStackIn, burningOverlayVertexBuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f);
            }
            matrixStackIn.pop();
        }

        matrixStackIn.pop();
    }
}
