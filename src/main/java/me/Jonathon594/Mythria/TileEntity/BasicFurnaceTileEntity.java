package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.CampfireBlock;
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
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BasicFurnaceTileEntity extends TileEntity implements ILightable, ITickableTileEntity, IFurnaceBase, IHeatProvider {
    protected final NonNullList<ItemStack> inventory;
    protected final double criticalTemperature;
    private final int maxTicks;
    protected float friction = 0;
    protected double temperature;
    protected double maxTemperature = 0;
    protected boolean lit = false;
    protected int ticksLeft = 0;

    public BasicFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn, int maxTicks, double criticalTemperature, int size) {
        super(tileEntityTypeIn);
        this.maxTicks = maxTicks;
        this.ticksLeft = maxTicks;
        this.criticalTemperature = criticalTemperature;
        inventory = NonNullList.withSize(size, ItemStack.EMPTY);
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

    protected double getHeatingRate(double objectTemp, double furnaceTemp) {
        return MythriaUtil.thermalConduction(getHeatingEfficiency(), objectTemp, furnaceTemp);
    }

    protected abstract double getHeatingEfficiency();

    protected void addParticles() {
        World world = this.getWorld();
        if (world != null) {
            BlockPos blockpos = this.getPos();
            Random random = world.rand;
            if (random.nextFloat() < 0.11F) {
                for (int i = 0; i < random.nextInt(2) + 2; ++i) {
                    CampfireBlock.spawnSmokeParticles(world, blockpos, false, false);
                }
            }

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

    /**
     * Returns a NonNullList<ItemStack> of items currently held in the campfire.
     */
    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    private void explode() {

    }

    protected void onFinishBurning() {

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
    public boolean isLit() {
        return lit;
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
    public double getTemperatureForHeating() {
        return temperature;
    }

    public void clear() {
        this.inventory.clear();
    }

    protected void inventoryChanged() {
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void dropAllItems() {
        if (!this.getWorld().isRemote) {
            InventoryHelper.dropItems(this.getWorld(), this.getPos(), this.getInventory());
        }

        this.inventoryChanged();
    }

    public boolean addItem(ItemStack itemStackIn) {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.get(i);
            if (itemstack.isEmpty() && itemStackIn.getItem() instanceof HeatableItem) {
                this.inventory.set(i, itemStackIn.split(1));
                this.inventoryChanged();
                return true;
            }
        }

        return false;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.inventory.clear();
        ItemStackHelper.loadAllItems(nbt, this.inventory);
    }

    public CompoundNBT write(CompoundNBT compound) {
        this.writeItems(compound);
        return compound;
    }

    protected CompoundNBT writeItems(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory, true);
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
}
