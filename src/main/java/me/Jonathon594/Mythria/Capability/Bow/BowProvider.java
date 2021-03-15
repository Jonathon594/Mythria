package me.Jonathon594.Mythria.Capability.Bow;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BowProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IBow.class)
    public static final Capability<IBow> BOW_CAP = null;
    private final IBow instance = new Bow();

    public static Bow getBow(ItemStack itemStack) {
        return (Bow) itemStack.getCapability(BOW_CAP, null).orElse(null);
    }

    @Override
    public INBT serializeNBT() {
        return BOW_CAP.getStorage().writeNBT(BOW_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        BOW_CAP.getStorage().readNBT(BOW_CAP, instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return BOW_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }
}
