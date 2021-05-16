package me.Jonathon594.Mythria.Genetic.Gene;

import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;

public class NutritionGene extends Gene {
    private final HashMap<Consumable.Nutrition, Integer> requiredNutrition;

    public NutritionGene(HashMap<Consumable.Nutrition, Integer> requiredNutrition) {
        super(GeneType.NUTRITION);
        this.requiredNutrition = requiredNutrition;
    }

    public HashMap<Consumable.Nutrition, Integer> getRequiredNutrition() {
        return requiredNutrition;
    }

    @Override
    public GeneSerializer<NutritionGene> getSerializer() {
        return GeneSerializers.NUTRITION;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }


}
