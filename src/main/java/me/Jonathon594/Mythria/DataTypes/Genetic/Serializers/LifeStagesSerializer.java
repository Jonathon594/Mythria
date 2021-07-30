package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.LifeSpanGene;
import net.minecraft.nbt.CompoundNBT;

public class LifeStagesSerializer extends GeneSerializer<LifeSpanGene> {
    public LifeStagesSerializer(String name) {
        super(name);
    }

    @Override
    public LifeSpanGene deserialize(CompoundNBT compoundNBT) {
        return new LifeSpanGene(compoundNBT);
    }

    @Override
    public CompoundNBT serialize(LifeSpanGene gene, CompoundNBT compoundNBT) {
        for (LifeSpanGene.LifeStage stage : gene.getStages().keySet()) {
            compoundNBT.putInt(stage.name(), gene.getStage(stage));
        }
        compoundNBT.putBoolean("immortal", gene.isImmortal());
        return compoundNBT;
    }

}
