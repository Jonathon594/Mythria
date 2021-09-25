package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Container.MythriaFurnaceContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FurnaceScreen extends ContainerScreen<MythriaFurnaceContainer> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("mythria:textures/gui/container/gui_furnace.png");

    public FurnaceScreen(MythriaFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 4.0F, 4210752);

        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int temp = container.getTemperature();
        int temprel = Math.max(temp - 250, 0);
        float prop = (float) temprel / 3750.0f;
        blit(matrixStack, 8, 8 + (int) (((1 - prop) * 64)), 190, 0, 15, 5);

        if (this.container.isBurning()) {
            int k = this.container.getBurnLeftScaled();
            this.blit(matrixStack, 80, 37 + 12 - k, 176, 12 - k, 14, k + 1);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
    }
}
