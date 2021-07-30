package me.Jonathon594.Mythria.Client.Renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.math.MathHelper;

public class StatRenderer extends AbstractGui {
    private int value = 0;
    private int backgroundValue = 0;
    private final int u, v, uf, ufh;
    private final boolean hasBack, reverse;
    private final int slotOrder;

    public StatRenderer(int u, int v, int uf, int ufh, boolean hasBack, boolean reverse, int slotOrder) {
        this.u = u;
        this.v = v;
        this.uf = uf;
        this.ufh = ufh;
        this.hasBack = hasBack;
        this.reverse = reverse;
        this.slotOrder = slotOrder;
    }

    public int getSlotOrder() {
        return slotOrder;
    }

    public StatRenderer setValueFront(int value) {
        this.value = MathHelper.clamp(value, 0, 20);
        return this;
    }


    public StatRenderer setValueBack(int value) {
        this.backgroundValue = value;
        return this;
    }

    public void render(MatrixStack matrixStack, int x, int y) {
        RenderSystem.enableBlend();

        for (int i = 0; i < 10; i++) {
            int idx = i * 2 + 1;
            int offset = reverse ? 72 - (i * 8) : i * 8;

            if (hasBack) {
                if (idx <= backgroundValue) blit(matrixStack, x + offset, y, u, v, 9, 9);
            }

            if (idx < value) {
                blit(matrixStack, x + offset, y, uf, v, 9, 9);
            }

            if (idx == value) {
                blit(matrixStack, x + offset, y, ufh, v, 9, 9);
            }
        }

        RenderSystem.disableBlend();
    }
}
