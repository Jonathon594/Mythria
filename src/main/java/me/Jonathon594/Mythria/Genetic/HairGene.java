package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Enum.Gender;

public class HairGene extends SkinPartGene {
    public HairGene(SkinPart skinPart, Gender gender) {
        super(skinPart, GeneType.HAIR, gender);
    }
}
