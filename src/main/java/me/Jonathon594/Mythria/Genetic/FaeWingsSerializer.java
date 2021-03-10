package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class FaeWingsSerializer extends GeneSerializer<FaeWingsGene> {
    public FaeWingsSerializer(String name) {
        super(name);
    }

    @Override
    public FaeWingsGene deserialize(CompoundNBT compoundNBT) {
        SkinPart skinPart = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(compoundNBT.getString("Value")));
        return new FaeWingsGene(skinPart);
    }

    @Override
    public CompoundNBT serialize(FaeWingsGene gene, CompoundNBT compoundNBT) {
        compoundNBT.putString("Value", gene.getSkinPart().getRegistryName().toString());
        return compoundNBT;
    }
}
