package me.Jonathon594.Mythria.DataTypes;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class OreSpawnData {
    private final OreSpawnType type;
    private final int rarity;
    private final int minY;
    private final int maxY;
    private final int vSize;
    private final int hSize;
    private final int clusters;
    private final int density;
    private final List<RegistryKey<World>> dimension;
    private final Block block;
    private final ArrayList<Biome> idealBiomes;

    public OreSpawnData(List<RegistryKey<World>> dimension, Block block, OreSpawnType type, int rarity, int minY, int maxY, int vSize, int hSize, int clusters, int density) {
        this.block = block;
        this.type = type;
        this.rarity = rarity;
        this.minY = minY;
        this.maxY = maxY;
        this.vSize = vSize;
        this.hSize = hSize;
        this.dimension = dimension;
        this.clusters = clusters;
        this.density = density;
        idealBiomes = new ArrayList<>();
    }

    public OreSpawnData(RegistryKey<World> dimension, Block block, OreSpawnType type, int rarity, int minY, int maxY, int vSize, int hSize, int clusters, int density) {
        this(ImmutableList.of(dimension), block, type, rarity, minY, maxY, vSize, hSize, clusters, density);
    }

    public OreSpawnData addIdealBiome(Biome b) {
        idealBiomes.add(b);
        return this;
    }

    public OreSpawnType getType() {
        return type;
    }

    public int getSize() {
        return 0;
    }

    public int getRarity() {
        return rarity;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getVerticalSize() {
        return vSize;
    }

    public int getHorizontalSize() {
        return hSize;
    }

    public Block getBlock() {
        return block;
    }

    public ArrayList<Biome> getIdealBiomes() {
        return idealBiomes;
    }

    public List<RegistryKey<World>> getDimensions() {
        return dimension;
    }

    public int getClusters() {
        return clusters;
    }

    public int getDensity() {
        return density;
    }

    public enum OreSpawnType {
        CLUSTER, VEIN
    }
}
