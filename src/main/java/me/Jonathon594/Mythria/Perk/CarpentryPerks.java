package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class CarpentryPerks implements IPerkRegistry {
    public static final Perk CARPENTRY = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("carpentry", type, Blocks.OAK_LOG, Skill.CARPENTRY, 0,
                        new ResourceLocation("minecraft:textures/block/oak_planks.png"))
                        .withDescription("Wood is a versatile material that is quite strong.")
                        .addPlaceableBlockTag(BlockTags.LOGS.getName())
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 3)
                        .addRequiredAttribute(Attribute.STRENGTH, 2),

                new Perk("advanced_carpentry", type, MythriaItems.OAK_PLANK, Skill.CARPENTRY, 10, () -> CARPENTRY)
                        .setDisplayName("Advanced Carpentry").withDescription("Cutting wood into straight precise pieces makes a very strong and light building material.")
                        .addCraftableItemTag(new MythriaResourceLocation("planks"))
                        .addCraftableBlockTag(BlockTags.PLANKS.getName())
                        .addCraftableBlockTag(BlockTags.WOODEN_STAIRS.getName())
                        .addCraftableBlockTag(BlockTags.WOODEN_SLABS.getName())
                        .addCraftableBlockTag(BlockTags.WOODEN_FENCES.getName())
                        .addPlaceableBlockTag(BlockTags.PLANKS.getName())
                        .addPlaceableBlockTag(BlockTags.WOODEN_STAIRS.getName())
                        .addPlaceableBlockTag(BlockTags.WOODEN_SLABS.getName())
                        .addPlaceableBlockTag(BlockTags.WOODEN_FENCES.getName())
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 5)
                        .addRequiredAttribute(Attribute.DEXTERITY, 2)
        );
    }
}
