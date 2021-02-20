package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.Container.BlockCrafterContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class BlockCrafterScreen<T extends BlockCrafterContainer> extends CrafterScreen<T> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("mythria:textures/gui/container/blockcrafter.png");

    public BlockCrafterScreen(T container, PlayerInventory invPlayer, ITextComponent title) {
        super(container, invPlayer, title);
        container.setRunnable(this::onInventoryUpdate);
    }

    @Override
    protected ResourceLocation getBackground() {
        return BACKGROUND;
    }
}
