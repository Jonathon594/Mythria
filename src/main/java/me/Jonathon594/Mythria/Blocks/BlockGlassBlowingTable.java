package me.Jonathon594.Mythria.Blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGlassBlowingTable extends MythriaBlock {
    public BlockGlassBlowingTable(Material material, String name, double weight, SoundType sound) {
        super(name, weight, Properties.create(material).hardnessAndResistance(30f, 30f).sound(sound));

    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
