package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.AbilityGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class AbilitySerializer extends GeneSerializer<AbilityGene> {
    public AbilitySerializer(String name) {
        super(name);
    }

    @Override
    public AbilityGene deserialize(CompoundNBT compoundNBT) {
        Gene.GeneType type = Gene.GeneType.valueOf(compoundNBT.getString("Type"));
        Ability ability = MythriaRegistries.ABILITIES.getValue(new ResourceLocation(compoundNBT.getString("Value")));
        return new AbilityGene(ability);
    }

    @Override
    public CompoundNBT serialize(AbilityGene gene, CompoundNBT compoundNBT) {
        compoundNBT.putString("Type", gene.getType().name());
        compoundNBT.putString("Value", gene.getAbility().getRegistryName().toString());
        return compoundNBT;
    }
}
