package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Container.SawhorseContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockSawhorse extends MythriaBlockHorizontal {
    protected static final VoxelShape[] SHAPE = {
            Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 16.0D, 10.0D, 15.0D),
            Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 10.0D, 16.0D)
    };
    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.sawhorse");

    public BlockSawhorse(String name, double weight) {
        super(name, weight, Block.Properties.create(Material.WOOD).hardnessAndResistance(10f, 10f).sound(SoundType.WOOD).notSolid());
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) player, state.getContainer(worldIn, pos), pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) -> new SawhorseContainer(windowID, invPlayer), CONTAINER_NAME);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE[state.get(FACING).getHorizontalIndex() % 2];
    }
}
