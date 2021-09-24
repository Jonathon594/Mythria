package me.Jonathon594.Mythria.DataTypes.Genetic.Gene;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public class SaerkiTailGene extends Gene implements ISkinPartGene, ISlotLockingGene, ISpecialAbilitiesGene {
    private SkinPart skinPart;

    public SaerkiTailGene(SkinPart skinPart) {
        super(GeneType.SAERKI_TAIL);
        this.skinPart = skinPart;
    }

    @Override
    public List<EquipmentSlotType> getLockedSlots() {
        return ImmutableList.of(EquipmentSlotType.LEGS, EquipmentSlotType.FEET);
    }

    @Override
    public GeneSerializer<SaerkiTailGene> getSerializer() {
        return GeneSerializers.SAERKI_TAIL;
    }

    @Override
    public List<SpecialAbility> getSpecialAbilities() {
        return ImmutableList.of(SpecialAbility.FORCE_SWIMMING);
    }

    @Override
    public CompoundNBT toNBT(boolean writeSerializer) {
        return getSerializer().serialize(this, writeSerializer);
    }

    @Override
    public SkinPart getSkinPart() {
        return skinPart;
    }

    @Override
    public void setSkinPart(SkinPart skinPart) {
        this.skinPart = skinPart;
    }
}
