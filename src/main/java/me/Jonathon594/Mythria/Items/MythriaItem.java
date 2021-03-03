package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.Item;

public class MythriaItem extends Item {

    public MythriaItem(String name, Properties properties) {
        this(name, properties, false);
    }

    public MythriaItem(String name, Properties properties, boolean override) {
        super(properties);
        String modid = override ? "minecraft" : Mythria.MODID;
        setRegistryName(modid, name);
    }
}
