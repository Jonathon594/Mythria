package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.MythriaMaterial;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MetallurgyRecipe {
    private final int difficulty;
    private final MythriaMaterial material;
    private final double meltingPoint;
    private final HashMap<Item, Double> recipe = new HashMap<>();

    public MetallurgyRecipe(int difficulty, MythriaMaterial material, double meltingPoint, MetallurgyRecipePair... items) {
        this(difficulty, material, meltingPoint);

        double p = 0;
        if (items != null) {
            for (MetallurgyRecipePair entry : items) {
                recipe.put(entry.getItem(), entry.getProp());
                p += entry.getProp();
            }

            if (p != 1) {
                System.out.println("INVALID SMELTING RECIPE");
                return;
            }
        }
    }

    public MetallurgyRecipe(int difficulty, MythriaMaterial material, double meltingPoint) {
        this.difficulty = difficulty;
        this.material = material;
        this.meltingPoint = meltingPoint;

        SmeltingManager.addRecipe(this);
    }

    public MetallurgyRecipe addIngredient(Item item, double proportion) {
        if (proportion > 1.0) throw new IllegalArgumentException("proportion can not be larger than 1.0.");
        double proportionTotal = 0;
        for (double d : recipe.values()) proportionTotal += d;
        double remainingProportion = 1.0 - proportionTotal;
        if (proportion > remainingProportion)
            throw new IllegalArgumentException("total proportion of all ingredients cannot exceed 1.0.");
        recipe.put(item, proportion);
        return this;
    }

    public double getDeviance(IItemHandler items) {
        if (recipe.size() == 0) return 1.0;
        HashMap<Item, Integer> itemCounts = new HashMap<>();
        int totalItems = 0;
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack itemStack = items.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            Item item = itemStack.getItem();
            if (itemCounts.containsKey(item)) {
                itemCounts.put(item, itemCounts.get(item) + 1);
            } else {
                itemCounts.put(item, 1);
            }
            totalItems++;
        }

        double totalDeviance = 0.0;
        int ingredientCount = recipe.size();
        for (Map.Entry<Item, Double> entry : recipe.entrySet()) {
            if (!itemCounts.containsKey(entry.getKey())) {
                totalDeviance += 1.0;
                continue;
            }
            double existingProp = (double) itemCounts.get(entry.getKey()) / (double) totalItems;
            double requiredProp = recipe.get(entry.getKey());
            double devianceProp = Math.abs(requiredProp - existingProp);
            totalDeviance += devianceProp;
        }
        totalDeviance /= ingredientCount;

        return totalDeviance;
    }

    public MythriaMaterial getMaterial() {
        return material;
    }

    public double getMeltingPoint() {
        return meltingPoint;
    }

    public HashMap<Item, Double> getRecipe() {
        return recipe;
    }

    public static class MetallurgyRecipePair {
        private final Supplier<Item> item;
        private final double prop;

        public MetallurgyRecipePair(Supplier<Item> item, double prop) {
            this.item = item;
            this.prop = prop;
        }

        public Item getItem() {
            return item.get();
        }

        public double getProp() {
            return prop;
        }
    }
}
