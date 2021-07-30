package me.Jonathon594.Mythria.DataTypes.Origins;

import me.Jonathon594.Mythria.Mythria;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class Origins {
    public static final Origin SURVIVOR = null;

    @SubscribeEvent
    public static void onRegisterGifts(RegistryEvent.Register<Origin> event) {
        event.getRegistry().registerAll(
                new EmptyOrigin("survivor").withDisplayName("Survivor")
        );
    }
}
