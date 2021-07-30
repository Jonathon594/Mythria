package me.Jonathon594.Mythria.DataTypes.Genetic.Gene;

import me.Jonathon594.Mythria.DataTypes.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.nbt.CompoundNBT;

public class SkinPartGene extends Gene implements ISkinPartGene {
    private SkinPart skinPart;

    public SkinPartGene(SkinPart skinPart, GeneType type) {
        super(type);
        this.skinPart = skinPart;
    }

    @Override
    public GeneSerializer<SkinPartGene> getSerializer() {
        return GeneSerializers.SKIN_PART;
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
