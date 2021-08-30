package me.Jonathon594.Mythria.DataTypes.Genetic.Gene;

import me.Jonathon594.Mythria.DataTypes.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;

public class LifeSpanGene extends Gene {
    private final HashMap<LifeStage, Integer> lifeMap = new HashMap<>();
    private final boolean immortal;

    public LifeSpanGene(Integer childAge, Integer adolescentAge, Integer adultAge, Integer middleAge, Integer elderlyAge, boolean immortal) {
        super(GeneType.LIFESPAN);
        this.immortal = immortal;
        lifeMap.put(LifeStage.CHILD, childAge);
        lifeMap.put(LifeStage.ADOLESCENT, adolescentAge);
        lifeMap.put(LifeStage.ADULT, adultAge);
        lifeMap.put(LifeStage.MIDDLE_AGE, middleAge);
        lifeMap.put(LifeStage.ELDERLY, elderlyAge);
    }

    public LifeSpanGene(CompoundNBT compoundNBT) {
        super(GeneType.LIFESPAN);
        for (LifeStage stage : LifeStage.values()) {
            if (compoundNBT.contains(stage.name())) {
                lifeMap.put(stage, compoundNBT.getInt(stage.name()));
            }
        }
        immortal = compoundNBT.getBoolean("immortal");
    }

    @Override
    public GeneSerializer<LifeSpanGene> getSerializer() {
        return GeneSerializers.LIFESPAN;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }

    public int getStage(LifeStage stage) {
        if (stage.equals(LifeStage.INFANT)) return 0;
        return lifeMap.get(stage);
    }

    public HashMap<LifeStage, Integer> getStages() {
        return lifeMap;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public enum LifeStage {
        INFANT, CHILD, ADOLESCENT, ADULT, MIDDLE_AGE, ELDERLY
    }
}
