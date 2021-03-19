package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.Perk;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialManager {
    public static final Map<Item, List<Perk>> PERKS_FOR_CRAFTING = new HashMap<>();
    public static final Map<Block, List<Perk>> PERKS_FOR_PLACING = new HashMap<>();
    public static final Map<Block, List<Perk>> PERKS_FOR_BREAKING = new HashMap<>();

    public static double getStaminaCostForBreaking(BlockState blockState, IWorld world, BlockPos pos) {
        return blockState.getBlockHardness(world, pos);
    }

    public static void onTagsUpdated() {
        MaterialManager.PERKS_FOR_BREAKING.clear();
        MaterialManager.PERKS_FOR_CRAFTING.clear();
        MaterialManager.PERKS_FOR_PLACING.clear();
    }

    public static void registerBreakable(List<Block> blocks, Perk perk) {
        for (Block block : blocks) {
            PERKS_FOR_BREAKING.computeIfAbsent(block, k -> new ArrayList<>());
            PERKS_FOR_BREAKING.get(block).add(perk);
        }
    }

    public static void registerCraftable(List<Item> items, Perk perk) {
        for (Item item : items) {
            PERKS_FOR_CRAFTING.computeIfAbsent(item, k -> new ArrayList<>());
            PERKS_FOR_CRAFTING.get(item).add(perk);
        }
    }

    public static void registerPlaceable(List<Block> blocks, Perk perk) {
        for (Block block : blocks) {
            PERKS_FOR_PLACING.computeIfAbsent(block, k -> new ArrayList<>());
            PERKS_FOR_PLACING.get(block).add(perk);
        }
    }
}
