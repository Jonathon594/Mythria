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

public class OrcType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 700),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 100),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.FRUIT, 50),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.DAIRY, 50),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 100)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    public OrcType() {
        super("orc", "Orc", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.ORC, 20, 140, -0.01,
                120, 0.8, 5, 48, 16, 0, NUTRITION), 14);
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.ORC_SKIN_LIGHT,
                SkinParts.ORC_SKIN_TAN,
                SkinParts.ORC_SKIN_MEDIUM,
                SkinParts.ORC_SKIN_DARK);
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
