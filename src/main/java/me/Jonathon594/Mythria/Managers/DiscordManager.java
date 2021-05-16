package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Config.ServerGlobalConfig;
import me.Jonathon594.Mythria.Enum.ChatChannel;
import me.Jonathon594.Mythria.Listener.DiscordListener;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.ServerChatEvent;

import javax.security.auth.login.LoginException;
import java.util.List;

public class DiscordManager {
    private static JDA jda = null;

    public static JDA getJda() {
        return jda;
    }

    public static void init() {
        String token = ServerGlobalConfig.discordToken;
        if (token.isEmpty()) return;
        try {
            jda = JDABuilder.createDefault(token).addEventListeners(new DiscordListener()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToChannel(String text, String channelName) {
        TextChannel general = getDiscordChannelByName(channelName);
        if (general == null) return;
        general.sendMessage(text).queue();
    }

    public static TextChannel getDiscordChannelByName(String name) {
        List<TextChannel> channels = jda.getTextChannelsByName(name, true);
        if (channels.size() == 0) return null;
        return channels.get(0);
    }

    public static void handleDiscordChat(ServerChatEvent event, ServerPlayerEntity sender, Profile profile, ChatChannel channel) {
        JDA jda = getJda();
        if (jda != null) {
            if (channel.equals(ChatChannel.OOC)) {
                sendMessageToChannel(profile.getFirstName() + " " + profile.getLastName() + " (" + sender.getName() + "): " +
                                event.getMessage(),
                        "general");
            } else if (channel.equals(ChatChannel.PRAY)) {
                sendMessageToChannel(profile.getFirstName() + " " + profile.getLastName() + " (" + sender.getName() + "): " +
                                event.getMessage() + " " + sender.getPosition().toString(),
                        "pray");
            } else {
                sendMessageToChannel("[" + MythriaUtil.capitalize(channel.name()) + "] " + profile.getFirstName() + " " + profile.getLastName() + " (" + sender.getName() + "): " +
                        event.getMessage(), "local-log");
            }
        }
    }
}
