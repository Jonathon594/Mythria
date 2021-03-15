package me.Jonathon594.Mythria.Capability.Bow;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IBow {
    ItemStack getArrow();

    void setArrow(ItemStack arrow);

    CompoundNBT toNBT();

    void fromNBT(CompoundNBT nbt);

    ItemStack getBowstring();

    void setBowstring(ItemStack bowstring);
}
