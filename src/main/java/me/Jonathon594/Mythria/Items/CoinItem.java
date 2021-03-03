package me.Jonathon594.Mythria.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CoinItem extends MythriaItem {
    public CoinItem(String name) {
        super(name, new Item.Properties().group(ItemGroup.MISC).maxStackSize(50));
    }
}
