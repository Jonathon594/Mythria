package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CombatPerks implements IPerkRegistry {
    public static final Perk BASIC_COMBAT = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("basic_combat", type, Items.IRON_SWORD, null, 0,
                        new ResourceLocation("minecraft:textures/block/nether_bricks.png"))
                        .setDisplayName("Basic Combat").setDescription("A strong offense is the best defence.")
                        .addRequiredAttribute(Attribute.STRENGTH, 3)
                        .addRequiredAttribute(Attribute.ENDURANCE, 2)
                        .addAttributeFlag(AttributeFlag.COMBAT_ABILITY_HEAVY_ATTACK)
        );
    }
}
