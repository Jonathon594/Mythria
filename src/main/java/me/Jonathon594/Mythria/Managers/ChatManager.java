package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.ChatChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManager {
    private static final Map<ServerPlayerEntity, ChatChannel> selectedChannels = new HashMap<>();

    public static void handleForgeChat(ServerChatEvent event, ServerPlayerEntity sender, Profile p) {
        final List<ServerPlayerEntity> players = ServerLifecycleHooks.getCurrentServer().getPlayerList()
                .getPlayers();
        final ChatChannel channel = ChatManager.getChatChannel(sender);
        final int range = ChatManager.getRange(channel);
        final TextFormatting tf = ChatManager.getColor(channel);
        for (final ServerPlayerEntity player : players) {
            if (!sender.getEntityWorld().equals(player.getEntityWorld()) && range > 0)
                continue;
            if (range == 0 || sender.getDistance(player) <= range) {
                sendMessage(event, sender, p, player, tf, true, !channel.equals(ChatChannel.EVENT));
            } else if (sender.getDistance(player) <= (range + 15)) {
                sendMessage(event, sender, p, player, tf, false, true);
            }
        }

        if (channel.equals(ChatChannel.PRAY)) {
            for (final ServerPlayerEntity player : players) {
                if (player.hasPermissionLevel(2)) {
                    sendMessage(event, sender, p, player, tf, true, true);
                }
            }
        }

//        JDA jda = DiscordManager.getJda();
//        if (jda != null) {
//            if (range == 0) {
//
//                List<TextChannel> channels = jda.getTextChannelsByName("general", true);
//                channels.get(0).sendMessage(p.getFirstName() + " " + p.getLastName() + " (" + sender.getName() + "): " +
//                        event.getMessage()).queue();
//            }
//            if (channel == ChatChannel.PRAY) {
//                List<TextChannel> channels = jda.getTextChannelsByName("pray", true);
//                channels.get(0).sendMessage(p.getFirstName() + " " + p.getLastName() + " (" + sender.getName() + "): " +
//                        event.getMessage() + " " + sender.getPosition().toString()).queue();
//            }
//        }
    }

    public static ChatChannel getChatChannel(final ServerPlayerEntity player) {
        return selectedChannels.get(player);
    }

    public static int getRange(final ChatChannel channel) {
        switch (channel) {
            case OOC:
                return 0;
            case WHISPER:
                return 5;
            case YELL:
                return 40;
            case EVENT:
                return 200;
            default:
                return 20;
        }
    }

    public static TextFormatting getColor(final ChatChannel channel) {
        switch (channel) {
            case OOC:
                return TextFormatting.WHITE;
            case WHISPER:
                return TextFormatting.BLUE;
            case YELL:
                return TextFormatting.RED;
            case PRAY:
                return TextFormatting.YELLOW;
            case EVENT:
                return TextFormatting.GOLD;
            default:
                return TextFormatting.GREEN;
        }
    }

    public static void sendMessage(ServerChatEvent event, ServerPlayerEntity sender, Profile p, ServerPlayerEntity player, TextFormatting tf, boolean isInRange, boolean showName) {
        String name = sender.getName().getString();
        if (p.getCreated())
            name = TextFormatting.YELLOW + p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName();
        String message = (showName ? name + TextFormatting.WHITE + " -> " : "") + tf + event.getMessage();
        if (!isInRange && showName) {
            message = name + " says something off in the distance...";
        }
        player.sendMessage(
                new StringTextComponent(message), sender.getUniqueID());
    }

    public static void setChatChannel(final ServerPlayerEntity player, final ChatChannel channel) {
        selectedChannels.put(player, channel);
    }

    public static void toggleChatChannel(final ServerPlayerEntity player) {
        ChatChannel channel = getChatChannel(player);
        if (channel == null)
            channel = ChatChannel.LOCAL;
        switch (channel) {
            case LOCAL:
                channel = ChatChannel.YELL;
                break;
            case OOC:
                if (player.hasPermissionLevel(2)) {
                    channel = ChatChannel.EVENT;
                    break;
                }
                channel = ChatChannel.WHISPER;
                break;
            case WHISPER:
                channel = ChatChannel.LOCAL;
                break;
            case YELL:
                channel = ChatChannel.PRAY;
                break;
            case PRAY:
                channel = ChatChannel.OOC;
                break;
            case EVENT:
                channel = ChatChannel.WHISPER;
                break;
        }
        selectedChannels.put(player, channel);
    }
}
