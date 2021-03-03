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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class LumberingPerks implements IPerkRegistry {
    public static final Perk BASIC_LUMBERING = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("basic_lumbering", type, MythriaItems.BRONZE_AXE, MythicSkills.WOODCUTTING, 0,
                        new ResourceLocation("minecraft:textures/block/oak_log.png"))
                        .setDisplayName("Basic Lumbering").setDescription("With an axe even the toughest of wood can be cut.")
                        .addBreakableBlockTag(BlockTags.LOGS.getName())
                        .addPerkTypeUnlock(PerkType.CARPENTRY)
                        .addRequiredAttribute(Attribute.STRENGTH, 1)
        );
    }
}
