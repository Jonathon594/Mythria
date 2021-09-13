package me.Jonathon594.Mythria.DataTypes.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Ability.Abilities;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.*;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.DataTypes.Origins.Origins;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DryadType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 600),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.FRUIT, 300),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 100)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    private static final LifeSpanGene LIFESPAN = new LifeSpanGene(3, 11, 16, 523, 649, false);

    public DryadType() {
        super("dryad", "Dryad", () -> new Genetic(GeneticTypes.DRYAD, 8, 120, 0.01,
                40, 1.0, 200, 14, 0.0, NUTRITION, LIFESPAN)
                .setGenderBias(1.0)
                .withExtraGene(new AbilityGene(Abilities.DRYAD_GROWTH))
                .withExtraGene(new AbilityGene(Abilities.DRYAD_ABSORB))
                .withExtraGene(new EntityAttitudeGene(EntityAttitudeGene.Attitude.TRUCE, EntityType.WOLF,
                        EntityType.POLAR_BEAR))
                .withExtraGene(new ImmunityGene(DamageSource.CACTUS, DamageSource.SWEET_BERRY_BUSH))
                .withExtraGene(new VinesGene(SkinParts.DRYAD_VINES_OAK)));
        setSpecialSkinPartType(SkinPart.Type.DRYAD_VINES);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_NUDE);
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
                SkinParts.HUMAN_SKIN_MEDIUM);
    }
}
