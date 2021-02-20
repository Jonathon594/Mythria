package me.Jonathon594.Mythria.WorldGen.Feature;

import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.WorldGen.Feature.Config.GroundCoverConfig;
import me.Jonathon594.Mythria.WorldGen.Feature.Config.GroundStickConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class MythriaFeatures {
    public static final Feature<NoFeatureConfig> ORE_GEN = null;
    public static final Feature<ProbabilityConfig> GROUND_ROCK_GEN = null;
    public static final Feature<ProbabilityConfig> GROUND_STICK_GEN = null;
    public static final Feature<ProbabilityConfig> GROUND_FLINT_GEN = null;

    @SubscribeEvent
    public static void onRegisterFeature(RegistryEvent.Register<Feature<?>> event) {
        MythriaOreFeature.init();
        event.getRegistry().registerAll(
                new MythriaOreFeature("ore_gen", NoFeatureConfig.field_236558_a_),
                new GroundCoverFeature("ground_rock_gen", GroundCoverConfig.CODEC),
                new GroundStickFeature("ground_stick_gen", GroundStickConfig.CODEC),
                new GroundCoverFeature("ground_flint_gen", GroundCoverConfig.CODEC)
        );
    }
}
