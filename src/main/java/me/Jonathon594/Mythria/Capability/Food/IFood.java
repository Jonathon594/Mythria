package me.Jonathon594.Mythria.Capability.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IFood {

    void fromNBT(CompoundNBT comp);

    long getAge();

    void setAge(long age);

    double getCooked();

    void setCooked(double cooked);

    long getOrigin();

    void setOrigin(long origin);

    CompoundNBT toNBT();

    double getAgeProportion(ItemStack is);
}
