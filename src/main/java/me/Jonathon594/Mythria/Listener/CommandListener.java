package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Commands.DiscordSayCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandListener {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        DiscordSayCommand.register(event.getDispatcher());
    }
}
