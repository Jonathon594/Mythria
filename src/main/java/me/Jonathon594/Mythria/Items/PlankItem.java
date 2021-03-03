package me.Jonathon594.Mythria.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class PlankItem extends MythriaItem {
    public PlankItem(String name) {
        super(name, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16));
    }
}
