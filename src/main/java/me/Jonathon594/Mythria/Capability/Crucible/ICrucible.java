package me.Jonathon594.Mythria.Capability.Crucible;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public interface ICrucible {
    void fromNBT(CompoundNBT nbt);

    ItemStackHandler getOreInventory();

    CompoundNBT toNBT();
}
