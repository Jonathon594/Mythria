package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.Container.ToolHandleContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ToolHandleScreen extends ToolComponentScreen<ToolHandleContainer> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("mythria:textures/gui/container/gui_tool_handle.png");

    public ToolHandleScreen(ToolHandleContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected ResourceLocation getBackground() {
        return BACKGROUND;
    }
}
