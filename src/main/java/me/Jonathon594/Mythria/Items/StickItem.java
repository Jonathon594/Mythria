package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Const.MythriaConst;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class StickItem extends AbstractFireStarterItem {
    public StickItem(String name) {
        super(name, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16));
    }

    @Override
    protected boolean doesInstantLight() {
        return false;
    }

    @Override
    protected String getBreakMessage() {
        return MythriaConst.FIREMAKING_STICK_BROKEN;
    }

    @Override
    protected double getFriction() {
        return 0.1;
    }

    @Override
    protected int getRequiredLevel() {
        return 0;
    }
}
