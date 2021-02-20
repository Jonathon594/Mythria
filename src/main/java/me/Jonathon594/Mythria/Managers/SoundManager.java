package me.Jonathon594.Mythria.Managers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundManager {

    public static void playForAllNearby(final Entity entity, final SoundEvent blockFireExtinguish) {
        entity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), blockFireExtinguish, SoundCategory.MASTER,
                1.0f, 1.0f);
    }

    public static void playForPlayerOnly(final ServerPlayerEntity player, final SoundEvent sound, float vol) {
        playForPlayerOnly(player, player.getPosX(), player.getPosY(), player.getPosZ(), sound, vol, 1.0f);
    }

    private static void playForPlayerOnly(final ServerPlayerEntity player, final double getPosX, final double getPosY,
                                          final double getPosZ, final SoundEvent sound, final float volume, final float pitch) {
        player.connection
                .sendPacket(new SPlaySoundEffectPacket(sound, SoundCategory.MASTER, getPosX, getPosY, getPosZ, volume, pitch));
    }

}
