package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImmunityGene extends Gene {
    private final List<String> damageSources;

    public ImmunityGene(DamageSource... damageSources) {
        super(GeneType.IMMUNITY);
        this.damageSources = new ArrayList<>();
        for (int i = 0; i < damageSources.length; i++) {
            this.damageSources.add(damageSources[i].damageType);
        }
    }

    public ImmunityGene(String... damageSources) {
        super(GeneType.IMMUNITY);
        this.damageSources = Arrays.asList(damageSources);
    }

    public ImmunityGene(List<String> damageSources) {
        super(GeneType.IMMUNITY);
        this.damageSources = damageSources;
    }


    public List<String> getDamageSources() {
        return damageSources;
    }

    @Override
    public GeneSerializer<ImmunityGene> getSerializer() {
        return GeneSerializers.IMMUNITY;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }
}
