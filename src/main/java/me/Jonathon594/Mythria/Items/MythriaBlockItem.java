package me.Jonathon594.Mythria.Items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class MythriaBlockItem extends BlockItem {
    public MythriaBlockItem(Block block, Properties properties) {
        super(block, properties);
        setRegistryName(block.getRegistryName());
    }
}
