package me.Jonathon594.Mythria.Capability.Profile;

import net.minecraft.nbt.CompoundNBT;

public interface IProfile {
    CompoundNBT toNBT();

    void fromNBT(CompoundNBT comp);
}
