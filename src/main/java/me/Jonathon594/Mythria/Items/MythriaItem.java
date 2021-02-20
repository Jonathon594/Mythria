package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.Item;

public class MythriaItem extends Item implements IItemData {
    private final double weight;

    public MythriaItem(String name, double weight, Properties properties) {
        this(name, weight, properties, false);
    }

    public MythriaItem(String name, double weight, Properties properties, boolean override) {
        super(properties);
        String modid = override ? "minecraft" : Mythria.MODID;
        setRegistryName(modid, name);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
