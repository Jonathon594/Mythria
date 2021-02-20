package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Recipe.SimpleLeatherRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleLeatherContainer extends ToolCrafterContainer {
    public SimpleLeatherContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.SIMPLE_LEATHER, windowID, playerInventory);
    }

    @Override
    protected Collection<Item> getValidItems() {
        Collection<Item> collection = new ArrayList<>();
        collection.add(Items.LEATHER);
        return collection;
    }

    @Override
    protected boolean needsTool() {
        return false;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    protected boolean isValidTool(ItemStack tool) {
        return true;
    }

    @Override
    protected int getCraftingTier() {
        return 0;
    }

    @Override
    protected IRecipeType<SimpleLeatherRecipe> getRecipeType() {
        return SimpleLeatherRecipe.SIMPLE_LEATHER_RECIPE;
    }
}
