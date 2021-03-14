package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGift;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Skin.SkinParts;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGifts;

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

    public HumanType() {
        super("human", "Human", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.HUMAN, 4, 100, 0.0, 50,
                1.5, 10, 74, 14, 0, NUTRITION), 15);
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
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
    }

    @Override
    public Collection<SpawnGift> getAllowedSpawnGifts() {
        return ImmutableList.of(SpawnGifts.OLD_PICKAXE, SpawnGifts.OLD_AXE, SpawnGifts.OLD_HOE, SpawnGifts.OLD_SWORD,
                SpawnGifts.BANDOLIER);
    }
}
