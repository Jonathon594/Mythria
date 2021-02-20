package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.item.Item;

public class MythriaFoodData {
    private final Item item;
    private final double maxLife;
    private final Consumable.Nutrition nutrition;
    private final double thirst;

    public MythriaFoodData(final Item item, final double maxLife, Consumable.Nutrition nutrition, double thirst) {
        super();
        this.item = item;
        this.maxLife = maxLife;
        this.nutrition = nutrition;
        this.thirst = thirst;
    }

    public Item getItem() {
        return item;
    }

    public double getMaxLife() {
        return maxLife;
    }

    public Consumable.Nutrition getNutrition() {
        return nutrition;
    }

    public double getThirst() {
        return thirst;
    }
}
