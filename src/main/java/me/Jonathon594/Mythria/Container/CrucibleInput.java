package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Items.OreItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrucibleInput extends SlotItemHandler {
    public CrucibleInput(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof OreItem;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        return true;
    }
}
