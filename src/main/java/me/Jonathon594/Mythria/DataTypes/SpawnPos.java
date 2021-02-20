package me.Jonathon594.Mythria.DataTypes;

public class SpawnPos {
    public static final SpawnPos ZERO = new SpawnPos(0, 0);
    private int x, z;

    public SpawnPos(int x, int z) {
        this.x = x;
        this.z = z;
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
