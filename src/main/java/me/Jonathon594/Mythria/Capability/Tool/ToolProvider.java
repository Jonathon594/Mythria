package me.Jonathon594.Mythria.Capability.Tool;

import me.Jonathon594.Mythria.Interface.IModularTool;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ToolProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(ITool.class)
    public static final Capability<ITool> TOOL_CAP = null;
    private final ITool instance = new Tool();

    public ToolProvider(IModularTool item) {
        instance.getInventory().setStackInSlot(0, new ItemStack(item.getDefaultHandle(), 1));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return TOOL_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }

    public static Tool getTool(ItemStack itemStack) {
        return (Tool) itemStack.getCapability(TOOL_CAP, null).orElse(null);
    }

    @Override
    public INBT serializeNBT() {
        return TOOL_CAP.getStorage().writeNBT(TOOL_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        TOOL_CAP.getStorage().readNBT(TOOL_CAP, instance, null, nbt);
    }
}
