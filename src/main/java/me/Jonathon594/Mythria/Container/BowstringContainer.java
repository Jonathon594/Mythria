package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Capability.Bow.Bow;
import me.Jonathon594.Mythria.Capability.Bow.BowProvider;
import me.Jonathon594.Mythria.Items.BowstringItem;
import me.Jonathon594.Mythria.Items.MythriaBowItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class BowstringContainer extends ToolComponentContainer {

    public BowstringContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.BOWSTRING, windowID, playerInventory);
    }

    @Override
    public ItemStack getResultStack(ItemStack bow, ItemStack component) {
        if (!(bow.getItem() instanceof MythriaBowItem)) return ItemStack.EMPTY;
        if (component.isEmpty()) return ItemStack.EMPTY;
        Bow bowCap = BowProvider.getBow(bow);
        bowCap.setBowstring(component.copy());
        return bow;
    }

    @Override
    protected SoundEvent getCraftSound() {
        return SoundEvents.ENTITY_SHEEP_SHEAR;
    }

    @Override
    protected boolean isValidComponent(ItemStack stack) {
        return stack.getItem() instanceof BowstringItem;
    }

    @Override
    protected boolean isValidTool(ItemStack stack) {
        return stack.getItem() instanceof MythriaBowItem && BowProvider.getBow(stack).getBowstring().isEmpty();
    }
}
