package me.Jonathon594.Mythria.Capability.Tool;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class ToolStorage implements IStorage<ITool> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ITool> capability, ITool instance, Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<ITool> capability, ITool instance, Direction side, INBT nbt) {
        instance.fromNBT((CompoundNBT) nbt);
    }
}
