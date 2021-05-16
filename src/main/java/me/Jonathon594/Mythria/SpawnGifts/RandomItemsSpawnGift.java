package me.Jonathon594.Mythria.SpawnGifts;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class RandomItemsSpawnGift extends SpawnGift {
    private final Supplier<List<? extends IItemProvider>> validItems;
    private final int count;

    public RandomItemsSpawnGift(String name, Supplier<List<? extends IItemProvider>> validItems, int count) {
        super(name);
        this.validItems = validItems;
        this.count = count;
    }

    @Override
    protected List<ItemStack> getGiftItems() {
        List<? extends IItemProvider> copy = new ArrayList<>(validItems.get());
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Collections.shuffle(copy);
            itemStacks.add(new ItemStack(copy.get(0), 1));
        }
        return itemStacks;
    }
}
