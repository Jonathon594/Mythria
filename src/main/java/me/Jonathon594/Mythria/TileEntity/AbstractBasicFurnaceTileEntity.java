package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Capability.Crucible.Crucible;
import me.Jonathon594.Mythria.Capability.Crucible.CrucibleProvider;
import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableProvider;
import me.Jonathon594.Mythria.Interface.IFurnaceBase;
import me.Jonathon594.Mythria.Interface.IHeatChangingItem;
import me.Jonathon594.Mythria.Interface.IHeatProvider;
import me.Jonathon594.Mythria.Interface.ILightable;
import me.Jonathon594.Mythria.Items.CrucibleItem;
import me.Jonathon594.Mythria.Items.HeatableItem;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class AbstractBasicFurnaceTileEntity extends TileEntity implements IInventory, ILightable, ITickableTileEntity, IFurnaceBase, IHeatProvider {
    protected final NonNullList<ItemStack> inventory;
    protected final double criticalTemperature;
    protected int maxTicks;
    protected float friction = 0;
    protected double temperature;
    protected double maxTemperature = 0;
    protected boolean lit = false;
    protected int ticksLeft = 0;

    public AbstractBasicFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn, int maxTicks, double criticalTemperature, int size) {
        super(tileEntityTypeIn);
        this.maxTicks = maxTicks;
        this.ticksLeft = maxTicks;
        this.criticalTemperature = criticalTemperature;
        inventory = NonNullList.withSize(size, ItemStack.EMPTY);
    }


    public boolean addItem(ItemStack itemStackIn) {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.get(i);
            if (itemstack.isEmpty() && itemStackIn.getItem() instanceof HeatableItem) {
                setInventorySlotContents(i, itemStackIn.split(1));
                this.inventoryChanged();
                return true;
            }
        }

        return false;
    }

    public void clear() {
        this.inventory.clear();
    }

    public void dropAllItems() {
        if (!this.getWorld().isRemote) {
            InventoryHelper.dropItems(this.getWorld(), this.getPos(), this.getInventory());
        }

        this.inventoryChanged();
    }

    /**
     * Returns a NonNullList<ItemStack> of items currently held in the campfire.
     */
    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    public double getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public double getTemp() {
        return temperature;
    }

    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.inventory.size() ? this.inventory.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index >= 0 && index < this.inventory.size()) {
            this.inventory.set(index, stack);
        }
    }

    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public double getTemperatureForHeating() {
        return temperature;
    }

    @Override
    public boolean isLit() {
        return lit;
    }

    @Override
    public boolean tryLight(double friction) {
        this.friction += Math.random() * 20;
        if (this.friction > 100) {
            light();
            return true;
        }
        return false;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(world.getBlockState(pos), pkt.getNbtCompound());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.inventory.clear();
        ItemStackHelper.loadAllItems(nbt.getCompound("inventory"), this.inventory);
        temperature = nbt.getDouble("temperature");
        maxTemperature = nbt.getDouble("maxTemperature");
        maxTicks = nbt.getInt("maxTicks");
        ticksLeft = nbt.getInt("ticksLeft");
    }

    public CompoundNBT write(CompoundNBT compound) {
        compound.putDouble("temperature", temperature);
        compound.putDouble("maxTemperature", maxTemperature);
        compound.putInt("maxTicks", maxTicks);
        compound.putInt("ticksLeft", ticksLeft);
        writeItems(compound);
        return compound;
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 13, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag() {
        return this.writeItems(new CompoundNBT());
    }

    @Override
    public void tick() {
        lit = this.getBlockState().get(BlockStateProperties.LIT);
        double ambientTemp = MythriaUtil.celciusFromBiomeTemperature(world.getBiome(pos).getTemperature(pos));
        if (temperature < ambientTemp) temperature = ambientTemp;
        if (lit) {
            temperature += getMaxTemperature() / 1200.0;
            if (temperature > getMaxTemperature()) temperature = getMaxTemperature();
            ticksLeft -= 1;

            //Server Side
            if (!world.isRemote) {
                if (world.getDayTime() % 20 == 0) {
                    processItems();
                }
            } else {
                this.addParticles();
            }

            if (criticalTemperature > 0) {
                double chanceOfBoom = Math.pow(Math.max(getTemp() - criticalTemperature, 0), 1.5) * 0.000001;
                if (Math.random() < chanceOfBoom) {
                    explode();
                }
            }

            if (ticksLeft <= 0) {
                onFinishBurning();
            }
        } else {
            if (friction > 1)
                friction--;

            if (temperature > ambientTemp) temperature -= 0.1;
        }
    }

    private void explode() {

    }

    protected void inventoryChanged() {
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    protected CompoundNBT writeItems(CompoundNBT compound) {
        super.write(compound);
        CompoundNBT inventory = new CompoundNBT();
        ItemStackHelper.saveAllItems(inventory, this.inventory, true);
        compound.put("inventory", inventory);
        return compound;
    }

    protected void processItems() {
        NonNullList<ItemStack> inventory = this.getInventory();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.get(i);
            if (stack.isEmpty()) continue;
            me.Jonathon594.Mythria.Capability.HeatableItem.HeatableItem heatableItem = HeatableProvider.getHeatable(stack);
            if (heatableItem == null) continue;
            double currentTemp = heatableItem.getTemperature();
            double newTemp = Math.min(currentTemp + getHeatingRate(currentTemp, temperature), maxTemperature);
            Item item = stack.getItem();
            heatableItem.setTemperature(newTemp);
            heatableItem.updateTime();

            if (item instanceof IHeatChangingItem) {
                IHeatChangingItem iHeatChangingItem = (IHeatChangingItem) item;
                if (heatableItem.getTemperature() > iHeatChangingItem.getChangeTemperature()) {
                    ItemStack newStack = new ItemStack(iHeatChangingItem.getChangeItem(), stack.getCount());
                    inventory.set(i, newStack);
                    me.Jonathon594.Mythria.Capability.HeatableItem.HeatableItem newHeatableItem = HeatableProvider.getHeatable(newStack);
                    newHeatableItem.setTemperature(heatableItem.getTemperature());
                }
            }

            if (item instanceof CrucibleItem) {
                Crucible crucible = CrucibleProvider.getCrucible(stack);
                crucible.createMetal();
            }
        }
    }

    protected void addParticles() {
        World world = this.getWorld();
        if (world != null) {
            BlockPos blockpos = this.getPos();
            Random random = world.rand;

            if (canSpawnSmokeParticles()) {
                if (random.nextFloat() < 0.11F) {
                    for (int i = 0; i < random.nextInt(2) + 2; ++i) {
                        MythriaUtil.spawnSmokeParticles(world, blockpos, shouldSpawnSignalSmoke(), false, getSmokeParticleOffset());
                    }
                }
            }
        }
    }

    protected double getSmokeParticleOffset() {
        return 0.0;
    }

    protected abstract boolean shouldSpawnSignalSmoke();

    protected abstract boolean canSpawnSmokeParticles();

    protected void onFinishBurning() {

    }

    protected double getHeatingRate(double objectTemp, double furnaceTemp) {
        return MythriaUtil.thermalConduction(getHeatingEfficiency(), objectTemp, furnaceTemp);
    }

    protected abstract double getHeatingEfficiency();

}
