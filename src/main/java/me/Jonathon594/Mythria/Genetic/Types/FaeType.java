package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.FaeWingsGene;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Managers.SkinParts;

import java.util.List;

public class FaeType extends GeneticType {
    public FaeType() {
        super("fae", "Fae", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.FAE, -10, 80, 0.02,
                40, 1.0, 80, 284, 11, 1)
                .withExtraGene(new FaeWingsGene(SkinParts.FAE_WINGS_BLUE)));
        setSpecialSkinPartType(SkinPart.Type.WINGS);
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.HUMAN_SKIN_WHITE,
                SkinParts.HUMAN_SKIN_TAN,
                SkinParts.HUMAN_SKIN_MEDIUM);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
    }
}
