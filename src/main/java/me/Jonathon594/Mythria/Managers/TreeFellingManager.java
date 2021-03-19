package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Tags.MythriaBlockTags;
import me.Jonathon594.Mythria.Util.BlockUtils;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TreeFellingManager {
    public static ArrayList<Block> HandleTreeChop(final PlayerEntity p, final BlockPos block) {
        final ArrayList<Block> blocks = new ArrayList<>();
        Block b = p.world.getBlockState(block).getBlock();
        Collection<Block> logs = MythriaUtil.getBlockCollectionFromTag(BlockTags.LOGS.getName());
        if (!logs.contains(b))
            return new ArrayList<>();
        ItemStack axe = p.getHeldItemMainhand();
        if (!MythriaUtil.isAxe(axe.getItem()))
            return new ArrayList<>();
        if (p.isSneaking())
            return new ArrayList<>();
        final int itemd = axe.getDamage();
        final int itemmd = axe.getMaxDamage();
        final int itemr = (itemmd - itemd);
        ArrayList<BlockPos> posList = new ArrayList<>();
        List<Block> leaves = MythriaBlockTags.TREE_FELLER_LEAVES.getAllElements();
        BlockUtils.getAllTreeBlocks(p.world, block, posList, 0, block);
        for (BlockPos pos : posList) {
            blocks.add(p.world.getBlockState(pos).getBlock());
        }
        boolean hasLeaves = false;
        for (Block b1 : blocks) {
            if (leaves.contains(b1)) {
                hasLeaves = true;
                break;
            }
        }
        if (!hasLeaves) return new ArrayList<>();
        for (BlockPos pos : posList) {
            MythriaUtil.destroyBlockWithTool(p.world, pos, true, p, axe);
        }
        final ArrayList<Block> consideredMaterials = new ArrayList<>();
        int d = 0;
        for (Block b1 : blocks) {
            if (logs.contains(b1)) {
                d++;
                consideredMaterials.add(b1);
            }
        }
        axe
                .setDamage((axe.getDamage() + d));
        if (d > itemr) {
            p.attackEntityFrom(DamageSource.GENERIC, 4);
            SoundManager.playForAllNearby(p, SoundEvents.ENTITY_ITEM_BREAK);
            p.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
        }
        return consideredMaterials;
    }
}
