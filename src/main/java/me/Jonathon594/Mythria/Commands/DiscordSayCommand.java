package me.Jonathon594.Mythria.Commands;

import com.mojang.brigadier.CommandDispatcher;
import me.Jonathon594.Mythria.Managers.DiscordManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.util.text.ITextComponent;

public class DiscordSayCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("discord").requires((source) -> {
            return source.hasPermissionLevel(2);
        }).then(Commands.argument("message", MessageArgument.message()).executes((context) -> {
            ITextComponent message = MessageArgument.getMessage(context, "message");
            DiscordManager.sendMessageToChannel(message.getString(), "general");
            return 1;
        })));
    }
}
