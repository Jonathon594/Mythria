package me.Jonathon594.Mythria.Genetic.Types;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Genetic.*;
import me.Jonathon594.Mythria.Managers.SkinParts;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class SkaerenType extends GeneticType {
    public SkaerenType() {
        super("skaeren", "Skaeren", SpawnPos.ZERO, () -> new Genetic(GeneticTypes.SKAEREN, 0, 100, 0.01,
                60, 1.0, 40, 210, 20, 1)
                .withExtraGene(new ImmunityGene(DamageSource.IN_FIRE, DamageSource.ON_FIRE, DamageSource.HOT_FLOOR,
                        DamageSource.LAVA))
                .withExtraGene(new EntityRelationGene(EntityRelationGene.Relationship.TRUCE, EntityType.GHAST,
                        EntityType.BLAZE)));
        setSpawnDimension(World.THE_NETHER);
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
        return ImmutableList.of(SkinParts.SKAEREN_CLOTHES_PRIMITIVE);
    }
}
