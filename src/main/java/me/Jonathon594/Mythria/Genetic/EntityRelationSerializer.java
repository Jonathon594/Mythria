package me.Jonathon594.Mythria.Genetic;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class EntityRelationSerializer extends GeneSerializer<EntityRelationGene> {
    public EntityRelationSerializer(String name) {
        super(name);
    }

    @Override
    public EntityRelationGene deserialize(CompoundNBT compoundNBT) {
        List<INBT> list = compoundNBT.getList("Values", 8);
        List<EntityType<?>> types = new ArrayList<>();
        list.forEach(inbt -> types.add(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(inbt.getString()))));
        EntityRelationGene.Relationship relationship = EntityRelationGene.Relationship.valueOf(compoundNBT.getString("Relationship"));
        return new EntityRelationGene(relationship, types);
    }

    @Override
    public CompoundNBT serialize(EntityRelationGene gene, CompoundNBT compoundNBT) {
        ListNBT list = new ListNBT();
        for (EntityType<?> entityType : gene.getEntityTypes()) {
            list.add(StringNBT.valueOf(entityType.getRegistryName().toString()));
        }
        compoundNBT.put("Values", list);
        compoundNBT.putString("Relationship", gene.getRelationship().name());
        return compoundNBT;
    }
}
