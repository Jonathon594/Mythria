package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.Container.BowstringContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BowstringScreen extends ToolComponentScreen<BowstringContainer> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("mythria:textures/gui/container/gui_tool_handle.png");

    public BowstringScreen(BowstringContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected ResourceLocation getBackground() {
        return BACKGROUND;
    }
}
