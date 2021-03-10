package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Managers.SkinParts;

import java.util.List;

public class OrcType extends GeneticType {
    public OrcType() {
        super("orc", "Orc", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.ORC, 20, 140, -0.01,
                120, 0.8, 5, 48, 16, 0));
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.ORC_SKIN_LIGHT,
                SkinParts.ORC_SKIN_TAN,
                SkinParts.ORC_SKIN_MEDIUM,
                SkinParts.ORC_SKIN_DARK);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
    }
}
