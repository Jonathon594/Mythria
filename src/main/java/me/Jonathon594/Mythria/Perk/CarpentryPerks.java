package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class CarpentryPerks implements IPerkRegistry {
    public static final Perk BASIC_CARPENTRY = null;
    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("basic_carpentry", type, Blocks.OAK_LOG, MythicSkills.CARPENTRY, 0,
                        new ResourceLocation("minecraft:textures/block/oak_planks.png"))
                        .setDisplayName("Basic Carpentry").setDescription("Wood is a versatile material that is quite strong.")
                        .addPlaceableBlockTag(BlockTags.LOGS.getName())
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 2)
                        .addRequiredAttribute(Attribute.STRENGTH, 2)
        );
    }
}
