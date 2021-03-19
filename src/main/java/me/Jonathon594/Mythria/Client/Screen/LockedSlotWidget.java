package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Managers.LimitedInventoryManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class LockedSlotWidget extends Widget {
    private final ContainerScreen gui;
    private final Minecraft minecraft;

    public LockedSlotWidget(final ContainerScreen gui) {
        super(0, 0, 16, 16, new StringTextComponent(""));
        this.gui = gui;
        active = false;
        minecraft = Minecraft.getInstance();
    }

    public final void drawLockedSlot(MatrixStack matrixStack, final int slotX, final int slotY) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(0.0f, 0.0f, 0.0f, 0.5f);
        this.blit(matrixStack, gui.getGuiLeft() + slotX, gui.getGuiTop() + slotY, 0, 0, 16, 16);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            final List<Slot> slotList = gui.getContainer().inventorySlots;
            if (slotList != null && slotList.size() > 0)
                for (int i = 0; i < slotList.size(); i++) {
                    final Slot slot = gui.getContainer().inventorySlots.get(i);
                    if (slot != null && LimitedInventoryManager.isSameInventory(minecraft.player.inventory, slot)) {
                        if (!LimitedInventoryManager.isSlotOpen(minecraft.player, slot.getSlotIndex())) {
                            drawLockedSlot(matrixStack, slot.xPos, slot.yPos);
                        }
                    }
                }
        }
    }

}
