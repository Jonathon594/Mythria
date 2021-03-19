package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.ContainerBlock;

public abstract class MythriaBlockContainer extends ContainerBlock {

    public MythriaBlockContainer(String name, Properties properties) {
        this(name, properties, false);
    }

    public MythriaBlockContainer(String name, Properties properties, boolean override) {
        super(properties);
        setRegistryName(override ? "minecraft" : Mythria.MODID, name);
    }
}
