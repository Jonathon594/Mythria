package me.Jonathon594.Mythria.Genetic.Gene;

import me.Jonathon594.Mythria.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;

import java.util.Arrays;
import java.util.List;

public class EntityAttitudeGene extends Gene {
    private final List<EntityType<?>> entityTypes;
    private final Attitude attitude;

    public EntityAttitudeGene(Attitude attitude, EntityType<?>... entityTypes) {
        super(GeneType.ENTITY_RELATION);
        this.entityTypes = Arrays.asList(entityTypes);
        this.attitude = attitude;
    }

    public EntityAttitudeGene(Attitude attitude, List<EntityType<?>> entityTypes) {
        super(GeneType.ENTITY_RELATION);
        this.entityTypes = entityTypes;
        this.attitude = attitude;
    }

    public Attitude getAttitude() {
        return attitude;
    }

    public List<EntityType<?>> getEntityTypes() {
        return entityTypes;
    }

    @Override
    public GeneSerializer<EntityAttitudeGene> getSerializer() {
        return GeneSerializers.ENTITY_RELATION;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }

    public enum Attitude {
        LOVE, TRUCE, FEAR, HATE
    }


}
