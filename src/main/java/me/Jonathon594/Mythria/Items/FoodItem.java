package me.Jonathon594.Mythria.Items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class FoodItem extends MythriaItem {
    public FoodItem(String name, int hunger, float saturation) {
        this(name, hunger, saturation, false);
    }

    public FoodItem(String name, int hunger, float saturation, boolean override) {
        super(name, new Item.Properties().group(ItemGroup.FOOD).food(
                new Food.Builder().hunger(hunger).saturation(saturation).build()
        ), override);
    }
}
