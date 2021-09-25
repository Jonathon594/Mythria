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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PitFurnaceTileEntity extends AbstractBasicFurnaceTileEntity implements IClearable {
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
    protected void addParticles() {
        super.addParticles();

        World world = this.getWorld();
        if (world != null) {
            BlockPos blockpos = this.getPos();
            Random random = world.rand;
            for (int j = 0; j < this.inventory.size(); ++j) {
                if (!this.inventory.get(j).isEmpty() && random.nextFloat() < 0.2F) {
                    Direction direction = Direction.byHorizontalIndex(Math.floorMod(j, 4));
                    float f = 0.3125F;
                    double d0 = (double) blockpos.getX() + 0.5D - (double) ((float) direction.getXOffset() * 0.3125F) + (double) ((float) direction.rotateY().getXOffset() * 0.3125F);
                    double d1 = (double) blockpos.getY() + 1.0D;
                    double d2 = (double) blockpos.getZ() + 0.5D - (double) ((float) direction.getZOffset() * 0.3125F) + (double) ((float) direction.rotateY().getZOffset() * 0.3125F);

                    for (int k = 0; k < 4; ++k) {
                        world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
                    }
                }
            }

            final int a = 1;
            for (int i = 0; i < a; i++) {
                final double x = pos.getX() + Math.random();
                final double y = pos.getY() + Math.random() * 0.2 + 1;
                final double z = pos.getZ() + Math.random();
                world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, Math.random() * 0.08D, 0.0D);
            }
        }
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
