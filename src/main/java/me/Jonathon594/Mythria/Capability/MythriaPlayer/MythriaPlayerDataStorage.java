package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MythriaPlayerDataStorage implements IStorage<IMythriaPlayer> {
    @Override
    public CompoundNBT writeNBT(final Capability<IMythriaPlayer> capability, final IMythriaPlayer instance, final Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(final Capability<IMythriaPlayer> capability, final IMythriaPlayer instance, final Direction side, final INBT nbt) {
        final CompoundNBT comp = (CompoundNBT) nbt;
        instance.fromNBT(comp);
    }
}
