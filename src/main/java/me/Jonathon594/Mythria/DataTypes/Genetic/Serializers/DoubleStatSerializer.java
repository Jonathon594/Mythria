package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.DoubleStatGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import net.minecraft.nbt.CompoundNBT;

public class DoubleStatSerializer extends GeneSerializer<DoubleStatGene> {
    public DoubleStatSerializer(String name) {
        super(name);
    }

    @Override
    public DoubleStatGene deserialize(CompoundNBT compoundNBT) {
        Gene.GeneType type = Gene.GeneType.valueOf(compoundNBT.getString("Type"));
        double value = compoundNBT.getDouble("Value");
        return new DoubleStatGene(type, value);
    }

    @Override
    public CompoundNBT serialize(DoubleStatGene gene, CompoundNBT compoundNBT) {
        compoundNBT.putString("Type", gene.getType().name());
        compoundNBT.putDouble("Value", gene.getValue());
        return compoundNBT;
    }
}
