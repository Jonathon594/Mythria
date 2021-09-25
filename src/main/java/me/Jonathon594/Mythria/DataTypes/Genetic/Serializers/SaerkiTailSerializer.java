package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.SaerkiTailGene;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class SaerkiTailSerializer extends GeneSerializer<SaerkiTailGene> {
    public SaerkiTailSerializer(String name) {
        super(name);
    }

    @Override
    public SaerkiTailGene deserialize(CompoundNBT compoundNBT) {
        SkinPart skinPart = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(compoundNBT.getString("Value")));
        return new SaerkiTailGene(skinPart);
    }

    @Override
    public CompoundNBT serialize(SaerkiTailGene gene, CompoundNBT compoundNBT) {
        compoundNBT.putString("Value", gene.getSkinPart().getRegistryName().toString());
        return compoundNBT;
    }
}
