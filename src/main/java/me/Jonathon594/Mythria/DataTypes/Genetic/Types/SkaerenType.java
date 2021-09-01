package me.Jonathon594.Mythria.DataTypes.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.EntityAttitudeGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.ImmunityGene;
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
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkaerenType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 600),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 300),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 100)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    private static final LifeSpanGene LIFESPAN = new LifeSpanGene(3, 11, 16, 105, 210, false);

    public SkaerenType() {
        super("skaeren", "Skaeren", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.SKAEREN, 0, 100, 0.01,
                60, 1.0, 40, 20, 1, NUTRITION, LIFESPAN)
                .withExtraGene(new ImmunityGene(DamageSource.IN_FIRE, DamageSource.ON_FIRE, DamageSource.HOT_FLOOR,
                        DamageSource.LAVA))
                .withExtraGene(new EntityAttitudeGene(EntityAttitudeGene.Attitude.TRUCE, EntityType.GHAST,
                        EntityType.BLAZE)));
        setSpawnDimension(World.THE_NETHER);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.SKAEREN_CLOTHES_PRIMITIVE);
    }

    @Override
    public Collection<Origin> getAllowedOrigins() {
        return ImmutableList.of(Origins.SURVIVOR);
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
