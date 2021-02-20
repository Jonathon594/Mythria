package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Recipe.SimplePotteryRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.Collection;

public class SimplePotteryContainer extends ToolCrafterContainer {
    public SimplePotteryContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.SIMPLE_POTTERY, windowID, playerInventory);
    }

    @Override
    protected Collection<Item> getValidItems() {
        Collection<Item> collection = new ArrayList<>();
        collection.add(Items.CLAY_BALL);
        return collection;
    }

    @Override
    protected boolean needsTool() {
        return false;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ENTITY_SHEEP_SHEAR;
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
    protected IRecipeType<SimplePotteryRecipe> getRecipeType() {
        return SimplePotteryRecipe.SIMPLE_POTTERY_RECIPE;
    }
}
