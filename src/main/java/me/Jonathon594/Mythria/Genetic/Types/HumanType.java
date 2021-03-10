package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Managers.SkinParts;

import java.util.List;

public class HumanType extends GeneticType {
    public HumanType() {
        super("human", "Human", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.HUMAN, 4, 100, 0.0, 50,
                1.5, 10, 74, 14, 0));
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.HUMAN_SKIN_WHITE,
                SkinParts.HUMAN_SKIN_TAN,
                SkinParts.HUMAN_SKIN_MEDIUM,
                SkinParts.HUMAN_SKIN_DARK,
                SkinParts.HUMAN_SKIN_BLACK);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
    }
}
