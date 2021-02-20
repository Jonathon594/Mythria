package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.item.Item;

public class FuelData {
    private final Item item;
    private final int burnTime;
    private final double temperature;

    public FuelData(Item item, int burnTime, double temperature) {
        this.item = item;
        this.burnTime = burnTime;
        this.temperature = temperature;
    }

    public Item getItem() {
        return item;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public double getTemperature() {
        return temperature;
    }
}
