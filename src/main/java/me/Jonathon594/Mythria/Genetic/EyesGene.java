package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Enum.Gender;

public class EyesGene extends SkinPartGene {
    public EyesGene(SkinPart skinPart, Gender gender) {
        super(skinPart, GeneType.EYES, gender);
    }
}
