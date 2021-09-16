package me.Jonathon594.Mythria.Recipe;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class CarpentryRecipe extends CrafterRecipe {

    public static final ResourceLocation RECIPE_TYPE_ID = new MythriaResourceLocation("carpentry");
    public static final IRecipeType<CarpentryRecipe> CARPENTRY = Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPE_ID,
            new IRecipeType<CarpentryRecipe>() {
                @Override
                public String toString() {
                    return RECIPE_TYPE_ID.toString();
                }
            });

    public CarpentryRecipe(final ResourceLocation resourceLocation, final String group, final Ingredient ingredient, int cost, final ItemStack itemStack) {
        super(CARPENTRY, MythriaRecipeSerializer.CARPENTRY, resourceLocation, group, ingredient, cost, itemStack);

    }

    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    public ItemStack getIcon() {
        return new ItemStack(MythriaBlocks.SAW_HORSE);
    }
}
