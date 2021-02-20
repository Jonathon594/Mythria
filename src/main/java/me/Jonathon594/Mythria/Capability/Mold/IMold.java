package me.Jonathon594.Mythria.Capability.Mold;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public interface IMold {
    CompoundNBT toNBT();

    void fromNBT(CompoundNBT nbt);

    ItemStackHandler getInventory();
}
