package me.Jonathon594.Mythria.Capability.HeatableItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IHeatable {

    void fromNBT(CompoundNBT comp);

    long getLastUpdate();

    double getTemperature();

    void setTemperature(double temperature);

    CompoundNBT toNBT();

    void update(double ambientTemp, ItemStack is);

    void updateTime();
}
