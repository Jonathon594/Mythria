package me.Jonathon594.Mythria.WorldGen.Feature;

import com.mojang.serialization.Codec;
import me.Jonathon594.Mythria.Blocks.BlockGroundCover;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.WorldGen.Feature.Config.GroundStickConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.Random;

public class GroundStickFeature<U extends GroundStickConfig> extends MythriaFeature<U> {
    public GroundStickFeature(String name, Codec<U> codec) {
        super(codec);
        setRegistryName(Mythria.MODID, name);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, U config) {
        boolean generated = false;
        while (true) {
            BlockState state = reader.getBlockState(pos);
            if (state.getBlock().equals(config.leaves.getBlock())) {
                int lastPlaceHeight = placeUnderLeaves(reader, generator, rand, new BlockPos(pos), config);
                if (lastPlaceHeight > 0) generated = true;
                pos = new BlockPos(pos.getX(), lastPlaceHeight, pos.getZ());
            }
            pos = pos.down();
            if (pos.getY() <= 0) break;
        }
        return generated;
    }

    private int placeUnderLeaves(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, U config) {
        boolean airFound = false;
        while (true) {
            BlockState state = reader.getBlockState(pos);
            if (!airFound && state.isAir()) airFound = true;
            if (airFound && !state.isAir()) {
                Block block = state.getBlock();
                if (isDirt(block) || isStone(block) || isSand(block) || isGravel(block) || isNetherrack(block) || isNylium(block) || isNetherStone(block))
                    break;
            }
            pos = pos.down();
            if (pos.getY() <= 0) return -1;
        }
        placeBlock(reader, pos.up(), rand, config);
        return pos.up().getY();
    }

    protected void placeBlock(final IWorld worldIn, final BlockPos pos, final Random rand, U config) {
        FluidState fluidState = worldIn.getFluidState(pos);
        BlockState state = config.stick.getBlockState();
        if (state.isValidPosition(worldIn, pos) && worldIn.getBlockState(pos).getMaterial().isReplaceable() && fluidState.getFluid() != Fluids.LAVA && fluidState.getFluid() != Fluids.FLOWING_LAVA) {
            worldIn.setBlockState(pos, state.with(BlockGroundCover.ROTATION, rand.nextInt(16)).with(BlockGroundCover.WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER)), 2);
        }
    }
}
