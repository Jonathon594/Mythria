package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Food.Food;
import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.DataTypes.FoodRecipe;
import me.Jonathon594.Mythria.DataTypes.MythriaFoodData;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    public static final List<FoodRecipe> RECIPES = new ArrayList<>();
    public static final List<MythriaFoodData> FOOD_DATA = new ArrayList<>();

//    public static int Cook(final ItemStackHandler cookItems, final double cookspeed, CookType cookType) {
////        int cooked = 0;
////        for (int i = 0; i < cookItems.getSlots(); i++) {
////            ItemStack is = cookItems.getStackInSlot(i);
////            ItemStack newItem = cookFood(cookspeed, cookType, is);
////            if (!is.equals(newItem)) {
////                cookItems.setStackInSlot(i, newItem);
////                cooked++;
////            }
////        }
////        return cooked;
////    }

    public static void UpdateFood(final ItemStack is, double preservation, boolean softUpdate) {
        if (is.isEmpty())
            return;
        Food food = FoodProvider.getFood(is);
        if (food == null) return;
        if (food.getOrigin() == 0)
            food.setOrigin(System.currentTimeMillis());
        double agePropPre = food.getAgeProportion(is);
        final long realAge = System.currentTimeMillis() - food.getOrigin();
        long deltaAge = (long) ((realAge - food.getAge()) * preservation);
        food.setAge(food.getAge() + deltaAge);
        double ageProp = food.getAgeProportion(is);
        if (ageProp > 1.25) {
            is.setCount(0);
        }
    }

    public static MythriaFoodData getFoodData(Item food) {
        for (final MythriaFoodData mfd : FOOD_DATA)
            if (mfd.getItem().equals(food))
                return mfd;

        return null;
    }

    public static long getFoodLifeTime(final Item food) {
        MythriaFoodData mfd = getFoodData(food);
        if (mfd == null) return 0;
        return (long) (mfd.getMaxLife() * 20 * 60 * 1000);
    }

    public static FoodRecipe getFoodRecipe(final Item item) {
        for (final FoodRecipe fr : RECIPES)
            if (fr.getIngredient().equals(item))
                return fr;
        return null;
    }

    public static void init() {
        FOOD_DATA.add(new MythriaFoodData(Items.APPLE, 7, Consumable.Nutrition.FRUIT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.BAKED_POTATO, 10, Consumable.Nutrition.STARCH, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.BEETROOT, 14, Consumable.Nutrition.FRUIT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.BEETROOT_SOUP, 7, Consumable.Nutrition.FRUIT, 4));
        FOOD_DATA.add(new MythriaFoodData(MythriaItems.WHEAT_DOUGH, 1, Consumable.Nutrition.STARCH, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.BREAD, 21, Consumable.Nutrition.STARCH, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.CARROT, 14, Consumable.Nutrition.VEGETABLE, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.CHORUS_FRUIT, 18, Consumable.Nutrition.FRUIT, 4));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_CHICKEN, 14, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_MUTTON, 14, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_PORKCHOP, 14, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_RABBIT, 14, Consumable.Nutrition.MEAT, 0));
        //FOOD_DATA.add(new MythriaFoodData(Items.COOKED_FISH, 14, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKIE, 8, Consumable.Nutrition.STARCH, 0));
        //FOOD_DATA.add(new MythriaFoodData(Items.FISH, 2, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.MELON, 4, Consumable.Nutrition.FRUIT, 4));
        FOOD_DATA.add(new MythriaFoodData(Items.MUSHROOM_STEW, 7, Consumable.Nutrition.VEGETABLE, 4));
        FOOD_DATA.add(new MythriaFoodData(Items.POISONOUS_POTATO, 14, Consumable.Nutrition.STARCH, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.POTATO, 14, Consumable.Nutrition.STARCH, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.PUMPKIN_PIE, 7, Consumable.Nutrition.FRUIT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.RABBIT_STEW, 7, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.BEEF, 2, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.CHICKEN, 1, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.MUTTON, 3, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.PORKCHOP, 2, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.RABBIT, 3, Consumable.Nutrition.MEAT, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.SPIDER_EYE, 6, null, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_BEEF, 14, Consumable.Nutrition.MEAT, 0));
        //FOOD_DATA.add(new MythriaFoodData(MythriaItems.COOKED_CARROT, 10, Consumable.Nutrition.VEGETABLE, 0));
        FOOD_DATA.add(new MythriaFoodData(Items.MILK_BUCKET, 7, Consumable.Nutrition.DAIRY, 12));
        //FOOD_DATA.add(new MythriaFoodData(Items.POTIONITEM, 34, null, 6));
        FOOD_DATA.add(new MythriaFoodData(Items.EGG, 7, Consumable.Nutrition.MEAT, 0));
        //FOOD_DATA.add(new MythriaFoodData(MythriaItems.RAW_PUMPKIN_PIE, 7, Consumable.Nutrition.FRUIT, 0));

        FOOD_DATA.add(new MythriaFoodData(MythriaItems.WARPED_FUNGUS, 4, Consumable.Nutrition.VEGETABLE, 6));
        FOOD_DATA.add(new MythriaFoodData(MythriaItems.CRIMSON_FUNGUS, 4, Consumable.Nutrition.VEGETABLE, 4));
        FOOD_DATA.add(new MythriaFoodData(MythriaItems.RED_MUSHROOM, 3, Consumable.Nutrition.VEGETABLE, 0));
        FOOD_DATA.add(new MythriaFoodData(MythriaItems.BROWN_MUSHROOM, 3, Consumable.Nutrition.VEGETABLE, 0));

