package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.TileEntity.MythriaBarrelTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class MythriaBarrelBlock extends BarrelBlock {
    private final double weight;

    public MythriaBarrelBlock(String name, double weight) {
        super(AbstractBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.5F).sound(SoundType.PLANT));
        this.weight = weight;
        setRegistryName(name);
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof MythriaBarrelTileEntity) {
                player.openContainer((MythriaBarrelTileEntity) tileentity);
                player.addStat(Stats.OPEN_BARREL);
                PiglinTasks.func_234478_a_(player, true);
            }

            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof MythriaBarrelTileEntity) {
            ((MythriaBarrelTileEntity) tileentity).barrelTick();
        }
    }

    @Nullable
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new MythriaBarrelTileEntity();
    }
}
