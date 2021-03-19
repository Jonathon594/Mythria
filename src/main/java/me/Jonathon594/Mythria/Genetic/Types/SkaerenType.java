package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Genetic.Gene.EntityAttitudeGene;
import me.Jonathon594.Mythria.Genetic.Gene.ImmunityGene;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGift;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGifts;
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

    public SkaerenType() {
        super("skaeren", "Skaeren", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.SKAEREN, 0, 100, 0.01,
                60, 1.0, 40, 210, 20, 1, NUTRITION)
                .withExtraGene(new ImmunityGene(DamageSource.IN_FIRE, DamageSource.ON_FIRE, DamageSource.HOT_FLOOR,
                        DamageSource.LAVA))
                .withExtraGene(new EntityAttitudeGene(EntityAttitudeGene.Attitude.TRUCE, EntityType.GHAST,
                        EntityType.BLAZE)), 15);
        setSpawnDimension(World.THE_NETHER);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.SKAEREN_CLOTHES_PRIMITIVE);
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.HUMAN_SKIN_TAN,
                SkinParts.HUMAN_SKIN_MEDIUM,
                SkinParts.HUMAN_SKIN_DARK,
                SkinParts.HUMAN_SKIN_BLACK);
    }

    @Override
    public Collection<SpawnGift> getAllowedSpawnGifts() {
        return ImmutableList.of(SpawnGifts.OLD_PICKAXE_NETHER, SpawnGifts.OLD_AXE_NETHER, SpawnGifts.OLD_SWORD_NETHER,
                SpawnGifts.BANDOLIER);
    }
}
