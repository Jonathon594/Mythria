package me.Jonathon594.Mythria.Util;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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
     * @param anchor   anchor block
     * @param list   list
     * @param validBlocks     TODO
     * @param maxCount TODO
     * @param maxRadius   TODO
     * @param maxHeight       TODO
     * @param original TODO
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

    public static void physicsCheck(World worldIn, BlockPos pos, final Block block, final int recursionCount, final boolean forceCheck) {
        BlockUtils.physicsTask(worldIn, pos, block, forceCheck, recursionCount);
    }

    public static ArrayList<Block> getFallingBlocks() {
        final ArrayList<Block> fallingBlocks = new ArrayList<>();
        fallingBlocks.add(Blocks.COBBLESTONE);
        fallingBlocks.add(Blocks.COAL_ORE);
        fallingBlocks.add(Blocks.REDSTONE_ORE);
        fallingBlocks.add(Blocks.DIAMOND_ORE);
        fallingBlocks.add(Blocks.EMERALD_ORE);
        fallingBlocks.add(Blocks.LAPIS_ORE);
        fallingBlocks.add(Blocks.NETHER_QUARTZ_ORE);
        fallingBlocks.add(Blocks.NETHER_WART_BLOCK);
        fallingBlocks.add(Blocks.HAY_BLOCK);
        fallingBlocks.add(Blocks.MOSSY_COBBLESTONE);
        fallingBlocks.add(Blocks.DIRT);
        fallingBlocks.add(Blocks.GRASS);
        fallingBlocks.add(Blocks.MYCELIUM);
        fallingBlocks.add(Blocks.NETHERRACK);
        fallingBlocks.add(Blocks.SOUL_SAND);
        fallingBlocks.add(Blocks.SLIME_BLOCK);
        fallingBlocks.add(Blocks.CLAY);

        fallingBlocks.add(MythriaBlocks.COPPER_ORE);
        fallingBlocks.add(MythriaBlocks.TIN_ORE);
        fallingBlocks.add(MythriaBlocks.SILVER_ORE);
        fallingBlocks.add(MythriaBlocks.IRON_ORE);
        fallingBlocks.add(MythriaBlocks.GOLD_ORE);
        fallingBlocks.add(MythriaBlocks.TITANIUM_ORE);
        fallingBlocks.add(MythriaBlocks.TUNGSTEN_ORE);
        fallingBlocks.add(MythriaBlocks.PLATINUM_ORE);
        return fallingBlocks;
    }

    @SuppressWarnings("deprecation")
    public static UUID applyPhysics(World worldIn, final Block block, BlockPos pos) {
        if (block == Blocks.AIR)
            return null;
        BlockState blockState = worldIn.getBlockState(pos);
        if (block == Blocks.GRASS || block == Blocks.MYCELIUM)
            worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
        final FallingBlockEntity fallingBlock = new FallingBlockEntity(worldIn, pos.getX() + 0.5, pos.getY(),
                pos.getZ() + 0.5, blockState);
        fallingBlock.shouldDropItem = false;
        fallingBlock.fallTime = 1;
        // remove original block
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        worldIn.addEntity(fallingBlock);
        return fallingBlock.getUniqueID();
    }

    private static void physicsTask(World worldIn, BlockPos pos, Block block, boolean force, int recursionCount) {
        boolean fall = false;
        final ArrayList<Block> fallingBlocks = BlockUtils.getFallingBlocks();
        final Block underBlock = worldIn.getBlockState(pos.down()).getBlock();
        if ((underBlock == Blocks.AIR || underBlock instanceof FlowingFluidBlock || underBlock == Blocks.TORCH)
                && (block == Blocks.SAND || block == Blocks.GRAVEL
                || (fallingBlocks.contains(block)) && block != Blocks.AIR)) {
            applyPhysics(worldIn, block, pos);
            fall = true;
        }
        if (fall || force)
            if (recursionCount >= 0) {
                if (force) {
                    BlockPos neighbor = pos.down();
                    Block b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);
                }

                BlockPos neighbor = pos.up();
                Block b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                neighbor = pos.down();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                neighbor = pos.north();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                neighbor = pos.east();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                neighbor = pos.west();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);

                neighbor = pos.south();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount - 1, false);
            }
    }

    public static void softenStone(IWorld world, BlockPos pos, Block block) {
        if (MythriaUtil.isOre(block)) {
            if (world.getBlockState(pos.up()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.up(), Blocks.COBBLESTONE.getDefaultState(), 7);

            if (world.getBlockState(pos.down()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.down(), Blocks.COBBLESTONE.getDefaultState(), 7);

            if (world.getBlockState(pos.north()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.north(), Blocks.COBBLESTONE.getDefaultState(), 7);

            if (world.getBlockState(pos.east()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.east(), Blocks.COBBLESTONE.getDefaultState(), 7);

            if (world.getBlockState(pos.west()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.west(), Blocks.COBBLESTONE.getDefaultState(), 7);

            if (world.getBlockState(pos.south()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.south(), Blocks.COBBLESTONE.getDefaultState(), 7);
        }
    }
}
