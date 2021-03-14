package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Ability.Abilities;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGift;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.Gene.AbilityGene;
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

public class ElfType extends GeneticType {
    private static final Map NUTRITION = Stream.of(
            new AbstractMap.SimpleEntry(Consumable.Nutrition.MEAT, 250),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.VEGETABLE, 250),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.FRUIT, 100),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.DAIRY, 200),
            new AbstractMap.SimpleEntry(Consumable.Nutrition.STARCH, 200)
    ).collect(Collectors.toMap(simpleEntry -> simpleEntry.getKey(), simpleEntry -> simpleEntry.getValue()));

    public ElfType() {
        super("elf", "Elf", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.ELF, -2, 80, 0.01, 45,
                1.0, 50, 0, 12, 0.5, NUTRITION)
                .withExtraGene(new AbilityGene(Abilities.ELF_HEALING)), 14);
    }

    @Override
    public List<SkinPart> getAllowedSkins() {
        return ImmutableList.of(
                SkinParts.HUMAN_SKIN_WHITE,
                SkinParts.HUMAN_SKIN_TAN,
                SkinParts.HUMAN_SKIN_MEDIUM);
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
