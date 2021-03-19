package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.MetallurgyRecipe;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;
import me.Jonathon594.Mythria.Items.MythriaItems;

import java.util.ArrayList;

public class SmeltingManager {
    private static final ArrayList<MetallurgyRecipe> metalRecipes = new ArrayList<>();

    public static void addRecipe(MetallurgyRecipe metallurgyRecipe) {
        MetallurgyRecipe recipe = getMetalRecipe(metallurgyRecipe.getMaterial());
        if (recipe != null) return;
        metalRecipes.add(metallurgyRecipe);
    }

    public static MetallurgyRecipe getMetalRecipe(MythriaMaterial material) {
        for (MetallurgyRecipe recipe : metalRecipes) {
            if (recipe.getMaterial().equals(material)) return recipe;
        }
        return null;
    }

    public static ArrayList<MetallurgyRecipe> getMetalRecipes() {
        return metalRecipes;
    }

    public static void init() {
        new MetallurgyRecipe(1, MythriaMaterial.TIN, 231.9, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.TIN_ORE_ITEM, 1.0));
        new MetallurgyRecipe(1, MythriaMaterial.COPPER, 1084, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.COPPER_ORE_ITEM, 1.0));
        new MetallurgyRecipe(1, MythriaMaterial.SILVER, 961.8, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.SILVER_ORE_ITEM, 1.0));
        new MetallurgyRecipe(1, MythriaMaterial.GOLD, 1064, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.GOLD_ORE_ITEM, 1.0));
        new MetallurgyRecipe(1, MythriaMaterial.BRONZE, 950, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.COPPER_ORE_ITEM, 0.8),
                new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.TIN_ORE_ITEM, 0.2));
        new MetallurgyRecipe(1, MythriaMaterial.IRON, 1538, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.IRON_ORE_ITEM, 1.0));
        new MetallurgyRecipe(1, MythriaMaterial.STEEL, 1370);
        new MetallurgyRecipe(3, MythriaMaterial.TITANIUM, 1668, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.TITANIUM_ORE_ITEM, 1.0));
        new MetallurgyRecipe(4, MythriaMaterial.PLATINUM, 1765, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.PLATINUM_INGOT, 1.0));
        new MetallurgyRecipe(4, MythriaMaterial.TUNGSTEN, 3422, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.TUNGSTEN_ORE_ITEM, 1.0));
        new MetallurgyRecipe(0, MythriaMaterial.GLASS, 1200, new MetallurgyRecipe.MetallurgyRecipePair(() -> MythriaItems.SAND, 1.0));
    }

    public enum Temperature {
        COLD("Cold"), WARM("Warm"), HOT("Hot"), VERY_HOT("Very Hot"), MOLTEN("Molten");

        private final String display;

        Temperature(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }
    }
}
