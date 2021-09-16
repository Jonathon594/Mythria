package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Recipe.SimpleCraftingRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;

public class SimpleCraftingContainer extends ToolCrafterContainer {
    public SimpleCraftingContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.SIMPLE_CRAFTING, windowID, playerInventory);
    }

    @Override
    protected boolean isValidTool(ItemStack tool) {
        return true;
    }

    @Override
    protected IRecipeType<SimpleCraftingRecipe> getRecipeType() {
        return SimpleCraftingRecipe.SIMPLE_CRAFTING_RECIPE;
    }

    @Override
    protected Collection<Item> getValidItems() {
        return ForgeRegistries.ITEMS.getValues();
    }

    @Override
    protected boolean needsTool() {
        return false;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ENTITY_SHEEP_SHEAR;
    }
}
