package me.Jonathon594.Mythria.Managers;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.HashMap;

public class SpawnManager {
    private static final CommentedFileConfig CONFIG = CommentedFileConfig.of("config/spawns.toml");
    private static final HashMap<ResourceLocation, SpawnPos> spawnMap = new HashMap<>();

    public static void spawnInWorld(ServerPlayerEntity serverPlayer, Profile profile) {
        SpawnPos spawnPos = spawnMap.get(profile.getGenetic().getType());
        ServerWorld world = serverPlayer.getServer().getWorld(spawnPos.getDimension());
        BlockPos blockPos;
        int spawnHeight;
        int recursion = 0;
        while (true) {
            spawnPos = MythriaUtil.getRandomPositionAroundPoint(spawnPos, 50 + recursion, world);
            blockPos = new BlockPos(spawnPos.getX(), 0, spawnPos.getZ());
            spawnHeight = getSpawnHeight(spawnPos.getDimension(), world, blockPos);
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

    public static void init() {
        if (CONFIG.isEmpty()) {
            saveDefaultConfig();
        }
        for (GeneticType type : MythriaRegistries.GENETICS) {
            int x = CONFIG.get("Spawn.Position.X");
            int z = CONFIG.get("Spawn.Position.Z");
            String world = CONFIG.get("Spawn.World");
            RegistryKey<World> spawnDimension = World.OVERWORLD;
            for (RegistryKey<World> worldRegistryKey : ImmutableList.of(World.OVERWORLD, World.THE_NETHER, World.THE_END)) {
                if (worldRegistryKey.getLocation().toString().equals(world)) spawnDimension = worldRegistryKey;
            }
            spawnMap.put(type.getRegistryName(), new SpawnPos(x, z, spawnDimension));
        }
    }

    private static void saveDefaultConfig() {
        for (GeneticType type : MythriaRegistries.GENETICS) {
            CONFIG.set(type.getRegistryName() + ".X", 0);
            CONFIG.set(type.getRegistryName() + ".Z", 0);
            CONFIG.set(type.getRegistryName() + ".World", World.OVERWORLD.getLocation().toString());
        }
        CONFIG.save();
    }
}
