package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.CookType;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class FoodRecipe {
    private final Item result;
    private final Item ingredient;
    private final ArrayList<CookType> type = new ArrayList<>();

    public FoodRecipe(final Item result, final Item ingredient, final CookType... type) {
        super();
        this.result = result;
        this.ingredient = ingredient;
        this.type.addAll(Arrays.asList(type));
    }

    public Item getIngredient() {
        return ingredient;
    }

    public Item getResult() {
        return result;
    }

    public ArrayList<CookType> getType() {
        return type;
    }
}
