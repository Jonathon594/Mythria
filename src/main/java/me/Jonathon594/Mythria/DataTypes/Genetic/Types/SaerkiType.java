package me.Jonathon594.Mythria.DataTypes.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Ability.PassiveFaeFlightAbility;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.FaeWingsGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.LifeSpanGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.SaerkiTailGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.SpecialAbilityGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.DataTypes.Origins.Origins;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaerkiType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 1000)
            //new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 300)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    private static final LifeSpanGene LIFESPAN = new LifeSpanGene(3, 11, 16, 40, 74, false);

    public SaerkiType() {
        super("saerki", "Saerki", () -> new Genetic(GeneticTypes.SAERKI, 0, 120, 0.0,
                1.5, 45, 1.0, 80, 12.5, 1, NUTRITION, LIFESPAN)
                .withExtraGene(new SaerkiTailGene(SkinParts.SAERKI_TAIL_BLUE))
                .withExtraGene(new SpecialAbilityGene(ImmutableList.of(SpecialAbility.FORCE_NIGHT_VISION)))
                .withExtraGene(new SpecialAbilityGene(ImmutableList.of(SpecialAbility.WATER_BREATHING))));
        setSpecialSkinPartType(SkinPart.Type.SAERKI_TAIL);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_SEASHELL_BRA, SkinParts.CLOTHES_SAERKI_SHIRTLESS);
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

    @Override
    public List<SkinPart> getAllowedHairs() {
        return SkinParts.getSkinPartsFor(SkinPart.Type.HAIR); //todo fix
    }
}
