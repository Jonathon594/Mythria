package me.Jonathon594.Mythria.Capability.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FoodProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IFood.class)
    public static final Capability<IFood> FOOD_CAP = null;
    private final IFood instance = FOOD_CAP.getDefaultInstance();

    public static Food getFood(ItemStack itemStack) {
        Food profile = (Food) itemStack.getCapability(FOOD_CAP, null).orElse(null);
        return profile;
    }

    @Override
    public INBT serializeNBT() {
        return FOOD_CAP.getStorage().writeNBT(FOOD_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        FOOD_CAP.getStorage().readNBT(FOOD_CAP, instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return FOOD_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }
}
