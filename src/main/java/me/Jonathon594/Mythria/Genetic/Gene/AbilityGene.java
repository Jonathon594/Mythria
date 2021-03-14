package me.Jonathon594.Mythria.Genetic.Gene;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.nbt.CompoundNBT;

public class AbilityGene extends Gene implements IAbilityGene {
    private final Ability ability;

    public AbilityGene(Ability ability) {
        super(GeneType.ABILITY);
        this.ability = ability;
    }

    @Override
    public Ability getAbility() {
        return ability;
    }

    @Override
    public GeneSerializer<AbilityGene> getSerializer() {
        return GeneSerializers.ABILITY;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }
}
