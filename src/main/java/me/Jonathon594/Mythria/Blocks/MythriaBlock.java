package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;

public class MythriaBlock extends Block {
    private final double weight;

    public MythriaBlock(String name, double weight, Properties properties) {
        this(name, weight, properties, false);
    }

    public MythriaBlock(String name, double weight, Properties properties, boolean override) {
        super(properties);
        setRegistryName(override ? "minecraft" : Mythria.MODID, name);
        this.weight = weight;
    }
}
