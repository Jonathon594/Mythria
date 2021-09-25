package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.DataTypes.FuelData;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;

public abstract class AbstractFuelableFurnaceTileEntity extends AbstractBasicFurnaceTileEntity {
    public AbstractFuelableFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn, double criticalTemperature, int size) {
        super(tileEntityTypeIn, 0, criticalTemperature, size);
    }

    @Override
    public void light() {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
        consumeFuel();
    }

    protected void consumeFuel() {
        ItemStack fuel = getFuelStack();
        FuelData fuelData = SmeltingManager.getFuelData(fuel.getItem());
        maxTemperature = fuelData.getTemperature();
        ticksLeft = fuelData.getBurnTime();
        maxTicks = fuelData.getBurnTime();
        fuel.shrink(1);
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
        if (fuel.isEmpty()) {
            setLit(false);
            return;
        }

        consumeFuel();
    }
}
