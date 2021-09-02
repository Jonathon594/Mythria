package me.Jonathon594.Mythria.DataTypes.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Ability.Abilities;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.AbilityGene;
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

public class ElfType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 250),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 250),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.FRUIT, 100),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.DAIRY, 200),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 200)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    private static final LifeSpanGene LIFESPAN = new LifeSpanGene(3, 11, 16, 523, 860, true);

    public ElfType() {
        super("elf", "Elf", new SpawnPos(3119, -3909), () -> new Genetic(GeneticTypes.ELF, -2, 80, 0.01,
                50, 1.0, 50, 12, 0.5, NUTRITION, LIFESPAN)
                .withExtraGene(new AbilityGene(Abilities.ELF_HEALING)));
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
    }

    @Override
    public Collection<Origin> getAllowedOrigins() {
        return ImmutableList.of(Origins.SURVIVOR);
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.HUMAN_SKIN_WHITE,
                SkinParts.HUMAN_SKIN_TAN,
                SkinParts.HUMAN_SKIN_MEDIUM);
    }
}
