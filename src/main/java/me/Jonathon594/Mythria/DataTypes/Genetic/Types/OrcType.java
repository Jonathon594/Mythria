package me.Jonathon594.Mythria.DataTypes.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.LifeSpanGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.DataTypes.Origins.Origins;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrcType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 700),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 100),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.FRUIT, 50),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.DAIRY, 50),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 100)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    private static final LifeSpanGene LIFESPAN = new LifeSpanGene(2, 8, 12, 24, 48, false);

    public OrcType() {
        super("orc", "Orc", new SpawnPos(3710, -4925), () -> new Genetic(GeneticTypes.ORC, 20, 140, -0.01,
                100, 0.8, 5, 16, 0, NUTRITION, LIFESPAN));
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
    }

    @Override
    public Collection<Origin> getAllowedOrigins() {
        return ImmutableList.of(Origins.SCAVENGER);
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.ORC_SKIN_LIGHT,
                SkinParts.ORC_SKIN_TAN,
                SkinParts.ORC_SKIN_MEDIUM,
                SkinParts.ORC_SKIN_DARK);
    }
}
