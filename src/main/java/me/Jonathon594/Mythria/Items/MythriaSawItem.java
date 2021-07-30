package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.Client.Renderer.Items.SawItemRenderer;
import me.Jonathon594.Mythria.DataTypes.MythriaToolType;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;

import java.util.Set;
import java.util.function.Supplier;

public class MythriaSawItem extends ToolItem implements IModularTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();
    private final Supplier<Item> toolHead;

    public MythriaSawItem(String name, float damage, float speed, IItemTier tier, Supplier<Item> toolHead) {
        super(damage, speed, tier, EFFECTIVE_ON, new Item.Properties().group(ItemGroup.TOOLS)
                .addToolType(MythriaToolType.SAW, tier.getHarvestLevel())
                .setISTER(() -> SawItemRenderer::new));
        setRegistryName(Mythria.MODID, name);
        this.toolHead = toolHead;
    }

    @Override
    public Item getDefaultHandle() {
        return MythriaItems.OAK_SAW_HANDLE;
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }

    @Override
    public Item[] getValidHandles() {
        return MythriaUtil.getItemsFromTag(new MythriaResourceLocation("saw_handles"));
    }
}
