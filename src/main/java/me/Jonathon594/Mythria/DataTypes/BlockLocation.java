package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.util.math.BlockPos;

public class BlockLocation {
    private final BlockPos position;
    private final int dimension;

    public BlockLocation(BlockPos position, int dimension) {
        this.position = position;
        this.dimension = dimension;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockLocation) {
            BlockLocation other = (BlockLocation) obj;
            return other.position.equals(position) && other.dimension == dimension;
        }
        return false;
    }

    public int getDimension() {
        return dimension;
    }

    public BlockPos getPosition() {
        return position;
    }
}
