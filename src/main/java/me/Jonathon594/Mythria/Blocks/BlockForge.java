package me.Jonathon594.Mythria.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockForge extends MythriaBlock {
    public BlockForge(String name) {
        super(name, 0, Block.Properties.create(Material.EARTH).hardnessAndResistance(0f, 0f).sound(SoundType.GROUND));
    }
}
