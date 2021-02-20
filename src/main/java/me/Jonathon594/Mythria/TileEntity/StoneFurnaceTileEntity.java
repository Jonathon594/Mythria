package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.MythriaFurnaceBlock;
import me.Jonathon594.Mythria.Blocks.PitFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;

public class StoneFurnaceTileEntity extends FuelableFurnaceTileEntity implements IClearable {

    public StoneFurnaceTileEntity() {
        super(MythriaTileEntities.STONE_FURNACE.get(), 1649, 5);
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

    @Override
    protected void setLit(boolean lit) {
        world.setBlockState(pos, getBlockState().with(MythriaFurnaceBlock.LIT, false));
    }

    @Override
    protected ItemStack getFuelStack() {
        return inventory.get(4);
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
     */
    public CompoundNBT getUpdateTag() {
        return this.writeItems(new CompoundNBT());
    }

    @Override
    public void light() {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
    }

    @Override
    public boolean canBeLit() {
        BlockState blockState = getBlockState();
        return blockState.get(PitFurnaceBlock.FULL) == 7 && blockState.get(PitFurnaceBlock.WATERLOGGED) == false;
    }
}
