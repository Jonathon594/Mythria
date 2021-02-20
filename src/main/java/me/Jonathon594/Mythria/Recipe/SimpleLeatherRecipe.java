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

public class SimpleLeatherRecipe extends CrafterRecipe {

    public static final ResourceLocation RECIPE_TYPE_ID = new MythriaResourceLocation("simple_leather");
    public static final IRecipeType<SimpleLeatherRecipe> SIMPLE_LEATHER_RECIPE = Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPE_ID,
            new IRecipeType<SimpleLeatherRecipe>() {
                @Override
                public String toString() {
                    return RECIPE_TYPE_ID.toString();
                }
            });

    public SimpleLeatherRecipe(final ResourceLocation resourceLocation, final String group, final Ingredient ingredient, int cost, final ItemStack itemStack, int tier) {
        super(SIMPLE_LEATHER_RECIPE, MythriaRecipeSerializer.SIMPLE_LEATHER, resourceLocation, group, ingredient, cost, itemStack, tier);

    }

    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    public ItemStack getIcon() {
        return new ItemStack(Items.LEATHER);
    }
}
