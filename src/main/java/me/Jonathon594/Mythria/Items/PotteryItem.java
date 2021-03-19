package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IHeatChangingItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.function.Supplier;

public class PotteryItem extends HeatableItem implements IHeatChangingItem {
    private final Supplier<Item> result;

    public PotteryItem(String name, Supplier<Item> result) {
        super(name, new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
        this.result = result;
    }

    @Override
    public Item getChangeItem() {
        return result.get();
    }

    @Override
    public double getChangeTemperature() {
        return 540.0;
    }

    public Item getResult() {
        return result.get();
    }
}
