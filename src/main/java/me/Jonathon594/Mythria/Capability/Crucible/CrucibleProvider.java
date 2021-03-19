package me.Jonathon594.Mythria.Capability.Crucible;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrucibleProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(ICrucible.class)
    public static final Capability<ICrucible> CRUCIBLE_CAP = null;
    private final ICrucible instance = new Crucible();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CRUCIBLE_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }

    public static Crucible getCrucible(ItemStack itemStack) {
        return (Crucible) itemStack.getCapability(CRUCIBLE_CAP, null).orElse(null);
    }

    @Override
    public INBT serializeNBT() {
        return CRUCIBLE_CAP.getStorage().writeNBT(CRUCIBLE_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CRUCIBLE_CAP.getStorage().readNBT(CRUCIBLE_CAP, instance, null, nbt);
    }
}
