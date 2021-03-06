package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Enum.Gender;

public abstract class SkinPartGene extends Gene {
    private final SkinPart skinPart;
    private final Gender gender;

    public SkinPartGene(SkinPart skinPart, GeneType type, Gender gender) {
        super(type);
        this.skinPart = skinPart;
        this.gender = gender;
    }

    public SkinPart getSkinPart() {
        return skinPart;
    }

    public Gender getGender() {
        return gender;
    }
}
