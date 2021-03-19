package me.Jonathon594.Mythria.Enum;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.IStringSerializable;

public enum EnumStone implements IStringSerializable {
    STONE(Blocks.STONE), ANDESITE(Blocks.ANDESITE), GRANITE(Blocks.GRANITE), DIORITE(Blocks.DIORITE),
    NETHERRACK(Blocks.NETHERRACK);

    private final Block block;

    EnumStone(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public static EnumStone getFromBlock(Block block) {
        for (EnumStone stone : values()) {
            if (stone.getBlock().equals(block)) return stone;
        }
        return null;
    }

    @Override
    public String getString() {
        return name().toLowerCase();
    }
}