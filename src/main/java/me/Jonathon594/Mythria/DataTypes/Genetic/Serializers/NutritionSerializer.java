package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.NutritionGene;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NutritionSerializer extends GeneSerializer<NutritionGene> {
    public NutritionSerializer(String name) {
        super(name);
    }

    @Override
    public NutritionGene deserialize(CompoundNBT compoundNBT) {
        HashMap<Consumable.Nutrition, Integer> map = new HashMap<>();
        Set<String> keys = compoundNBT.keySet();
        for (String key : keys) {
            Consumable.Nutrition nutrition = Consumable.Nutrition.valueOf(key);
            int weight = compoundNBT.getInt(key);
            map.put(nutrition, weight);
        }
        return new NutritionGene(map);
    }

    @Override
    protected CompoundNBT serialize(NutritionGene gene, CompoundNBT compoundNBT) {
        for (Map.Entry<Consumable.Nutrition, Integer> e : gene.getRequiredNutrition().entrySet()) {
            compoundNBT.putInt(e.getKey().name(), e.getValue());
        }
        return compoundNBT;
    }
}
