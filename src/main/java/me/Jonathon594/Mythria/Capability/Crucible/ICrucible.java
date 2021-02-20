package me.Jonathon594.Mythria.Capability.Crucible;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public interface ICrucible {
    CompoundNBT toNBT();

    void fromNBT(CompoundNBT nbt);

    ItemStackHandler getOreInventory();
}
