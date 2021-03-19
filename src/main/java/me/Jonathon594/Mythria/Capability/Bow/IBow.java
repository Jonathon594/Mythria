package me.Jonathon594.Mythria.Capability.Bow;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IBow {
    void fromNBT(CompoundNBT nbt);

    ItemStack getArrow();

    void setArrow(ItemStack arrow);

    ItemStack getBowstring();

    void setBowstring(ItemStack bowstring);

    CompoundNBT toNBT();
}
