package me.Jonathon594.Mythria.Capability.HeatableItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IHeatable {

    void fromNBT(CompoundNBT comp);

    CompoundNBT toNBT();

    void update(double ambientTemp, ItemStack is);

    void updateTime();

    double getTemperature();

    void setTemperature(double temperature);

    long getLastUpdate();
}
