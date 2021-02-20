package me.Jonathon594.Mythria.Blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockKiln extends MythriaBlockHorizontal {
    public BlockKiln(Material material, String name, double weight, SoundType sound) {
        super(name, weight, Properties.create(material).hardnessAndResistance(30f, 30f).sound(sound));

    }
}
