package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Items.CuttingStone;
import me.Jonathon594.Mythria.Recipe.WoodCarvingRecipe;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CarvingContainer extends ToolCrafterContainer {
    public CarvingContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.WOOD_CARVING, windowID, playerInventory);
    }

    @Override
    protected boolean isValidTool(ItemStack tool) {
        return tool.getItem() instanceof CuttingStone;
    }

    @Override
    protected IRecipeType<WoodCarvingRecipe> getRecipeType() {
        return WoodCarvingRecipe.WOOD_CARVING_RECIPE;
    }

    @Override
    protected Collection<Item> getValidItems() {
        Collection<Item> collection = new ArrayList<>();
        collection.addAll(MythriaUtil.getItemCollectionFromTag(ItemTags.LOGS.getName()));
        collection.addAll(MythriaUtil.getItemCollectionFromTag(new MythriaResourceLocation("logs")));
        collection.addAll(MythriaUtil.getItemCollectionFromTag(new MythriaResourceLocation("sticks")));
        collection.add(Items.BONE);
        return collection;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ENTITY_SHEEP_SHEAR;
    }
}
