package me.Jonathon594.Mythria.Capability.HeatableItem;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class HeatableStorage implements IStorage<IHeatable> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IHeatable> capability, IHeatable instance, Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<IHeatable> capability, IHeatable instance, Direction side, INBT nbt) {
        instance.fromNBT((CompoundNBT) nbt);
    }
}
