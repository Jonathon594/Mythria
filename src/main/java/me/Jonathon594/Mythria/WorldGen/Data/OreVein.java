package me.Jonathon594.Mythria.WorldGen.Data;

import me.Jonathon594.Mythria.DataTypes.OreSpawnData;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class OreVein {
    private final OreSpawnPoint[] spawnPoints;
    private final BlockPos startPosition;
    private final OreSpawnData spawnData;
    private final Random rand;

    public OreVein(BlockPos startPosition, OreSpawnData spawnData, Random rand) {
        this.startPosition = startPosition;
        this.spawnData = spawnData;
        this.rand = rand;

        int clusters = (int) (spawnData.getClusters() * (0.5 + rand.nextDouble()));
        spawnPoints = new OreSpawnPoint[clusters];
        spawnPoints[0] = new OreSpawnPoint(startPosition, 0.6 * +0.2 * rand.nextDouble());
        for (int i = 1; i < clusters; i++) {
            final BlockPos pos = startPosition.add(
                    spawnData.getHorizontalSize() * (0.3 - 0.6 * rand.nextDouble()),
                    spawnData.getVerticalSize() * (0.3 - 0.6 * rand.nextDouble()),
                    spawnData.getHorizontalSize() * (0.3 - 0.6 * rand.nextDouble())
            );
            spawnPoints[i] = new OreSpawnPoint(pos, 0.2 + 0.5 * rand.nextDouble());
        }
    }

    public BlockPos getStartPosition() {
        return startPosition;
    }

    public OreSpawnData getSpawnData() {
        return spawnData;
    }

    public Random getRand() {
        return rand;
    }

    public boolean isInRange(int x, int z) {
        double dx = Math.pow(x - startPosition.getX(), 2);
        double dz = Math.pow(z - startPosition.getZ(), 2);
        double dr = Math.pow(spawnData.getHorizontalSize(), 2);
        return dx + dz <= dr;
    }

    public double getGenerationChance(BlockPos current) {
        double shortestDistance = -1;

        for (OreSpawnPoint point : spawnPoints) {
            final double dx = Math.pow(point.getPos().getX() - current.getX(), 2);
            final double dy = Math.pow(point.getPos().getY() - current.getY(), 2);
            final double dz = Math.pow(point.getPos().getZ() - current.getZ(), 2);

            final double radius = (dx + dz) / Math.pow(point.getSize() * spawnData.getHorizontalSize(), 2)
                    + dy / Math.pow(point.getSize() * spawnData.getHorizontalSize(), 2);

            if (shortestDistance == -1 || radius < shortestDistance) shortestDistance = radius;
        }
        return 0.005 * spawnData.getDensity() * (1.0 - shortestDistance);
    }

    private class OreSpawnPoint {
        private final BlockPos pos;
        private final double size;

        private OreSpawnPoint(BlockPos pos, double size) {
            this.pos = pos;
            this.size = size;
        }

        public double getSize() {
            return size;
        }

        public BlockPos getPos() {
            return pos;
        }
    }
}
