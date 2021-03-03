package me.Jonathon594.Mythria.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class MythriaItemThrowable extends MythriaItem {
    private final float damage;

    public MythriaItemThrowable(String name, int stackSize, ItemGroup group, float damage) {
        super(name, new Item.Properties().maxStackSize(stackSize).group(group));
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
}
