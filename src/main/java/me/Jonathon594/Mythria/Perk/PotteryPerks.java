package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class PotteryPerks implements IPerkRegistry {
    public static final Perk BASIC_POTTERY = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("basic_pottery", type, MythriaItems.CLAY_BRICK, MythicSkills.POTTERY, 0,
                        new ResourceLocation("minecraft:textures/block/terracotta.png"))
                        .setDisplayName("Basic Pottery").setDescription("Clay is easily formed into other shapes, perhaps applying heat can change that?")
                        .addCraftableItemTag(new MythriaResourceLocation("clay"))
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 3)
                        .addRequiredAttribute(Attribute.DEXTERITY, 2)
        );
    }
}
