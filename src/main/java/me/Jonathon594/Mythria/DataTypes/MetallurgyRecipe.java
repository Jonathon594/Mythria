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
        this.material = material;
        this.meltingPoint = meltingPoint;
        double p = 0;
        this.difficulty = difficulty;

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

    public MythriaMaterial getMaterial() {
        return material;
    }

    public double getMeltingPoint() {
        return meltingPoint;
    }

    public HashMap<Item, Double> getRecipe() {
        return recipe;
    }

    public boolean matches(IItemHandler items) {
        if (recipe.size() == 0) return false;
        HashMap<Item, Integer> crucibleContents = new HashMap<>();
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack is = items.getStackInSlot(i);
            if (crucibleContents.containsKey(is.getItem())) {
                crucibleContents.put(is.getItem(), crucibleContents.get(is.getItem()) + is.getCount());
            } else {
                crucibleContents.put(is.getItem(), is.getCount());
            }
        }

        for (Map.Entry<Item, Double> entry : recipe.entrySet()) {
            if (!crucibleContents.containsKey(entry.getKey())) return false;
            int vesselCount = crucibleContents.get(entry.getKey());
            int total = items.getSlots();
            double prop = (double) vesselCount / (double) total;
            double variance = (4 - difficulty) * 0.05;
            if (prop < entry.getValue() - variance || prop > entry.getValue() + variance) return false;
        }

        return true;
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
