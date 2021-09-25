package me.Jonathon594.Mythria.DataTypes;

public class FuelData {
    private final int burnTime;
    private final double temperature;

    public FuelData(int burnTime, double temperature) {
        this.burnTime = burnTime;
        this.temperature = temperature;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public double getTemperature() {
        return temperature;
    }
}
