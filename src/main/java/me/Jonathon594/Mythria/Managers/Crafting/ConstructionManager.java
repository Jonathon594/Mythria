package me.Jonathon594.Mythria.Managers.Crafting;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ConstructionManager {
    private final static ArrayList<Block> reinforcedBlocks = new ArrayList<>();
    private static final ArrayList<RecentlyPlacedBlock> recentlyPlacedBlocks = new ArrayList<>();

    public static boolean isReinforced(Block block) {
        return reinforcedBlocks.contains(block);
    }

    public static void init() {
        //Blocks
        //reinforcedBlocks.addAll(MythriaUtil.getBlockCollectionFromTag(BlockTags.PLANKS.getName()));

        reinforcedBlocks.add(Blocks.BRICKS);
        reinforcedBlocks.add(Blocks.NETHER_BRICKS);
        reinforcedBlocks.add(Blocks.RED_NETHER_BRICKS);
        reinforcedBlocks.add(Blocks.END_STONE_BRICKS);
        reinforcedBlocks.add(Blocks.STONE_BRICKS);
        reinforcedBlocks.add(Blocks.SANDSTONE);
        reinforcedBlocks.add(Blocks.RED_SANDSTONE);
        reinforcedBlocks.add(Blocks.QUARTZ_BLOCK);
        reinforcedBlocks.add(MythriaBlocks.THATCH_BLOCK);

        ///reinforcedBlocks.addAll(MythriaUtil.getBlockCollectionFromTag(new MythriaResourceLocation("concrete")));

        reinforcedBlocks.add(Blocks.PRISMARINE);

        //Stairs
        //reinforcedBlocks.addAll(MythriaUtil.getBlockCollectionFromTag(BlockTags.WOODEN_STAIRS.getName()));
        reinforcedBlocks.add(Blocks.NETHER_BRICK_STAIRS);
        reinforcedBlocks.add(Blocks.QUARTZ_STAIRS);
        reinforcedBlocks.add(Blocks.STONE_BRICK_STAIRS);
        reinforcedBlocks.add(Blocks.PURPUR_STAIRS);
        reinforcedBlocks.add(Blocks.RED_SANDSTONE_STAIRS);
        reinforcedBlocks.add(Blocks.SANDSTONE_STAIRS);
        reinforcedBlocks.add(MythriaBlocks.THATCH_STAIR);

        //Fences
        //reinforcedBlocks.addAll(MythriaUtil.getBlockCollectionFromTag(BlockTags.WOODEN_FENCES.getName()));

        //Gates
        reinforcedBlocks.add(Blocks.DARK_OAK_FENCE_GATE);
        reinforcedBlocks.add(Blocks.JUNGLE_FENCE_GATE);
        reinforcedBlocks.add(Blocks.SPRUCE_FENCE_GATE);
        reinforcedBlocks.add(Blocks.ACACIA_FENCE_GATE);
        reinforcedBlocks.add(Blocks.BIRCH_FENCE_GATE);
        reinforcedBlocks.add(Blocks.OAK_FENCE_GATE);

        //Slabs
        //reinforcedBlocks.addAll(MythriaUtil.getBlockCollectionFromTag(BlockTags.WOODEN_SLABS.getName()));

        //Doors
        //reinforcedBlocks.addAll(MythriaUtil.getBlockCollectionFromTag(BlockTags.WOODEN_DOORS.getName()));
        reinforcedBlocks.add(Blocks.IRON_DOOR);
        reinforcedBlocks.add(Blocks.IRON_TRAPDOOR);
        //reinforcedBlocks.addAll(MythriaUtil.getBlockCollectionFromTag(BlockTags.WOODEN_TRAPDOORS.getName()));
    }

    public static void addRecentlyPlacedBlock(PlayerEntity p, Block block, BlockPos pos) {
        RecentlyPlacedBlock current = getRecentlyPlacedBlock(pos, p.world.getDimensionKey());
        if (current != null) recentlyPlacedBlocks.remove(current);

        RecentlyPlacedBlock recentlyPlacedBlock = new RecentlyPlacedBlock(p, pos, p.world.getDimensionKey(), block);
        recentlyPlacedBlocks.add(recentlyPlacedBlock);
    }

    private static RecentlyPlacedBlock getRecentlyPlacedBlock(BlockPos pos, RegistryKey<World> dimension) {
        for (RecentlyPlacedBlock recentlyPlacedBlock : recentlyPlacedBlocks) {
            if (recentlyPlacedBlock.getDimension() != dimension) continue;
            if (!recentlyPlacedBlock.getLocation().equals(pos)) continue;
            return recentlyPlacedBlock;
        }
        return null;
    }

    public static void removeRecentlyPlaced(BlockPos pos, RegistryKey<World> dimension) {
        RecentlyPlacedBlock recentlyPlacedBlock = getRecentlyPlacedBlock(pos, dimension);
        if (recentlyPlacedBlock != null) recentlyPlacedBlocks.remove(recentlyPlacedBlock);
    }

    public static boolean canBlockBeDemolishedBy(PlayerEntity player, BlockPos pos, RegistryKey<World> dimension, Block block) {
        RecentlyPlacedBlock recentlyPlacedBlock = getRecentlyPlacedBlock(pos, dimension);
        if (recentlyPlacedBlock == null) return false;
        if (!recentlyPlacedBlock.getPlacer().equals(player)) return false;
        if (!recentlyPlacedBlock.getBlock().equals(block)) return false;
        if (System.currentTimeMillis() > recentlyPlacedBlock.getExpiration()) return false;
        recentlyPlacedBlocks.remove(recentlyPlacedBlock);
        return true;
    }

    public static class RecentlyPlacedBlock {
        private final PlayerEntity placer;
        private final BlockPos location;
        private final RegistryKey<World> dimension;
        private final long expiration;
        private final Block block;

        public RecentlyPlacedBlock(PlayerEntity placer, BlockPos location, RegistryKey<World> dimension, Block block) {
            this.placer = placer;
            this.location = location;
            this.dimension = dimension;
            this.block = block;
            expiration = System.currentTimeMillis() + 1000 * 60 * 5;
        }

        public long getExpiration() {
            return expiration;
        }

        public RegistryKey<World> getDimension() {
            return dimension;
        }

        public BlockPos getLocation() {
            return location;
        }

        public PlayerEntity getPlacer() {
            return placer;
        }

        public Block getBlock() {
            return block;
        }
    }
}
