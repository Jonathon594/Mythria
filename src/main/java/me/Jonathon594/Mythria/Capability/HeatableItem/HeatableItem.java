package me.Jonathon594.Mythria.Capability.HeatableItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class HeatableItem implements IHeatable {
    private double temperature = 0;
    private long lastUpdate = 0;

    public HeatableItem() {
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void fromNBT(final CompoundNBT comp) {
        if (comp == null)
            return;
        temperature = comp.getDouble("Temperature");
        lastUpdate = comp.getLong("LastUpdate");
    }

    @Override
    public long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public CompoundNBT toNBT() {
        final CompoundNBT comp = new CompoundNBT();
        comp.putDouble("Temperature", temperature);
        comp.putLong("LastUpdate", lastUpdate);
        return comp;
    }

    @Override
    public void update(double ambientTemp, ItemStack is) {
        long currentTime = System.currentTimeMillis();
        long lastTime = getLastUpdate();
        long delta = currentTime - lastTime;

        double seconds = delta / 1000D;

        double temp = getTemperature();
        double newTemp = Math.max(temp - 1 * seconds, ambientTemp);
        setTemperature(newTemp);
        updateTime();
    }

    @Override
    public void updateTime() {
        lastUpdate = System.currentTimeMillis();
    }
}


