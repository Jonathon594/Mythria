package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SpawnManager {
    public static void spawnInWorld(ServerPlayerEntity serverPlayer, Profile profile) {
        RegistryKey<World> dimensionType = profile.getGenetic().getType().getSpawnDimension();
        ServerWorld world = serverPlayer.getServer().getWorld(dimensionType);
        SpawnPos spawnPos;
        BlockPos blockPos;
        int spawnHeight;
        int recursion = 0;
        while (true) {
            spawnPos = MythriaUtil.getRandomPositionAroundPoint(profile.getGenetic().getType().getSpawnPos(), 50 + recursion, world);
            blockPos = new BlockPos(spawnPos.getX(), 0, spawnPos.getZ());
            spawnHeight = getSpawnHeight(dimensionType, world, blockPos);
            if (spawnHeight > 0) break;
            recursion++;
        }
        serverPlayer.teleport(world, blockPos.getX(), spawnHeight, blockPos.getZ(), world.rand.nextFloat() * 180, 0);
    }

    protected static int getSpawnHeight(RegistryKey<World> dimensionType, ServerWorld world, BlockPos pos) {
        while (true) {
            boolean isNether = dimensionType == World.THE_NETHER;
            BlockState state = world.getBlockState(pos);
            BlockState up = world.getBlockState(pos.up());
            if (state.isAir() && up.isAir()) {
                if (isNether || world.canSeeSky(pos)) {
                    break;
                }
            }
            pos = pos.up();
            if (pos.getY() >= 127) return -1;
        }
        return pos.getY();
    }
}
