package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.AbilityGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.SpecialAbilityGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

public class SpecialAbilitySerializer extends GeneSerializer<SpecialAbilityGene> {
    public SpecialAbilitySerializer(String name) {
        super(name);
    }

    @Override
    public SpecialAbilityGene deserialize(CompoundNBT compoundNBT) {
        List<SpecialAbility> specialAbilities = compoundNBT.getList("Value", 8).stream()
        .map(inbt -> SpecialAbility.valueOf(inbt.getString())).collect(Collectors.toList());
        return new SpecialAbilityGene(specialAbilities);
    }

    @Override
    public CompoundNBT serialize(SpecialAbilityGene gene, CompoundNBT compoundNBT) {
        ListNBT list = new ListNBT();
        list.addAll(gene.getSpecialAbilities().stream().map(specialAbility -> StringNBT.valueOf(specialAbility.name())).collect(Collectors.toList()));
        compoundNBT.put("Value", list);
        return compoundNBT;
    }
}
