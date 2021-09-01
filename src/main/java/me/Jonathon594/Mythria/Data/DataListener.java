package me.Jonathon594.Mythria.Data;

import me.Jonathon594.Mythria.Mythria;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataListener {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            event.getGenerator().addProvider(new MythriaLangGenerator(event.getGenerator(), Mythria.MODID, "en_us"));
        }
    }
}
