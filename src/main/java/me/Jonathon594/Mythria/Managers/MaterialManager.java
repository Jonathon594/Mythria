package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MaterialManager {
    public static final Map<Item, List<Perk>> PERKS_FOR_CRAFTING = new HashMap<>();
    public static final Map<Block, List<Perk>> PERKS_FOR_PLACING = new HashMap<>();
    public static final Map<Block, List<Perk>> PERKS_FOR_BREAKING = new HashMap<>();

    public static void onBlockBreak(final BreakEvent event, final PlayerEntity player, final Profile p) {
        if (event.isCanceled())
            return;
        Block block = event.getState().getBlock();
        final List<Perk> list = PERKS_FOR_BREAKING.get(block);
        if (list == null)
            return;
        for (final Perk pa : list)
            if (p.getPlayerSkills().contains(pa)) {
                for (final Entry<MythicSkills, Integer> s : pa.getRequiredSkills().entrySet())
                    p.addSkillExperience(s.getKey(), EXPConst.BLOCK_BREAK * (s.getValue() / 10.0 + 1), (ServerPlayerEntity) player, s.getValue());
            }
    }

    public static void onBlockPlace(final BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof PlayerEntity)) return;
        final PlayerEntity player = (PlayerEntity) entity;
        final Profile p = ProfileProvider.getProfile(player);
        boolean able = false;
        final List<Perk> list = PERKS_FOR_PLACING.get(event.getPlacedBlock().getBlock());
        if (list == null)
            return;
        for (final Perk pa : list)
            if (p.getPlayerSkills().contains(pa)) {
                able = true;
                for (final Entry<MythicSkills, Integer> s : pa.getRequiredSkills().entrySet())
                    p.addSkillExperience(s.getKey(), EXPConst.BLOCK_PLACE * (s.getValue() / 10.0 + 1), (ServerPlayerEntity) player, s.getValue());
            }

        if (!able) {
            event.setCanceled(true);
        }
    }

    public static double getStaminaCostForBreaking(BlockState blockState, IWorld world, BlockPos pos) {
        return blockState.getBlockHardness(world, pos);
    }

    public static void registerCraftable(List<Item> items, Perk perk) {
        for (Item item : items) {
            PERKS_FOR_CRAFTING.computeIfAbsent(item, k -> new ArrayList<>());
            PERKS_FOR_CRAFTING.get(item).add(perk);
        }
    }

    public static void registerBreakable(List<Block> blocks, Perk perk) {
        for (Block block : blocks) {
            PERKS_FOR_BREAKING.computeIfAbsent(block, k -> new ArrayList<>());
            PERKS_FOR_BREAKING.get(block).add(perk);
        }
    }

    public static void registerPlaceable(List<Block> blocks, Perk perk) {
        for (Block block : blocks) {
            PERKS_FOR_PLACING.computeIfAbsent(block, k -> new ArrayList<>());
            PERKS_FOR_PLACING.get(block).add(perk);
        }
    }

    public static void onTagsUpdated() {
        MaterialManager.PERKS_FOR_BREAKING.clear();
        MaterialManager.PERKS_FOR_CRAFTING.clear();
        MaterialManager.PERKS_FOR_PLACING.clear();
    }
}
