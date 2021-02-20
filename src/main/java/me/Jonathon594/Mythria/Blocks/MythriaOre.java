package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Interface.IMinableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.BlockEvent;

public class MythriaOre extends MythriaBlock implements IMinableBlock {
    public MythriaOre(Material material, String name, double weight) {
        super(name, weight, Block.Properties.create(material).hardnessAndResistance(3.0f, 3.0f));
    }

    @Override
    public void onAttemptedMiningFailed(BlockEvent.BreakEvent event, PlayerEntity player, Profile profile) {

    }
}
