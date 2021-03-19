package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.item.Item;

public interface IModularTool {
    default Item getDefaultHandle() {
        return MythriaItems.OAK_TOOL_HANDLE;
    }

    Item getToolHeadItem();

    default Item[] getValidHandles() {
        return MythriaUtil.getItemsFromTag(new MythriaResourceLocation("tool_handles"));
    }
}
