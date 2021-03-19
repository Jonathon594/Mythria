package me.Jonathon594.Mythria.Genetic.Serializers;

import me.Jonathon594.Mythria.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.Genetic.Gene.SkinPartGene;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class SkinPartSerializer extends GeneSerializer<SkinPartGene> {
    public SkinPartSerializer(String name) {
        super(name);
    }

    @Override
    public SkinPartGene deserialize(CompoundNBT compoundNBT) {
        Gene.GeneType type = Gene.GeneType.valueOf(compoundNBT.getString("Type"));
        SkinPart skinPart = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(compoundNBT.getString("Value")));
        return new SkinPartGene(skinPart, type);
    }

    @Override
    public CompoundNBT serialize(SkinPartGene gene, CompoundNBT compoundNBT) {
        compoundNBT.putString("Type", gene.getType().name());
        compoundNBT.putString("Value", gene.getSkinPart().getRegistryName().toString());
        return compoundNBT;
    }
}
