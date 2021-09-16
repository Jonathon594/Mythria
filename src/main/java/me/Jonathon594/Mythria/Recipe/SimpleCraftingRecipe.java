package me.Jonathon594.Mythria.Recipe;

import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SimpleCraftingRecipe extends CrafterRecipe {

    public static final ResourceLocation RECIPE_TYPE_ID = new MythriaResourceLocation("simple_crafting");
    public static final IRecipeType<SimpleCraftingRecipe> SIMPLE_CRAFTING_RECIPE = Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPE_ID,
            new IRecipeType<SimpleCraftingRecipe>() {
                @Override
                public String toString() {
                    return RECIPE_TYPE_ID.toString();
                }
            });

    public SimpleCraftingRecipe(final ResourceLocation resourceLocation, final String group, final Ingredient ingredient, int cost, final ItemStack itemStack) {
        super(SIMPLE_CRAFTING_RECIPE, MythriaRecipeSerializer.SIMPLE_CRAFTING, resourceLocation, group, ingredient, cost, itemStack);

    }

    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    public ItemStack getIcon() {
        return new ItemStack(Blocks.CRAFTING_TABLE);
    }
}
