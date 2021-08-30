package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Renderer.Items.HoeItemRenderer;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.function.Supplier;

public class MythriaHoeItem extends HoeItem implements IModularTool {
    private final Supplier<Item> toolHead;

    public MythriaHoeItem(String name, IItemTier tier, float speed, Supplier<Item> toolHead, boolean overrideVanilla) {
        super(tier, 0, speed, new Item.Properties().group(ItemGroup.TOOLS)
                .setISTER(() -> HoeItemRenderer::new));
        setRegistryName(overrideVanilla ? "minecraft" : Mythria.MODID, name);
        this.toolHead = toolHead;
    }


    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }
}
