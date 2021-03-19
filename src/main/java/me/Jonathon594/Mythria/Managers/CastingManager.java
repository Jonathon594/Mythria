package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.CastingRecipe;
import me.Jonathon594.Mythria.Enum.EnumMetalShape;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;

import java.util.ArrayList;

public class CastingManager {
    private static final ArrayList<CastingRecipe> recipes = new ArrayList<>();

    public static void addRecipe(CastingRecipe castingRecipe) {
        if (getRecipe(castingRecipe.getShape(), castingRecipe.getMaterial()) != null)
            throw new IllegalArgumentException("Casting recipe already exists.");
        recipes.add(castingRecipe);
    }

    public static CastingRecipe getRecipe(EnumMetalShape shape, MythriaMaterial material) {
        for (CastingRecipe castingRecipe : recipes) {
            if (castingRecipe.getMaterial().equals(material) && castingRecipe.getShape().equals(shape))
                return castingRecipe;
        }
        return null;
    }
}
