package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Ability.Abilities;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.AbilityGene;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Managers.SkinParts;

import java.util.List;

public class ElfType extends GeneticType {
    public ElfType() {
        super("elf", "Elf", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.ELF, -2, 80, 0.01, 45,
                1.0, 50, 0, 12, 0.5)
                .withExtraGene(new AbilityGene(Abilities.ELF_HEALING)));
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
}
