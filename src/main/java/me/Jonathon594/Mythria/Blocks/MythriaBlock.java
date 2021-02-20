package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;

public class MythriaBlock extends Block implements IBlockData {
    private final double weight;

    public MythriaBlock(String name, double weight, Properties properties) {
        super(properties);
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
