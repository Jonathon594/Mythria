package me.Jonathon594.Mythria.Capability.HeatableItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HeatableProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IHeatable.class)
    public static final Capability<IHeatable> HEATABLE_CAP = null;
    private final IHeatable instance = new HeatableItem();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return HEATABLE_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }

    public static HeatableItem getHeatable(ItemStack itemStack) {
        return (HeatableItem) itemStack.getCapability(HEATABLE_CAP, null).orElse(null);
    }

    @Override
    public INBT serializeNBT() {
        return HEATABLE_CAP.getStorage().writeNBT(HEATABLE_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        HEATABLE_CAP.getStorage().readNBT(HEATABLE_CAP, instance, null, nbt);
    }
}
