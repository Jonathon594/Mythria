package me.Jonathon594.Mythria.Capability.Tool;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public interface ITool {
    void fromNBT(CompoundNBT nbt);

    ItemStackHandler getInventory();

    CompoundNBT toNBT();
}
