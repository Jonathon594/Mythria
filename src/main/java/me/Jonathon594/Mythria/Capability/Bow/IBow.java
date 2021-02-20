package me.Jonathon594.Mythria.Capability.Bow;

import net.minecraft.nbt.CompoundNBT;

public interface IBow {
    CompoundNBT toNBT();

    void fromNBT(CompoundNBT nbt);
}
