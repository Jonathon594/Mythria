package me.Jonathon594.Mythria.Recipe;

import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class WoodCarvingRecipe extends CrafterRecipe {

    public static final ResourceLocation RECIPE_TYPE_ID = new MythriaResourceLocation("wood_carving");
    public static final IRecipeType<WoodCarvingRecipe> WOOD_CARVING_RECIPE = Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPE_ID,
            new IRecipeType<WoodCarvingRecipe>() {
                @Override
                public String toString() {
                    return RECIPE_TYPE_ID.toString();
                }
            });

    public WoodCarvingRecipe(final ResourceLocation resourceLocation, final String group, final Ingredient ingredient, int cost, final ItemStack itemStack) {
        super(WOOD_CARVING_RECIPE, MythriaRecipeSerializer.WOOD_CARVING, resourceLocation, group, ingredient, cost, itemStack);

    }

    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    public ItemStack getIcon() {
        return new ItemStack(Items.FLINT);
    }
}
