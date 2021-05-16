package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.PitFurnaceBlock;
import me.Jonathon594.Mythria.Interface.IPitFurnaceFilling;
import me.Jonathon594.Mythria.Items.LogItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.NonNullList;

public class PitFurnaceTileEntity extends BasicFurnaceTileEntity implements IClearable {
    private final NonNullList<ItemStack> fuelInventory = NonNullList.withSize(8, ItemStack.EMPTY);

    public PitFurnaceTileEntity() {
        super(MythriaTileEntities.PIT_FURNACE.get(), 3000, -1, 4);
        maxTemperature = 600;
    }

    @Override
    public boolean addItem(ItemStack itemStackIn) {
        for (int i = 0; i < fuelInventory.size(); i++) {
            if (i < 4 && !(itemStackIn.getItem() instanceof IPitFurnaceFilling)) continue;
            else if (i >= 4 && !(itemStackIn.getItem() instanceof LogItem)) continue;
            ItemStack itemstack = this.fuelInventory.get(i);
            if (itemstack.isEmpty()) {
                this.fuelInventory.set(i, itemStackIn.split(1));
                this.inventoryChanged();
                return true;
            }
        }
        return super.addItem(itemStackIn);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.fuelInventory.clear();
        ItemStackHelper.loadAllItems(nbt.getCompound("fuelInventory"), fuelInventory);
    }

    public CompoundNBT getUpdateTag() {
        return this.writeItems(new CompoundNBT());
    }

    public void tick() {
        if (world.getBlockState(pos).get(PitFurnaceBlock.SOULFIRE)) maxTemperature = 900;
        else maxTemperature = 600;
        super.tick();
    }

    @Override
    protected CompoundNBT writeItems(CompoundNBT compound) {
        CompoundNBT fuelInventory = new CompoundNBT();
        ItemStackHelper.saveAllItems(fuelInventory, this.fuelInventory);
        compound.put("fuelInventory", fuelInventory);
        return super.writeItems(compound);
    }

    @Override
    protected boolean shouldSpawnSignalSmoke() {
        return true;
    }

    @Override
    protected boolean canSpawnSmokeParticles() {
        return !isSoulfire();
    }

    @Override
    protected void onFinishBurning() {
        if (world.isRemote) return;
        dropAllItems();
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    protected boolean hasFlameParticles() {
        return !isSoulfire();
    }

    @Override
    protected double getHeatingEfficiency() {
        return 0.023;
    }

    @Override
    public boolean canBeLit() {
        BlockState blockState = getBlockState();
        return getFillingCount() == 4 && getLogCount() == 4 && !blockState.get(PitFurnaceBlock.WATERLOGGED);
    }

    @Override
    public void light() {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
    }

    public int getFillingCount() {
        int count = 0;
        for (ItemStack itemStack : fuelInventory) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IPitFurnaceFilling) count++;
        }
        return count;
    }

    public NonNullList<ItemStack> getFuelInventory() {
        return fuelInventory;
    }

    public int getLogCount() {
        int count = 0;
        for (ItemStack itemStack : fuelInventory) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof LogItem) count++;
        }
        return count;
    }

    public boolean isSoulfire() {
        return getBlockState().get(PitFurnaceBlock.SOULFIRE);
    }
}
