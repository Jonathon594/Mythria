package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Container.CrucibleContainerFull;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CrucibleFullScreen extends ContainerScreen<CrucibleContainerFull> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("mythria:textures/gui/container/gui_crucible_full.png");

    public CrucibleFullScreen(CrucibleContainerFull screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 4.0F, 4210752);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 94), 4210752);

        ItemStack currentItem = playerInventory.getCurrentItem();
        RenderHelper.enableStandardItemLighting();
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(null, currentItem, 80, 12);
        if (mouseX > guiLeft + 80 && mouseX <= 96 + guiLeft && mouseY > 12 + guiTop && mouseY <= 28 + guiTop)
            renderTooltip(matrixStack, currentItem, mouseX - guiLeft, mouseY - guiTop);
        RenderHelper.disableStandardItemLighting();
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

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
}
