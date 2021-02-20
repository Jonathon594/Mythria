package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.ContainerBlock;

public abstract class MythriaBlockContainer extends ContainerBlock implements IBlockData {
    private final double weight;

    public MythriaBlockContainer(String name, double weight, Properties properties) {
        this(name, weight, properties, false);
    }

    public MythriaBlockContainer(String name, double weight, Properties properties, boolean override) {
        super(properties);
        setRegistryName(override ? "minecraft" : Mythria.MODID, name);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
