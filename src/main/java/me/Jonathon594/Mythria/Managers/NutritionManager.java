package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.DataTypes.MythriaFoodData;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public class NutritionManager {
    public static void onConsume(LivingEntityUseItemEvent.Finish event, PlayerEntity player, Profile profile) {
        ItemStack is = event.getItem();
        Item item = is.getItem();

        MythriaFoodData mythriaFoodData = FoodManager.getFoodData(item);
        if (mythriaFoodData == null) return;
        if (mythriaFoodData.getNutrition() == null) return;

        Consumable.Nutrition nutrition = mythriaFoodData.getNutrition();
        double amount = 1.3;
        if (item.isFood()) {
            Food food = item.getFood();
            float hunger = food.getHealing();
            amount = 0.1625 * hunger * (1 + food.getSaturation());
        }
        profile.setUndigestedNutrition(nutrition, profile.getUndigestedNutrition(nutrition) + amount);
        double thirst = mythriaFoodData.getThirst();
        if (thirst > 0)
            profile.setConsumable(Consumable.THIRST, profile.getConsumables().get(Consumable.THIRST) + thirst);
    }
}
