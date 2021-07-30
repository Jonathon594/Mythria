package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class GeneSerializer<T extends Gene> extends ForgeRegistryEntry<GeneSerializer<? extends Gene>> {
    public GeneSerializer(String name) {
        this.setRegistryName(new MythriaResourceLocation(name));
    }

    public abstract T deserialize(CompoundNBT compoundNBT);

    public CompoundNBT serialize(T gene, boolean writeSerializer) {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (writeSerializer) compoundNBT.putString("Serializer", getRegistryName().toString());
        return serialize(gene, compoundNBT);
    }

    protected abstract CompoundNBT serialize(T gene, CompoundNBT compoundNBT);
}
