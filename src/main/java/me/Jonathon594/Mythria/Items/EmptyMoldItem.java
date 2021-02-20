package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Enum.EnumMetalShape;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class EmptyMoldItem extends HeatableItem {
    private final EnumMetalShape shape;
    private final int volume;

    public EmptyMoldItem(String name, double weight, int volume, EnumMetalShape shape) {
        super(name, weight, new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
        this.volume = volume;
        this.shape = shape;
    }

    public EnumMetalShape getShape() {
        return shape;
    }

    public int getVolume() {
        return volume;
    }
}
