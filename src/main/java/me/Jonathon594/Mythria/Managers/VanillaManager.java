package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Iterator;

public class VanillaManager {

    public static void init() {
        Iterator<Item> itemIterator = ForgeRegistries.ITEMS.iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.getRegistryName().getNamespace().equalsIgnoreCase(Mythria.MODID)) continue;
            if (item instanceof IItemData) continue;
            setStackSize(item, 1);
        }

        setStackSize(ArrowItem.class, 16);
        setStackSize(Items.BONE, 6);
        setStackSize(Items.CLAY_BALL, 16);
        setStackSize(Items.COAL, 12);
        setStackSize(Items.DIAMOND, 8);
        setStackSize(DyeItem.class, 10);
        setStackSize(Items.EGG, 12);
        setStackSize(Items.EMERALD, 16);
        setStackSize(Items.FEATHER, 32);
        setStackSize(Items.FLINT, 6);
        setStackSize(Items.GHAST_TEAR, 8);
        setStackSize(Items.GLOWSTONE_DUST, 12);
        setStackSize(Items.GOLD_INGOT, 4);
        setStackSize(Items.GOLD_NUGGET, 4);
        setStackSize(Items.IRON_INGOT, 4);
        setStackSize(Items.IRON_NUGGET, 4);
        setStackSize(Items.LEAD, 5);
        setStackSize(Items.LEATHER, 6);
        setStackSize(Items.MAGMA_CREAM, 6);
        setStackSize(Items.MELON_SEEDS, 10);
        setStackSize(Items.NETHER_WART, 10);
        setStackSize(Items.PAPER, 12);
        setStackSize(Items.PRISMARINE_CRYSTALS, 12);
        setStackSize(Items.PRISMARINE_SHARD, 12);
        setStackSize(Items.PUMPKIN_SEEDS, 10);
        setStackSize(Items.QUARTZ, 6);
        setStackSize(Items.REDSTONE, 10);
        setStackSize(Items.SNOWBALL, 8);
        setStackSize(Items.SPECTRAL_ARROW, 16);
        setStackSize(Items.STICK, 10);
        setStackSize(Items.STRING, 10);
        setStackSize(Items.TIPPED_ARROW, 16);
        setStackSize(Items.WHEAT_SEEDS, 10);
        setStackSize(Blocks.BROWN_MUSHROOM, 4);
        setStackSize(Blocks.COCOA, 4);
        setStackSize(Blocks.CORNFLOWER, 4);
        setStackSize(Blocks.SUNFLOWER, 4);
        setStackSize(Blocks.RED_MUSHROOM, 4);
        setStackSize(Blocks.REDSTONE_TORCH, 10);
        setStackSize(Blocks.TORCH, 10);
        setStackSize(Blocks.REDSTONE_TORCH, 10);
    }

    private static void setStackSize(final Item item, final int i) {
        ObfuscationReflectionHelper.setPrivateValue(Item.class, item, i, "field_77777_bU");
    }

    private static void setStackSize(final Block block, final int i) {
        setStackSize(Item.BLOCK_TO_ITEM.get(block), i);
    }

    private static void setStackSize(Class<? extends Item> clazz, final int i) {
        for (Item item : MythriaUtil.getAllItemsOfType(clazz)) {
            setStackSize(item, i);
        }
    }
}
