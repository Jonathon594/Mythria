package me.Jonathon594.Mythria.Blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBookshelf extends MythriaBlock {
    public BlockBookshelf(String name, double weight) {
        super(name, weight, Properties.create(Material.WOOD).hardnessAndResistance(0f, 0f).sound(SoundType.WOOD));
    }
}
