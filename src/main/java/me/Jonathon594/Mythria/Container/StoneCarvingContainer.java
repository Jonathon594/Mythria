package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Items.MythriaChiselItem;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Recipe.StoneCarvingRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.Collection;

public class StoneCarvingContainer extends ToolCrafterContainer {
    public StoneCarvingContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.STONE_CARVING, windowID, playerInventory);
    }

    @Override
    protected boolean isValidTool(ItemStack tool) {
        return tool.getItem() instanceof MythriaChiselItem;
    }

    @Override
    protected IRecipeType<StoneCarvingRecipe> getRecipeType() {
        return StoneCarvingRecipe.STONE_CARVING_RECIPE;
    }

    @Override
    protected Collection<Item> getValidItems() {
        Collection<Item> collection = new ArrayList<>();
        collection.add(MythriaItems.ROCK);
        return collection;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ENTITY_SHEEP_SHEAR;
    }
}
