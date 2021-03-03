package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.PitFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IClearable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;

public class PitFurnaceTileEntity extends BasicFurnaceTileEntity implements IClearable {

    public PitFurnaceTileEntity() {
        super(MythriaTileEntities.PIT_FURNACE.get(), 3000, -1, 4);
        maxTemperature = 600;
    }

    public void tick() {
        super.tick();
    }

    @Override
    protected double getHeatingEfficiency() {
        return 0.023;
    }

    @Override
    protected void onFinishBurning() {
        if (world.isRemote) return;
        dropAllItems();
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    public CompoundNBT getUpdateTag() {
        return this.writeItems(new CompoundNBT());
    }

    @Override
    public void light() {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
    }

    @Override
    public boolean canBeLit() {
        BlockState blockState = getBlockState();
        return blockState.get(PitFurnaceBlock.FULL) == 7 && !blockState.get(PitFurnaceBlock.WATERLOGGED);
    }
}
