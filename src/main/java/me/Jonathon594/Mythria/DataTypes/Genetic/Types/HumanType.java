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

public class HumanType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 250),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 250),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.FRUIT, 100),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.DAIRY, 200),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 200)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    private static final LifeSpanGene LIFESPAN = new LifeSpanGene(3, 11, 16, 40, 74, false);

    public HumanType() {
        super("human", "Human", new SpawnPos(3710, -4925), () -> new Genetic(GeneticTypes.HUMAN, 4, 100, 0.0, 55,
                1.5, 10, 14, 0, NUTRITION, LIFESPAN));
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
                SkinParts.HUMAN_SKIN_WHITE,
                SkinParts.HUMAN_SKIN_TAN,
                SkinParts.HUMAN_SKIN_MEDIUM,
                SkinParts.HUMAN_SKIN_DARK,
                SkinParts.HUMAN_SKIN_BLACK);
    }
}
