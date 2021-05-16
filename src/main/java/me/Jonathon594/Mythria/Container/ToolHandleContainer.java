package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Capability.Tool.Tool;
import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Items.ToolHandleItem;
import me.Jonathon594.Mythria.Items.ToolHeadItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class ToolHandleContainer extends ToolComponentContainer {

    public ToolHandleContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.TOOL_HANDLE, windowID, playerInventory);
    }

    @Override
    public ItemStack getResultStack(ItemStack head, ItemStack handle) {
        if (!(head.getItem() instanceof ToolHeadItem)) return ItemStack.EMPTY;
        if (handle.isEmpty()) return ItemStack.EMPTY;
        ToolHeadItem headItem = (ToolHeadItem) head.getItem();
        ItemStack result = new ItemStack(headItem.getResult(), 1);
        Item resultItem = result.getItem();
        if (resultItem instanceof IModularTool) {
            IModularTool modularTool = (IModularTool) resultItem;
            if (!Arrays.asList(modularTool.getValidHandles()).contains(handle.getItem())) return ItemStack.EMPTY;
        } else throw new IllegalArgumentException("Result item not instance of IModularTool.");
        Tool tool = ToolProvider.getTool(result);
        tool.getInventory().setStackInSlot(0, handle.copy());
        if (head.hasTag()) {
            CompoundNBT comp = head.getTag().getCompound("display");
            CompoundNBT n = result.hasTag() ? result.getTag() : new CompoundNBT();
            n.put("display", comp);
            result.setTag(n);
        }
        result.setDamage(head.getDamage());
        return result;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ENTITY_SHEEP_SHEAR;
    }

    @Override
    protected boolean isValidComponent(ItemStack stack) {
        return stack.getItem() instanceof ToolHandleItem;
    }

    @Override
    protected boolean isValidTool(ItemStack stack) {
        return stack.getItem() instanceof ToolHeadItem;
    }
}
