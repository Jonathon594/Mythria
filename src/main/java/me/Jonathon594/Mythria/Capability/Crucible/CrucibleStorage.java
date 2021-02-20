package me.Jonathon594.Mythria.Capability.Crucible;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class CrucibleStorage implements IStorage<ICrucible> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ICrucible> capability, ICrucible instance, Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<ICrucible> capability, ICrucible instance, Direction side, INBT nbt) {
        instance.fromNBT((CompoundNBT) nbt);
    }
}
