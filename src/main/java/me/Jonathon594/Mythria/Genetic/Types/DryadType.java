package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Ability.Abilities;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.*;
import me.Jonathon594.Mythria.Managers.SkinParts;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;

import java.util.List;

public class DryadType extends GeneticType {
    public DryadType() {
        super("dryad", "Dryad", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.DRYAD, 8, 120, 0.01,
                80, 1.0, 200, 649, 14, -0.1)
                .setGenderBias(1.0).withExtraGene(new AbilityGene(Abilities.DRYAD_GROWTH))
                .withExtraGene(new EntityRelationGene(EntityRelationGene.Relationship.TRUCE, EntityType.WOLF,
                        EntityType.POLAR_BEAR))
                .withExtraGene(new ImmunityGene(DamageSource.CACTUS, DamageSource.SWEET_BERRY_BUSH))
                .withExtraGene(new VinesGene(SkinParts.DRYAD_VINES_OAK)));
        setSpecialSkinPartType(SkinPart.Type.DRYAD_VINES);
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
        return ImmutableList.of(SkinParts.CLOTHES_NUDE);
    }
}
