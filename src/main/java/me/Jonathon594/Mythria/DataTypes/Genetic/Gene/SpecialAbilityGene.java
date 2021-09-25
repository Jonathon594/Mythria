package me.Jonathon594.Mythria.DataTypes.Genetic.Gene;

import me.Jonathon594.Mythria.DataTypes.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public class SpecialAbilityGene extends Gene implements ISpecialAbilitiesGene {
    private final List<SpecialAbility> specialAbilities;

    public SpecialAbilityGene(List<SpecialAbility> specialAbilities) {
        super(GeneType.SPECIAL_ABILITY);
        this.specialAbilities = specialAbilities;
    }

    @Override
    public GeneSerializer<SpecialAbilityGene> getSerializer() {
        return GeneSerializers.SPECIAL_ABILITY;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }

    @Override
    public List<SpecialAbility> getSpecialAbilities() {
        return specialAbilities;
    }
}
