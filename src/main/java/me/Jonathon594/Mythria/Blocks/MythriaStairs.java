package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

import java.util.function.Supplier;

public class MythriaStairs extends StairsBlock {
    private final double weight;

    public MythriaStairs(Supplier<BlockState> state, String name, double weight, Block.Properties properties) {
        super(state, properties);
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
    }
}
