package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.BlockEvent;

public interface IMinableBlock {
    void onAttemptedMiningFailed(BlockEvent.BreakEvent event, PlayerEntity player, Profile profile);
}
