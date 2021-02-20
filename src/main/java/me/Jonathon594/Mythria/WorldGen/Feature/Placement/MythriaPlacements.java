package me.Jonathon594.Mythria.WorldGen.Feature.Placement;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class MythriaPlacements {
    public static final Placement<FeatureSpreadConfig> GROUND_COVER = null;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Placement<?>> event) {
        event.getRegistry().registerAll(
                new GroundCoverPlacement(FeatureSpreadConfig.CODEC).setRegistryName("ground_cover")
        );
    }
}
