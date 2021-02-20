package me.Jonathon594.Mythria.Capability.Mold;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class MoldStorage implements IStorage<IMold> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IMold> capability, IMold instance, Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<IMold> capability, IMold instance, Direction side, INBT nbt) {
        instance.fromNBT((CompoundNBT) nbt);
    }
}
