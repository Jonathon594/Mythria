package me.Jonathon594.Mythria.DataTypes.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.EntityAttitudeGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.LifeSpanGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.DataTypes.Origins.Origins;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
import net.minecraft.entity.EntityType;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KatanaType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 800),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 50),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.FRUIT, 50),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.DAIRY, 50),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 50)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    private static final LifeSpanGene LIFESPAN = new LifeSpanGene(2, 5, 8, 16, 34, false);

    public KatanaType() {
        super("katana", "Ka'Tana", () -> new Genetic(GeneticTypes.KATANA, -4, 300, 0.02,
                1.0, 40, 0.85, 20, 14, 1, NUTRITION, LIFESPAN)
                .withExtraGene(new EntityAttitudeGene(EntityAttitudeGene.Attitude.FEAR, EntityType.CREEPER)));
    }

    @Override
    public Collection<Origin> getAllowedOrigins() {
        return ImmutableList.of(Origins.SCAVENGER);
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.HUMAN_SKIN_TAN,
                SkinParts.HUMAN_SKIN_MEDIUM,
                SkinParts.HUMAN_SKIN_DARK,
                SkinParts.HUMAN_SKIN_BLACK);
    }
}
