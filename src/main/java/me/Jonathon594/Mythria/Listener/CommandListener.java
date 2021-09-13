package me.Jonathon594.Mythria.Listener;

import com.mojang.brigadier.CommandDispatcher;
import me.Jonathon594.Mythria.Commands.DiscordSayCommand;
import me.Jonathon594.Mythria.Commands.SetSpawnCommand;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandListener {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        DiscordSayCommand.register(dispatcher);
        SetSpawnCommand.register(dispatcher);
    }
}
