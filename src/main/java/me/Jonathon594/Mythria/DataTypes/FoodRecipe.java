package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.CookType;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class FoodRecipe {
    private final Item result;
    private final Item ingredient;
    private final ArrayList<CookType> type = new ArrayList<>();

    public FoodRecipe(final Item result, final Item ingredient, final CookType... type) {
        super();
        this.result = result;
        this.ingredient = ingredient;
        for (int i = 0; i < type.length; i++) {
            this.type.add(type[i]);
        }
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
