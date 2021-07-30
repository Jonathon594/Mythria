package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Capability.CapabilityHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.Nullable;

public interface IShareTagCapability extends IForgeItem {
    default CompoundNBT getShareTag(ItemStack stack) {
        return CapabilityHandler.writeToShareTag(stack, stack.getTag());
    }

    default void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.setTag(nbt);
        CapabilityHandler.readFromShareTag(stack, nbt);
    }
}
