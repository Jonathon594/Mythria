package me.Jonathon594.Mythria.Capability.Mold;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MoldProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IMold.class)
    public static final Capability<IMold> MOLD_CAP = null;
    private final IMold instance = new Mold();

    public static Mold getMold(ItemStack itemStack) {
        Mold tool = (Mold) itemStack.getCapability(MOLD_CAP, null).orElse(null);
        return tool;
    }

    @Override
    public INBT serializeNBT() {
        return MOLD_CAP.getStorage().writeNBT(MOLD_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MOLD_CAP.getStorage().readNBT(MOLD_CAP, instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return MOLD_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }
}
