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
public class BluntWeaponPerks implements IPerkRegistry {
    public static final Perk BLUNT_WEAPONRY = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("blunt_weaponry", type, MythriaItems.OAK_CLUB, null, 0,
                        new ResourceLocation("minecraft:textures/block/nether_bricks.png"))
                        .withDescription("A stronger offense is the best offence.")
                        .addRequiredAttribute(Attribute.STRENGTH, 3)
                        .addRequiredAttribute(Attribute.ENDURANCE, 2),

                new Perk("basic_hammers", type, MythriaItems.TIN_HAMMER, Skill.HEAVY_WEAPONS, 0, () -> BLUNT_WEAPONRY)
                        .setDisplayName("Basic Hammers")
                        .withDescription("Hammers are heavy weapons designed to concuss and break armor.")
                        .addAttributeFlag(AttributeFlag.HAMMER_ABILITY_CRIT)
                        .addRequiredAttribute(Attribute.STRENGTH, 5)
        );
    }
}
