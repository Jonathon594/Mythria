package me.Jonathon594.Mythria.TileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public abstract class FuelableFurnaceTileEntity extends BasicFurnaceTileEntity {
    public FuelableFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn, double criticalTemperature, int size) {
        super(tileEntityTypeIn, 0, criticalTemperature, size);
    }

    private void consumeFuel() {

    }

    protected abstract ItemStack getFuelStack();

    protected abstract void setLit(boolean lit);

    @Override
    protected boolean shouldSpawnSignalSmoke() {
        return false;
    }

    @Override
    protected boolean canSpawnSmokeParticles() {
        return true;
    }

    @Override
    protected void onFinishBurning() {
        if (world.isRemote) return;
        ItemStack fuel = getFuelStack();
        if (fuel.isEmpty())
            setLit(false);

        fuel.shrink(1);
        consumeFuel();
    }

    @Override
    protected boolean hasFlameParticles() {
        return true;
    }
}
