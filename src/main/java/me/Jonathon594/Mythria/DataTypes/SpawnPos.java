package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class SpawnPos {
    public static final SpawnPos ZERO = new SpawnPos(0, 0);
    private int x, z;
    private RegistryKey<World> dimension = World.OVERWORLD;

    public SpawnPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public SpawnPos(int x, int z, RegistryKey<World> dimension) {
        this.x = x;
        this.z = z;
        this.dimension = dimension;
    }

    public RegistryKey<World> getDimension() {
        return dimension;
    }

    public int getX() {
        return x;
    }

    public SpawnPos setX(int x) {
        this.x = x;
        return this;
    }

    public int getZ() {
        return z;
    }

    public SpawnPos setZ(int z) {
        this.z = z;
        return this;
    }
}
