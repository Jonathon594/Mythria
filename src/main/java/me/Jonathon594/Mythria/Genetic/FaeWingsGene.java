package me.Jonathon594.Mythria.Genetic;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Ability.Abilities;
import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public class FaeWingsGene extends Gene implements ISkinPartGene, IAbilityGene, ISpecialAbilitiesGene, ISlotLockingGene {
    private SkinPart skinPart;

    public FaeWingsGene(SkinPart skinPart) {
        super(GeneType.WINGS);
        this.skinPart = skinPart;
    }

    @Override
    public SkinPart getSkinPart() {
        return skinPart;
    }

    @Override
    public void setSkinPart(SkinPart skinPart) {
        this.skinPart = skinPart;
    }

    @Override
    public Ability getAbility() {
        return Abilities.FAE_FLIGHT;
    }

    @Override
    public List<SpecialAbility> getSpecialAbilities() {
        return ImmutableList.of(SpecialAbility.GLIDING);
    }

    @Override
    public List<EquipmentSlotType> getLockedSlots() {
        return ImmutableList.of(EquipmentSlotType.CHEST, EquipmentSlotType.LEGS);
    }

    @Override
    public GeneSerializer<FaeWingsGene> getSerializer() {
        return GeneSerializers.FAE_WINGS;
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }
}
