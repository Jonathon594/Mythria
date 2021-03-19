package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Items.LogItem;
import me.Jonathon594.Mythria.TileEntity.CampfireTileEntity;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class CampfireBlock extends MythriaBlockContainer implements IWaterLoggable {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty CHARCOAL = BooleanProperty.create("charcoal");
    public static final BooleanProperty SOULFIRE = BooleanProperty.create("soulfire");
    public static final BooleanProperty SIGNAL_FIRE = BlockStateProperties.SIGNAL_FIRE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    private static final VoxelShape field_226912_f_ = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public CampfireBlock(String name) {
        super(name, Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD).setLightLevel(value -> value.get(BlockStateProperties.LIT) ? value.get(SOULFIRE) ? 10 : 15 : 0).tickRandomly().notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(SOULFIRE, false).with(CHARCOAL, false).with(LIT, Boolean.FALSE).with(SIGNAL_FIRE, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE).with(FACING, Direction.NORTH));
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return facing == Direction.DOWN ? stateIn
                .with(SIGNAL_FIRE, this.isHayBlock(facingState))
                .with(SOULFIRE, this.isSoulSoil(facingState)) :
                super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof CampfireTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((CampfireTileEntity) tileentity).getCookingInventory());
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof CampfireTileEntity) {
            CampfireTileEntity campfiretileentity = (CampfireTileEntity) tileentity;
            boolean isRemote = worldIn.isRemote;
            if (state.get(LIT)) {
                Optional<CampfireCookingRecipe> optional = campfiretileentity.findMatchingRecipe(itemstack);
                if (optional.isPresent()) {
                    Profile profile = ProfileProvider.getProfile(player);
                    if (profile.hasFlag(AttributeFlag.BASIC_COOKING)) {
                        if (!isRemote && campfiretileentity.addFoodItem(player.abilities.isCreativeMode ? itemstack.copy() : itemstack, optional.get().getCookTime())) {
                            player.addStat(Stats.INTERACT_WITH_CAMPFIRE);
                            return ActionResultType.SUCCESS;
                        }
                    }

                    return ActionResultType.CONSUME;
                }
            } else {
                int full = campfiretileentity.getLogCount();
                if (full < 4 && !isRemote) {
                    if (itemstack.getItem() instanceof LogItem) {
                        campfiretileentity.addLogItem(itemstack);
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

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isImmuneToFire() && state.get(LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.attackEntityFrom(DamageSource.IN_FIRE, state.get(SOULFIRE) ? 2f : 1f);
        }

        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!worldIn.isRemote) {
            boolean flag = projectile instanceof AbstractFireballEntity || projectile instanceof AbstractArrowEntity && projectile.isBurning();
            if (flag) {
                Entity entity = this.func_226913_a_(projectile);
                boolean flag1 = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entity);
                if (flag1 && !state.get(LIT) && !state.get(WATERLOGGED)) {
                    BlockPos blockpos = hit.getPos();
                    worldIn.setBlockState(blockpos, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
                }
            }
        }
    }

    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            if (rand.nextInt(10) == 0) {
                worldIn.playSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (!stateIn.get(SOULFIRE) && rand.nextInt(5) == 0) {
                for (int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, rand.nextFloat() / 2.0F, 5.0E-5D, rand.nextFloat() / 2.0F);
                }
            }

        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorld iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        boolean flag = iworld.getFluidState(blockpos).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, flag)
                .with(SIGNAL_FIRE, this.isHayBlock(iworld.getBlockState(blockpos.down())))
                .with(SOULFIRE, this.isSoulSoil(iworld.getBlockState(blockpos.down())))
                .with(LIT, Boolean.FALSE)
                .with(FACING, context.getPlacementHorizontalFacing());
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING, CHARCOAL, SOULFIRE);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CampfireTileEntity();
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public static boolean isLitCampfireInRange(World p_226914_0_, BlockPos p_226914_1_, int p_226914_2_) {
        for (int i = 1; i <= p_226914_2_; ++i) {
            BlockPos blockpos = p_226914_1_.down(i);
            BlockState blockstate = p_226914_0_.getBlockState(blockpos);
            if (isLit(blockstate)) {
                return true;
            }

            boolean flag = VoxelShapes.compare(field_226912_f_, blockstate.getCollisionShape(p_226914_0_, p_226914_1_, ISelectionContext.dummy()), IBooleanFunction.AND);
            if (flag) {
                BlockState blockstate1 = p_226914_0_.getBlockState(blockpos.down());
                return isLit(blockstate1);
            }
        }

        return false;
    }

    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            boolean flag = state.get(LIT);
            if (flag) {
                if (worldIn.isRemote()) {
                    for (int i = 0; i < 20; ++i) {
                        if (!state.get(SOULFIRE))
                            MythriaUtil.spawnSmokeParticles((World) worldIn, pos, state.get(SIGNAL_FIRE), true);
                    }
                } else {
                    worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                TileEntity tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof CampfireTileEntity) {
                    ((CampfireTileEntity) tileentity).dropAllItems();
                }
            }

            worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.TRUE).with(LIT, Boolean.FALSE), 3);
            worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private Entity func_226913_a_(Entity p_226913_1_) {
        if (p_226913_1_ instanceof AbstractFireballEntity) {
            return ((AbstractFireballEntity) p_226913_1_).func_234616_v_();
        } else {
            return p_226913_1_ instanceof AbstractArrowEntity ? ((AbstractArrowEntity) p_226913_1_).func_234616_v_() : null;
        }
    }

    /**
     * Returns true if the block of the passed blockstate is a Hay block, otherwise false.
     */
    private boolean isHayBlock(BlockState stateIn) {
        return stateIn.getBlock() == Blocks.HAY_BLOCK;
    }

    private static boolean isLit(BlockState p_226915_0_) {
        return p_226915_0_.getBlock() == MythriaBlocks.CAMPFIRE && p_226915_0_.get(LIT);
    }

    private boolean isSoulSoil(BlockState blockState) {
        return blockState.isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS);
    }
}
