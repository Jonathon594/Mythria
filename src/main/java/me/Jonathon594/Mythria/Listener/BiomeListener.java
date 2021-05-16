package me.Jonathon594.Mythria.Listener;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class BiomeListener {
    private static final ArrayList<Pair<EntityType, EntityType>> entitySpawnReplacements = new ArrayList<>();

    public static void addEntityReplacement(EntityType replace, EntityType with) {
        entitySpawnReplacements.add(new Pair<>(replace, with));
    }

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        for (EntityClassification classification : EntityClassification.values()) {
            ArrayList<MobSpawnInfo.Spawners> replacements = new ArrayList<>();
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(classification);
            for (Pair<EntityType, EntityType> pair : entitySpawnReplacements) {
                for (MobSpawnInfo.Spawners spawner : spawns) {
                    if (spawner.type.equals(pair.getFirst())) {
                        MobSpawnInfo.Spawners replacement = new MobSpawnInfo.Spawners(pair.getSecond(), spawner.itemWeight,
                                spawner.minCount, spawner.maxCount);
                        replacements.add(replacement);
                    }
                }
                spawns.removeIf(spawner -> spawner.type.equals(pair.getFirst()));
            }
            spawns.addAll(replacements);
        }
    }
}
