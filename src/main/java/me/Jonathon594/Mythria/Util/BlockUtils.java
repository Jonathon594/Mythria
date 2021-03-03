package me.Jonathon594.Mythria.Util;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;

public class BlockUtils {
    private static final Collection<Block> logs = MythriaUtil.getBlockCollectionFromTag(BlockTags.LOGS.getName());
    private static final Collection<Block> leaves = MythriaUtil.getBlockCollectionFromTag(BlockTags.LEAVES.getName());

    public static void getAllTreeBlocks(World worldIn, final BlockPos lastPos, final ArrayList<BlockPos> blocks, int leafStep, final BlockPos firstPos) {
        Block currentBlock = worldIn.getBlockState(lastPos).getBlock();

        if (leaves.contains(currentBlock)) leafStep++;

        for (Direction direction : Direction.values()) {
            BlockPos next = lastPos.offset(direction);
            Block nextBlock = worldIn.getBlockState(next).getBlock();
            BlockPos nextFlat = new BlockPos(next.getX(), firstPos.getY(), next.getZ());
            if (nextFlat.distanceSq(firstPos) < 25) {
                if (logs.contains(nextBlock) || (leafStep <= 2 && leaves.contains(nextBlock))) {
                    if (!blocks.contains(next)) {
                        blocks.add(next);
                        final boolean isHoriz = !direction.equals(Direction.DOWN) && !direction.equals(Direction.UP);
                        getAllTreeBlocks(worldIn, next, blocks, leafStep, firstPos);
                    }
                }
            }
        }
    }

    /**
     * Adds a tree to the given lists.
     *
     * @param anchor      anchor block
     * @param list        list
     * @param validBlocks TODO
     * @param maxCount    TODO
     * @param maxRadius   TODO
     * @param maxHeight   TODO
     * @param original    TODO
     */
    public static void getConnected(World worldIn, final BlockPos anchor, final ArrayList<BlockPos> list, final ArrayList<Block> validBlocks,
                                    final int maxCount, final int maxRadius, final int maxHeight, final BlockPos original) {
        // Limits:
        if (list.size() > maxCount)
            return;
        BlockPos nextAnchor = null;
        final BlockPos centerLine = new BlockPos(original.getX(), 0, original.getZ());
        // North:
        nextAnchor = anchor.north();
        Block nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        final int radiusSq = maxRadius * maxRadius;
        if (validBlocks.contains(nextBlock) && !list.contains(nextAnchor)) {
            if (centerLine.distanceSq(new Vector3i(nextAnchor.getX(), 0, nextAnchor.getZ())) > radiusSq)
                return;
            if (nextAnchor.getY() - original.getY() > maxHeight)
                return;
            list.add(nextAnchor);
            getConnected(worldIn, nextAnchor, list, validBlocks, maxCount, maxRadius, maxHeight, original);
        }
        // East:
        nextAnchor = anchor.east();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (validBlocks.contains(nextBlock) && !list.contains(nextAnchor)) {

            if (centerLine.distanceSq(new Vector3i(nextAnchor.getX(), 0, nextAnchor.getZ())) > radiusSq)
                return;
            if (nextAnchor.getY() - original.getY() > maxHeight)
                return;
            list.add(nextAnchor);
            getConnected(worldIn, nextAnchor, list, validBlocks, maxCount, maxRadius, maxHeight, original);
        }
        // south:
        nextAnchor = anchor.south();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (validBlocks.contains(nextBlock) && !list.contains(nextAnchor)) {

            if (centerLine.distanceSq(new Vector3i(nextAnchor.getX(), 0, nextAnchor.getZ())) > radiusSq)
                return;
            if (nextAnchor.getY() - original.getY() > maxHeight)
                return;
            list.add(nextAnchor);
            getConnected(worldIn, nextAnchor, list, validBlocks, maxCount, maxRadius, maxHeight, original);
        }
        // west:
        nextAnchor = anchor.west();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (validBlocks.contains(nextBlock) && !list.contains(nextAnchor)) {

            if (centerLine.distanceSq(new Vector3i(nextAnchor.getX(), 0, nextAnchor.getZ())) > radiusSq)
                return;
            if (nextAnchor.getY() - original.getY() > maxHeight)
                return;
            list.add(nextAnchor);
            getConnected(worldIn, nextAnchor, list, validBlocks, maxCount, maxRadius, maxHeight, original);
        }
        // maxHeight:
        nextAnchor = anchor.up();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (validBlocks.contains(nextBlock) && !list.contains(nextAnchor)) {

            if (centerLine.distanceSq(new Vector3i(nextAnchor.getX(), 0, nextAnchor.getZ())) > radiusSq)
                return;
            if (nextAnchor.getY() - original.getY() > maxHeight)
                return;
            list.add(nextAnchor);
            getConnected(worldIn, nextAnchor, list, validBlocks, maxCount, maxRadius, maxHeight, original);
        }
        // down:
        nextAnchor = anchor.down();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (validBlocks.contains(nextBlock) && !list.contains(nextAnchor)) {

            if (centerLine.distanceSq(new Vector3i(nextAnchor.getX(), 0, nextAnchor.getZ())) > radiusSq)
                return;
            if (nextAnchor.getY() - original.getY() > maxHeight)
                return;
            list.add(nextAnchor);
            getConnected(worldIn, nextAnchor, list, validBlocks, maxCount, maxRadius, maxHeight, original);
        }
    }
}
