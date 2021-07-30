package me.Jonathon594.Mythria.DataTypes.Genetic.Gene;

import me.Jonathon594.Mythria.DataTypes.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.nbt.CompoundNBT;

public class DoubleStatGene extends Gene {
    private final double value;

    public DoubleStatGene(GeneType type, double value) {
        super(type);
        this.value = value;
    }

    @Override
    public GeneSerializer<DoubleStatGene> getSerializer() {
        return GeneSerializers.DOUBLE_STAT;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }

    public double getValue() {
        return value;
    }
}
