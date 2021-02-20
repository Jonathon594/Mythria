package me.Jonathon594.Mythria.Blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAnvil extends MythriaBlockHorizontal {
    public BlockAnvil(Material material, String name, SoundType sound, double weight, int tier) {
        super(name, weight, Properties.create(material).hardnessAndResistance(30f, 30f).sound(sound));
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
