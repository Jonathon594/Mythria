package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.IntStatGene;
import net.minecraft.nbt.CompoundNBT;

public class IntStatSerializer extends GeneSerializer<IntStatGene> {
    public IntStatSerializer(String name) {
        super(name);
    }

    @Override
    public IntStatGene deserialize(CompoundNBT compoundNBT) {
        Gene.GeneType type = Gene.GeneType.valueOf(compoundNBT.getString("Type"));
        int value = compoundNBT.getInt("Value");
        return new IntStatGene(type, value);
    }

    @Override
    public CompoundNBT serialize(IntStatGene gene, CompoundNBT compoundNBT) {
        compoundNBT.putString("Type", gene.getType().name());
        compoundNBT.putInt("Value", gene.getValue());
        return compoundNBT;
    }

}
