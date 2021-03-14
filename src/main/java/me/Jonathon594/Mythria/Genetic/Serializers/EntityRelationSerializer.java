package me.Jonathon594.Mythria.Genetic.Serializers;

import me.Jonathon594.Mythria.Genetic.Gene.EntityAttitudeGene;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class EntityRelationSerializer extends GeneSerializer<EntityAttitudeGene> {
    public EntityRelationSerializer(String name) {
        super(name);
    }

    @Override
    public EntityAttitudeGene deserialize(CompoundNBT compoundNBT) {
        List<INBT> list = compoundNBT.getList("Values", 8);
        List<EntityType<?>> types = new ArrayList<>();
        list.forEach(inbt -> types.add(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(inbt.getString()))));
        EntityAttitudeGene.Attitude attitude = EntityAttitudeGene.Attitude.valueOf(compoundNBT.getString("Relationship"));
        return new EntityAttitudeGene(attitude, types);
    }

    @Override
    public CompoundNBT serialize(EntityAttitudeGene gene, CompoundNBT compoundNBT) {
        ListNBT list = new ListNBT();
        for (EntityType<?> entityType : gene.getEntityTypes()) {
            list.add(StringNBT.valueOf(entityType.getRegistryName().toString()));
        }
        compoundNBT.put("Values", list);
        compoundNBT.putString("Relationship", gene.getAttitude().name());
        return compoundNBT;
    }
}
