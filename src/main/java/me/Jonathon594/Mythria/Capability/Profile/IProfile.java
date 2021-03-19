package me.Jonathon594.Mythria.Capability.Profile;

import net.minecraft.nbt.CompoundNBT;

public interface IProfile {
    void fromNBT(CompoundNBT comp);

    CompoundNBT toNBT();
}
