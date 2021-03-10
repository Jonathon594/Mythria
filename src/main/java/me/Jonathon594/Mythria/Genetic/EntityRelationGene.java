package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;

import java.util.Arrays;
import java.util.List;

public class EntityRelationGene extends Gene {
    private final List<EntityType<?>> entityTypes;
    private final Relationship relationship;

    public EntityRelationGene(Relationship relationship, EntityType<?>... entityTypes) {
        super(GeneType.ENTITY_RELATION);
        this.entityTypes = Arrays.asList(entityTypes);
        this.relationship = relationship;
    }

    public EntityRelationGene(Relationship relationship, List<EntityType<?>> entityTypes) {
        super(GeneType.ENTITY_RELATION);
        this.entityTypes = entityTypes;
        this.relationship = relationship;
    }

    public List<EntityType<?>> getEntityTypes() {
        return entityTypes;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    @Override
    public GeneSerializer<EntityRelationGene> getSerializer() {
        return GeneSerializers.ENTITY_RELATION;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }

    public enum Relationship {
        LOVE, TRUCE, FEAR, HATE
    }
}
