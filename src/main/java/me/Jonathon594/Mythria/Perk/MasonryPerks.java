package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class MasonryPerks implements IPerkRegistry {
    public static final Perk MASONRY = null;
    public static final Perk STONE_CUTTING = null;
    public static final Perk STONE_CARVING = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("masonry", type, Blocks.STONE, Skill.MASONRY, 0,
                        new ResourceLocation("minecraft:textures/block/stone_bricks.png"))
                        .withDescription("Placing stones in a precise manner can build incredibly strong structures.")
                        .addPlaceableBlockTag(BlockTags.BASE_STONE_OVERWORLD.getName())
                        .addPlaceableBlockTag(BlockTags.BASE_STONE_NETHER.getName())
                        .addCraftable(Items.COBBLESTONE, Items.COBBLESTONE_SLAB, Items.COBBLESTONE_STAIRS, Items.COBBLESTONE_WALL, MythriaBlocks.COBBLESTONE_FURNACE)
                        .addPlaceable(Blocks.COBBLESTONE, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_WALL, MythriaBlocks.COBBLESTONE_FURNACE)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 2)
                        .addRequiredAttribute(Attribute.DEXTERITY, 1),

                new Perk("stone_cutting", type, MythriaItems.STONE_BRICK, Skill.MASONRY, 5, () -> MASONRY)
                        .setDisplayName("Stone Cutting")
                        .withDescription("A chisel can be used to cut straight lines in stone.")
                        .addCraftable(MythriaItems.STONE_BRICK)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 3),

                new Perk("brick_laying", type, Blocks.BRICKS, Skill.MASONRY, 5, () -> MASONRY)
                        .setDisplayName("Brick Laying")
                        .withDescription("Smaller, precisely cut pieces of stone or clay stacked on-top of one another.")
                        .addCraftable(Blocks.BRICKS, Blocks.BRICK_SLAB, Blocks.BRICK_STAIRS, Blocks.BRICK_WALL)
                        .addCraftable(Blocks.STONE_BRICKS, Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICK_WALL)
                        .addPlaceable(Blocks.BRICKS, Blocks.BRICK_SLAB, Blocks.BRICK_STAIRS, Blocks.BRICK_WALL)
                        .addPlaceable(Blocks.STONE_BRICKS, Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICK_WALL)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 3)
                        .addRequiredAttribute(Attribute.STRENGTH, 1),

                new Perk("stone_carving", type, MythriaBlocks.STONE_FURNACE, Skill.MASONRY, 10, () -> STONE_CUTTING)
                        .setDisplayName("Stone Carving")
                        .withDescription("Carved stone offers some advantages over individually stacked pieces.")
                        .addCraftable(MythriaBlocks.STONE_FURNACE).addPlaceable(MythriaBlocks.STONE_FURNACE)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 4)
                        .addRequiredAttribute(Attribute.DEXTERITY, 3)
        );
    }
}
