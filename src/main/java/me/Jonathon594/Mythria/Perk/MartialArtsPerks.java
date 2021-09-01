package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class MartialArtsPerks implements IPerkRegistry {
    public static final Perk MARTIAL_ARTS = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("martial_arts", type, Items.OAK_PLANKS, MythicSkills.MARTIAL_ARTS, 0,
                        new ResourceLocation("minecraft:textures/block/nether_bricks.png"))
                        .addDescriptionLine("Using ones fists is a difficult but rewarding journey.")
                        .addAttributeFlag(AttributeFlag.UNARMED_ABILITY_FORCE_PUNCH)
                        .addRequiredAttribute(Attribute.DEXTERITY, 3)
                        .addRequiredAttribute(Attribute.STRENGTH, 2)
        );
    }
}
