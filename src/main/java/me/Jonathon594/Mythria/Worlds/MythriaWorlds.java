package me.Jonathon594.Mythria.Worlds;

import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class MythriaWorlds {
    public static final RegistryKey<World> SPAWN_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new MythriaResourceLocation("spawn"));
}
