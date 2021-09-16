package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class BladedWeaponPerks implements IPerkRegistry {
    public static final Perk BLADED_WEAPONRY = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("bladed_weaponry", type, MythriaItems.BONE_DAGGER, null, 0,
                        new ResourceLocation("minecraft:textures/block/nether_bricks.png"))
                        .withDescription("A strong offense is the best defence.")
                        .addRequiredAttribute(Attribute.DEXTERITY, 3)
                        .addRequiredAttribute(Attribute.STRENGTH, 2),

                new Perk("basic_swordsmanship", type, MythriaItems.TIN_SWORD, Skill.SWORDS, 0, () -> BLADED_WEAPONRY)
                        .setDisplayName("Basic Swordsmanship")
                        .withDescription("Swordsmanship is all about finesse.")
                        .addAttributeFlag(AttributeFlag.SWORD_ABILITY_SWEEP)
                        .addRequiredAttribute(Attribute.DEXTERITY, 5),

                new Perk("basic_axes", type, MythriaItems.TIN_AXE, Skill.AXES, 0, () -> BLADED_WEAPONRY)
                        .setDisplayName("Basic Axes")
                        .withDescription("Axes are heavier weapons designed to overpower an enemy.")
                        .addAttributeFlag(AttributeFlag.AXE_ABILITY_CRIT)
                        .addRequiredAttribute(Attribute.STRENGTH, 5),

                new Perk("basic_daggers", type, MythriaItems.TIN_DAGGER, Skill.DAGGERS, 0, () -> BLADED_WEAPONRY)
                        .setDisplayName("Basic Daggers")
                        .withDescription("Daggers are smaller and faster blades for quick strikes.")
                        .addAttributeFlag(AttributeFlag.DAGGER_ABILITY_STAB)
                        .addRequiredAttribute(Attribute.DEXTERITY, 5)
        );
    }
}
