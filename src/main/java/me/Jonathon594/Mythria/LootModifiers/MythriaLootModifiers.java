package me.Jonathon594.Mythria.LootModifiers;

import me.Jonathon594.Mythria.Mythria;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MythriaLootModifiers {
    @SubscribeEvent
    public static void onRegisterLootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().registerAll(
                new ItemReplacementModifier.Serializer().setRegistryName(Mythria.MODID, "item_replacer")
        );
    }
}
