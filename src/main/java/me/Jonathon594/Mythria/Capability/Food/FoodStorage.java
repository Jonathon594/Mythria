package me.Jonathon594.Mythria.Capability.Food;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class FoodStorage implements IStorage<IFood> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IFood> capability, IFood instance, Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<IFood> capability, IFood instance, Direction side, INBT nbt) {
        instance.fromNBT((CompoundNBT) nbt);
    }
}
