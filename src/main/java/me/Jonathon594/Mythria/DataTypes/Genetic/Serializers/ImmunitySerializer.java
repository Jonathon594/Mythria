package me.Jonathon594.Mythria.DataTypes.Genetic.Serializers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.ImmunityGene;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

import java.util.ArrayList;
import java.util.List;

public class ImmunitySerializer extends GeneSerializer<ImmunityGene> {
    public ImmunitySerializer(String name) {
        super(name);
    }

    @Override
    public ImmunityGene deserialize(CompoundNBT compoundNBT) {
        List<INBT> list = compoundNBT.getList("Values", 8);
        List<String> damageSources = new ArrayList<>();
        list.forEach(inbt -> damageSources.add(inbt.getString()));
        return new ImmunityGene(damageSources);
    }

    @Override
    public CompoundNBT serialize(ImmunityGene gene, CompoundNBT compoundNBT) {
        ListNBT list = new ListNBT();
        for (String s : gene.getDamageSources()) {
            list.add(StringNBT.valueOf(s));
        }
        compoundNBT.put("Values", list);
        return compoundNBT;
    }
}
