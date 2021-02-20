package me.Jonathon594.Mythria.Container;

import net.minecraft.entity.player.PlayerInventory;

public class CuttingStoneContainer extends CarvingContainer {
    public CuttingStoneContainer(int windowID, PlayerInventory playerInventory) {
        super(windowID, playerInventory);
    }

    @Override
    protected int getCraftingTier() {
        return 0;
    }
}
