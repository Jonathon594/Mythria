package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Enum.Gender;

public class SkinGene extends SkinPartGene {
    public SkinGene(SkinPart skinPart, Gender gender) {
        super(skinPart, GeneType.SKIN, gender);
    }
}
