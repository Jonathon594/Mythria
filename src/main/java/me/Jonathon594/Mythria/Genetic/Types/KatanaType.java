package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.EntityRelationGene;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.GeneticTypes;
import me.Jonathon594.Mythria.Managers.SkinParts;
import net.minecraft.entity.EntityType;

import java.util.List;

public class KatanaType extends GeneticType {
    public KatanaType() {
        super("katana", "Ka'Tana", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.KATANA, -4, 300, 0.02,
                60, 0.85, 20, 34, 14, 1)
                .withExtraGene(new EntityRelationGene(EntityRelationGene.Relationship.FEAR, EntityType.CREEPER)));
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
    public List<SkinPart> getAllowedClothes() {
        return ImmutableList.of(SkinParts.CLOTHES_PRIMITIVE);
    }
}
