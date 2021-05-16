package me.Jonathon594.Mythria.SpawnGifts;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

import java.util.List;

public class SingleItemSpawnGift extends SpawnGift {

    private final IItemProvider itemProvider;
    private int count = 1;

    public SingleItemSpawnGift(String name, IItemProvider itemProvider) {
        super(name);
        this.itemProvider = itemProvider;
    }

    public SingleItemSpawnGift withCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    protected List<ItemStack> getGiftItems() {
        return ImmutableList.of(new ItemStack(itemProvider, count));
    }
}
