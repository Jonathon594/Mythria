package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Genetic.Gene.EntityAttitudeGene;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGift;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGifts;
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

    public KatanaType() {
        super("katana", "Ka'Tana", new SpawnPos(3119, -3909), () -> new Genetic(GeneticTypes.KATANA, -4, 300, 0.02,
                60, 0.85, 20, 34, 14, 1, NUTRITION)
                .withExtraGene(new EntityAttitudeGene(EntityAttitudeGene.Attitude.FEAR, EntityType.CREEPER)), 7);
    }

    @Override
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
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
        return ImmutableList.of(SpawnGifts.OLD_PICKAXE, SpawnGifts.OLD_AXE, SpawnGifts.OLD_HOE, SpawnGifts.OLD_SWORD,
                SpawnGifts.BANDOLIER);
    }
}
