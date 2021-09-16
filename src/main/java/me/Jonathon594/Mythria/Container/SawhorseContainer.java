package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Items.MythriaSawItem;
import me.Jonathon594.Mythria.Recipe.CarpentryRecipe;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.Collection;

public class SawhorseContainer extends BlockCrafterContainer {
    public SawhorseContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.SAWHORSE, windowID, playerInventory);
    }

    @Override
    protected boolean isValidTool(ItemStack tool) {
        return tool.getItem() instanceof MythriaSawItem;
    }

    @Override
    protected IRecipeType<CarpentryRecipe> getRecipeType() {
        return CarpentryRecipe.CARPENTRY;
    }

    @Override
    protected Collection<Item> getValidItems() {
        Collection<Item> collection = MythriaUtil.getItemCollectionFromTag(ItemTags.LOGS.getName());
        collection.addAll(MythriaUtil.getItemCollectionFromTag(new MythriaResourceLocation("logs")));
        collection.addAll(MythriaUtil.getItemCollectionFromTag(ItemTags.PLANKS.getName()));
        return collection;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ENTITY_SHEEP_SHEAR;
    }
}
