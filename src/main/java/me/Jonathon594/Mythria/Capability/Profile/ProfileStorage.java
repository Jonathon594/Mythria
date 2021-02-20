package me.Jonathon594.Mythria.Capability.Profile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class ProfileStorage implements IStorage<IProfile> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IProfile> capability, IProfile instance, Direction side) {
        final CompoundNBT comp = instance.toNBT();
        return comp;
    }

    @Override
    public void readNBT(Capability<IProfile> capability, IProfile instance, Direction side, INBT nbt) {
        final CompoundNBT comp = (CompoundNBT) nbt;
        instance.fromNBT(comp);
    }
}