//        RECIPES.add(new FoodRecipe(Items.COOKED_BEEF, Items.BEEF, CookType.ROAST, CookType.BAKE));
//        RECIPES.add(new FoodRecipe(Items.COOKED_MUTTON, Items.MUTTON, CookType.ROAST, CookType.BAKE));
//        RECIPES.add(new FoodRecipe(Items.COOKED_PORKCHOP, Items.PORKCHOP, CookType.ROAST, CookType.BAKE));
//        //RECIPES.add(new FoodRecipe(Items.COOKED_FISH, Items.FISH, CookType.ROAST, CookType.BAKE));
//        RECIPES.add(new FoodRecipe(Items.COOKED_CHICKEN, Items.CHICKEN, CookType.ROAST, CookType.BAKE));
//        RECIPES.add(new FoodRecipe(Items.BREAD, MythriaItems.WHEAT_DOUGH, CookType.BAKE));
//        RECIPES.add(new FoodRecipe(Items.BAKED_POTATO, Items.POTATO, CookType.BAKE));
        //RECIPES.add(new FoodRecipe(MythriaItems.COOKED_CARROT, Items.CARROT, CookType.BAKE, CookType.ROAST));
        //RECIPES.add(new FoodRecipe(Items.PUMPKIN_PIE, MythriaItems.RAW_PUMPKIN_PIE, CookType.BAKE));
    }

    public static boolean isRaw(ItemStack item) {
        if (item == null) return false;
        Item i = item.getItem();
        if (i.equals(Items.BEEF)) return true;
        if (i.equals(Items.CHICKEN)) return true;
        if (i.equals(Items.PORKCHOP)) return true;
        if (i.equals(Items.RABBIT)) return true;
        if (i.equals(Items.MUTTON)) return true;
        if (i.equals(Items.COD)) return true;
        return i.equals(Items.EGG);
    }

    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Food food = FoodProvider.getFood(itemStack);
        if (food == null) return;
        List<ITextComponent> toolTip = event.getToolTip();
        double ageProp = food.getAgeProportion(itemStack);
        int message = (int) Math.floor(ageProp * 4);
        toolTip.add(new StringTextComponent(ColorConst.MAIN_COLOR + MythriaConst.FOOD_AGE[message]));
        toolTip.add(new StringTextComponent(ColorConst.CONT_COLOR + "" + Math.round(food.getCooked() * 100) + "% Cooked."));
    }

    public static void onPlayerPickupItem(final EntityItemPickupEvent event) {
        if (event.isCanceled())
            return;
        if (event.getPlayer().world.isRemote) return;
        UpdateFood(event.getItem().getItem(), 1, false);
    }

    public static void updateFoodItems(final PlayerContainerEvent.Open event) {
        final Container c = event.getContainer();
        double preservation = 1.0;
//        if (c instanceof IFoodPreservationContainer) {
//            preservation = ((IFoodPreservationContainer) c).getFoodPreservation();
//        }
        for (int i = 0; i < c.getInventory().size(); i++) {
            final ItemStack is = c.getInventory().get(i);
            UpdateFood(is, preservation, false);
            c.putStackInSlot(i, is);
        }
        updatePlayerInventory(event.getPlayer(), false);
    }

    public static void updatePlayerInventory(final PlayerEntity player, boolean softUpdate) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            final ItemStack is = player.inventory.getStackInSlot(i);
            UpdateFood(is, 1, softUpdate);
            player.inventory.setInventorySlotContents(i, is);
        }
    }
}
