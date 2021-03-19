package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.Season;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class SpawnData {
    private final double baseChance;
    private final HashMap<Biome, Double> biomeMod = new HashMap<>();
    private final HashMap<Season, Double> seasonMod = new HashMap<>();
    boolean needsRain = false;

    public SpawnData(double baseChance) {
        this.baseChance = baseChance;
    }

    public SpawnData addBiomeModifier(Biome b, double v) {
        biomeMod.put(b, v);
        return this;
    }

    public SpawnData addSeasonModifier(Season s, double v) {
        seasonMod.put(s, v);
        return this;
    }

    public double getBiomeModifer(Biome b) {
        return biomeMod.getOrDefault(b, 1.0);
    }

    public double getSeasonModifier(Season s) {
        return seasonMod.getOrDefault(s, 1.0);
    }

    public double getSpawnChance(Biome b, Season s) {
        return getBaseChance() * getBiomeModifer(b) * getSeasonModifier(s);
    }

    public boolean needsRain() {
        return needsRain;
    }

    public SpawnData setNeedsRain(boolean needsRain) {
        this.needsRain = needsRain;
        return this;
    }

    private double getBaseChance() {
        return baseChance;
    }
}
