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
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;
@ObjectHolder(Mythria.MODID)
public class MiningPerks implements IPerkRegistry {
    public static final Perk EXCAVATION = null;
    public static final Perk STONE_MINING = null;
    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("excavation", type, MythriaItems.BRONZE_SHOVEL, MythicSkills.MINING, 0,
                        new ResourceLocation("minecraft:textures/block/stone.png"))
                        .setDisplayName("Excavation")
                        .setDescription("With a shovel you can move loose soils.")
                        .addRequiredAttribute(Attribute.ENDURANCE, 3)
                        .addRequiredAttribute(Attribute.STRENGTH, 1)
                        .addBreakable(Blocks.GRAVEL, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRASS_PATH),

                new Perk("stone_mining", type, MythriaItems.BRONZE_PICKAXE, MythicSkills.MINING, 10, () -> EXCAVATION)
                        .setDisplayName("Stone Mining")
                        .setDescription("With a pickaxe even something as hard as stone can be broken up.")
                        .addRequiredAttribute(Attribute.ENDURANCE, 5)
                        .addRequiredAttribute(Attribute.STRENGTH, 3)
                        .addBreakableBlockTag(BlockTags.BASE_STONE_OVERWORLD.getName())
        );
    }
}
