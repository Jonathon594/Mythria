package me.Jonathon594.Mythria.Capability.Food;

import me.Jonathon594.Mythria.Managers.FoodManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class Food implements IFood {
    private long origin = 0;
    private double cooked = 0;
    private long age = 0;

    @Override
    public void fromNBT(final CompoundNBT comp) {
        if (comp == null)
            return;
        origin = comp.getLong("Origin");
        cooked = comp.getDouble("Cooked");
        age = comp.getLong("Age");
    }

    @Override
    public long getAge() {
        return age;
    }

    @Override
    public void setAge(final long age) {
        this.age = age;
    }

    @Override
    public double getCooked() {
        return cooked;
    }

    @Override
    public void setCooked(final double cooked) {
        this.cooked = cooked;
    }

    @Override
    public long getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(final long origin) {
        this.origin = origin;
    }

    @Override
    public CompoundNBT toNBT() {
        final CompoundNBT comp = new CompoundNBT();
        comp.putLong("Origin", origin);
        comp.putDouble("Cooked", cooked);
        comp.putLong("Age", age);
        return comp;
    }

    @Override
    public double getAgeProportion(ItemStack is) {
        final long age = getAge();
        final long maxAge = FoodManager.getFoodLifeTime(is.getItem());
        return ((double) age / maxAge);
    }

}
