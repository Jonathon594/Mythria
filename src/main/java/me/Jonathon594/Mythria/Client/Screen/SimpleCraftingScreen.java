package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.Container.SimpleCraftingContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;

public class SimpleCraftingScreen extends ToolCrafterScreen<SimpleCraftingContainer> {
    public SimpleCraftingScreen(final SimpleCraftingContainer container, final PlayerInventory invPlayer, final ITextComponent title) {
        super(container, invPlayer, title);
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.UI_STONECUTTER_SELECT_RECIPE;
    }
}
