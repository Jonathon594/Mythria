package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Managers.DiscordManager;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.List;

public class DiscordListener extends ListenerAdapter {
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        DiscordManager.sendMessageToChannel("Server started successfully.", "general");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getChannel().getName().equalsIgnoreCase("general")) {
            final List<ServerPlayerEntity> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
            for (final ServerPlayerEntity player : players) {
                String message = TextFormatting.GREEN + "Discord " + TextFormatting.WHITE + "(" +
                        TextFormatting.BLUE + event.getAuthor().getName() + TextFormatting.WHITE + "): " +
                        event.getMessage().getContentRaw();
                player.sendMessage(new StringTextComponent(message), Util.DUMMY_UUID);
            }
        }
    }
}
