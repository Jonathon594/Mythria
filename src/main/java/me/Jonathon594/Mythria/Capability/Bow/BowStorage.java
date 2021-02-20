package me.Jonathon594.Mythria.Capability.Bow;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class BowStorage implements IStorage<IBow> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IBow> capability, IBow instance, Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<IBow> capability, IBow instance, Direction side, INBT nbt) {
        instance.fromNBT((CompoundNBT) nbt);
    }
}
