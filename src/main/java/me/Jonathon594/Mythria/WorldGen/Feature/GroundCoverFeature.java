package me.Jonathon594.Mythria.WorldGen.Feature;

import com.mojang.serialization.Codec;
import me.Jonathon594.Mythria.Blocks.BlockGroundCover;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.WorldGen.Feature.Config.GroundCoverConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class GroundCoverFeature<U extends GroundCoverConfig> extends MythriaFeature<U> {
    public GroundCoverFeature(String name, Codec<U> codec) {
        super(codec);
        setRegistryName(Mythria.MODID, name);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, U config) {
        if(reader.getWorld().getDimensionKey().equals(World.THE_NETHER)) pos = pos.offset(Direction.DOWN, 128);
        while (true) {
            BlockState state = reader.getBlockState(pos);
            Block block = state.getBlock();
            if (isDirt(block) || isStone(block) || isSand(block) || isGravel(block) || isNetherrack(block) || isNylium(block) || isNetherStone(block))
                placeBlock(reader, pos.up(), rand, config);
            pos = pos.down();
            if (pos.getY() <= 0) break;
        }
        return true;
    }

    protected void placeBlock(final IWorld worldIn, final BlockPos pos, final Random rand, U config) {
        FluidState fluidState = worldIn.getFluidState(pos);
        BlockState state = config.block.getBlockState();
        if (state.isValidPosition(worldIn, pos) && worldIn.getBlockState(pos).getMaterial().isReplaceable() && fluidState.getFluid() != Fluids.LAVA && fluidState.getFluid() != Fluids.FLOWING_LAVA) {
            worldIn.setBlockState(pos, state.with(BlockGroundCover.ROTATION, rand.nextInt(16)).with(BlockGroundCover.WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER)), 2);
        }
    }
}
