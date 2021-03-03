package me.Jonathon594.Mythria.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class OreItem extends MythriaItem {
    public OreItem(String name) {
        super(name, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(10));
    }
}
