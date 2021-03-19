package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Entity.MythriaEntityType;
import me.Jonathon594.Mythria.WorldGen.Feature.Config.GroundCoverConfig;
import me.Jonathon594.Mythria.WorldGen.Feature.Config.GroundStickConfig;
import me.Jonathon594.Mythria.WorldGen.Feature.MythriaFeatures;
import me.Jonathon594.Mythria.WorldGen.Feature.Placement.MythriaPlacements;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class WorldGenListener {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onBiomeLoadingNormal(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        List<Supplier<ConfiguredFeature<?, ?>>> underground_ores = generation.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
        for (GenerationStage.Decoration decoration : GenerationStage.Decoration.values()) {
            generation.getFeatures(decoration).removeIf(supplier -> {
                ConfiguredFeature<?, ?> configuredFeature = supplier.get();
                while (configuredFeature.getFeature() instanceof DecoratedFeature) {
                    configuredFeature = ((DecoratedFeatureConfig) configuredFeature.getConfig()).feature.get();
                }
                return configuredFeature.getFeature() instanceof OreFeature;
            });
        }

        ConfiguredPlacement<NoPlacementConfig> noPlacement = new ConfiguredPlacement<>(Placement.NOPE, new NoPlacementConfig());
        underground_ores.add(() -> new ConfiguredFeature<>(MythriaFeatures.ORE_GEN, new NoFeatureConfig()).withPlacement(noPlacement));

        List<Supplier<ConfiguredFeature<?, ?>>> localModifications = generation.getFeatures(GenerationStage.Decoration.TOP_LAYER_MODIFICATION);
        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_ROCK_GEN,
                new GroundCoverConfig(0.2f, MythriaBlocks.GROUND_ROCK.getDefaultState())).withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                new FeatureSpreadConfig(FeatureSpread.func_242253_a(1, 3)))));

        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.OAK_LEAVES.getDefaultState(), MythriaBlocks.OAK_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));
        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.BIRCH_LEAVES.getDefaultState(), MythriaBlocks.BIRCH_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));
        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.SPRUCE_LEAVES.getDefaultState(), MythriaBlocks.SPRUCE_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));
        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.JUNGLE_LEAVES.getDefaultState(), MythriaBlocks.JUNGLE_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));
        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.ACACIA_LEAVES.getDefaultState(), MythriaBlocks.ACACIA_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));
        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.DARK_OAK_LEAVES.getDefaultState(), MythriaBlocks.DARK_OAK_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));

        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.NETHER_WART_BLOCK.getDefaultState(), MythriaBlocks.CRIMSON_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));

        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_STICK_GEN,
                new GroundStickConfig(0.6f, Blocks.WARPED_WART_BLOCK.getDefaultState(), MythriaBlocks.WARPED_GROUND_STICK.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(8, 20)))));

        localModifications.add(() -> new ConfiguredFeature<>(MythriaFeatures.GROUND_FLINT_GEN,
                new GroundCoverConfig(0.1f, MythriaBlocks.GROUND_FLINT.getDefaultState()))
                .withPlacement(new ConfiguredPlacement<>(MythriaPlacements.GROUND_COVER,
                        new FeatureSpreadConfig(FeatureSpread.func_242253_a(1, 3)))));

        ResourceLocation name = event.getName();
        if (name.equals(Biomes.CRIMSON_FOREST.getLocation()) || name.equals(Biomes.WARPED_FOREST.getLocation())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(
                    new MobSpawnInfo.Spawners(MythriaEntityType.NETHER_CHICKEN, 60, 2, 6));
        }
    }
}
