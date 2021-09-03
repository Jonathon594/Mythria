package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

public class SpellBookGui extends AbstractGui implements IRenderable, IGuiEventListener {
    public void func_230477_a_(MatrixStack matrixStack, int guiLeft, int guiTop, boolean b, float partialTicks) {

    }

    public void func_238924_c_(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {

    }

    public void init(int width, int height, Minecraft minecraft, boolean widthTooNarrow) {

    }

    public void initSearchBar(boolean widthTooNarrow) {

    }

    public boolean isVisible() {
        return false;
    }

    public void removed() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

    }

    public void toggleVisibility() {

    }

    public int updateScreenPosition(boolean widthTooNarrow, int width, int xSize) {
        int i;
        if (this.isVisible() && !widthTooNarrow) {
            i = 177 + (width - xSize - 200) / 2;
        } else {
            i = (width - xSize) / 2;
        }

        return i;
    }
}
