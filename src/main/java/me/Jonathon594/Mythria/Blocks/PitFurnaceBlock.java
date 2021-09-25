package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.TileEntity.PitFurnaceTileEntity;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class PitFurnaceBlock extends MythriaBlockContainer implements IWaterLoggable {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty SOULFIRE = BooleanProperty.create("soulfire");
    protected static final VoxelShape[] SHAPE = new VoxelShape[5];

    public PitFurnaceBlock(String name) {
        super(name, Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)
                .setLightLevel(value -> value.get(BlockStateProperties.LIT) ? 15 : 0).tickRandomly().notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false).with(SOULFIRE, false).with(WATERLOGGED, false));

        for (int i = 0; i < 4; i++) {
            SHAPE[i] = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, (i * 2) + 2.0D, 16.0D);
        }
        SHAPE[4] = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        boolean flag = false;
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP) continue;
            if (worldIn.getBlockState(currentPos.offset(direction)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) flag = true;
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos).with(SOULFIRE, flag);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);

        if (!isValidPosition(state, worldIn, pos)) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof PitFurnaceTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((PitFurnaceTileEntity) tileentity).getInventory());
            }

            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof PitFurnaceTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((PitFurnaceTileEntity) tileentity).getInventory());
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof PitFurnaceTileEntity) {
            PitFurnaceTileEntity pitFurnaceTileEntity = (PitFurnaceTileEntity) tileentity;
            boolean isRemote = worldIn.isRemote;
            if (!isRemote) {
                if (!state.get(LIT)) {
                    if (pitFurnaceTileEntity.addItem(player.abilities.isCreativeMode ? itemstack.copy() : itemstack)) {
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.getBlockState(pos.down()).isSolid()) return false;
        if (!worldIn.getBlockState(pos.north()).isSolid()) return false;
        if (!worldIn.getBlockState(pos.east()).isSolid()) return false;
        if (!worldIn.getBlockState(pos.south()).isSolid()) return false;
        if (!worldIn.getBlockState(pos.west()).isSolid()) return false;

        return super.isValidPosition(state, worldIn, pos) && worldIn.getBlockState(pos.down()).isSolid();
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        PitFurnaceTileEntity pitFurnaceTileEntity = (PitFurnaceTileEntity) worldIn.getTileEntity(pos);
        if (pitFurnaceTileEntity == null) return SHAPE[0];
        return SHAPE[MathHelper.clamp(pitFurnaceTileEntity.getFillingCount() +
                pitFurnaceTileEntity.getLogCount() - 1, 0, 4)];
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isImmuneToFire() && state.get(LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.attackEntityFrom(DamageSource.IN_FIRE, state.get(SOULFIRE) ? 2f : 1f);
        }

        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            if (rand.nextInt(10) == 0) {
                worldIn.playSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (!stateIn.get(SOULFIRE) && rand.nextInt(5) == 0) {
                for (int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (float) pos.getX() + 0.5F, (float) pos.getY() + 1F, (float) pos.getZ() + 0.5F, rand.nextFloat() / 2.0F, 5.0E-5D, rand.nextFloat() / 2.0F);
                }
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorld iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        boolean flag = iworld.getFluidState(blockpos).getFluid() == Fluids.WATER;
        boolean flag2 = false;
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP) continue;
            if (iworld.getBlockState(blockpos.offset(direction)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) flag2 = true;
        }
        return this.getDefaultState().with(WATERLOGGED, flag).with(LIT, Boolean.FALSE)
                .with(SOULFIRE, flag2)
                .with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED, FACING, SOULFIRE);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new PitFurnaceTileEntity();
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            boolean flag = state.get(LIT);
            if (flag) {
                if (worldIn.isRemote()) {
                    for (int i = 0; i < 20; ++i) {
                        if (!state.get(SOULFIRE))
                            MythriaUtil.spawnSmokeParticles((World) worldIn, pos, false, true, 0.0);
                    }
                } else {
                    worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                TileEntity tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof PitFurnaceTileEntity) {
                    ((PitFurnaceTileEntity) tileentity).dropAllItems();
                }
            }

            worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.TRUE).with(LIT, Boolean.FALSE), 3);
            worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            return true;
        } else {
            return false;
        }
    }
}
