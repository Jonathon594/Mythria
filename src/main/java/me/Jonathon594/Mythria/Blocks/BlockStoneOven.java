package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Enum.AttributeFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStoneOven extends MythriaBlockHorizontal {
    public BlockStoneOven(String bane, AttributeFlag requiredFlag, double weight) {
        super(bane, weight, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(20f, 20f));
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
