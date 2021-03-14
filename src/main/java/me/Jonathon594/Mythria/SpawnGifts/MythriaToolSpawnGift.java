package me.Jonathon594.Mythria.SpawnGifts;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class MythriaToolSpawnGift extends SpawnGift {
    private Random random;
    private Supplier<List<? extends IItemProvider>> validToolHeads;
    private Supplier<List<? extends IItemProvider>> validToolHandles;
    private Function<ItemStack, ITextComponent> nameFactory = (itemstack) -> itemstack.getDisplayName();
    private Function<ItemStack, Integer> damageFactory = (itemstack) -> 0;

    public MythriaToolSpawnGift(String name, Supplier<List<? extends IItemProvider>> validToolHeads,
                                Supplier<List<? extends IItemProvider>> validToolHandles) {
        super(name);
        this.validToolHeads = validToolHeads;
        this.validToolHandles = validToolHandles;
        random = new Random();
    }

    public MythriaToolSpawnGift withNameFactory(Function<ItemStack, ITextComponent> nameFactory) {
        this.nameFactory = nameFactory;
        return this;
    }

    public MythriaToolSpawnGift withDamageFactory(Function<ItemStack, Integer> damageFactory) {
        this.damageFactory = damageFactory;
        return this;
    }

    @Override
    protected List<ItemStack> getGiftItems() {
        List<? extends IItemProvider> toolHeads = validToolHeads.get();
        List<? extends IItemProvider> toolHandles = validToolHandles.get();
        ItemStack toolItem = new ItemStack(toolHeads.get(random.nextInt(toolHeads.size())), 1);
        ToolProvider.getTool(toolItem).getInventory()
                .setStackInSlot(0, new ItemStack(toolHandles.get(random.nextInt(toolHandles.size())), 1));
        toolItem.setDisplayName(nameFactory.apply(toolItem));
        toolItem.setDamage(damageFactory.apply(toolItem));
        return ImmutableList.of(toolItem);
    }
}
