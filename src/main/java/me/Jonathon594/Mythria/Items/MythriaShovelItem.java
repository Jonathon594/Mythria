package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Renderer.Items.ShovelItemRenderer;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;

import java.util.function.Supplier;

public class MythriaShovelItem extends ShovelItem implements IModularTool {
    private final Supplier<Item> toolHead;

    public MythriaShovelItem(String name, IItemTier tier, Supplier<Item> toolHead, boolean overrideVanilla) {
        super(tier, 1.5f, -3.0f, new Item.Properties().group(ItemGroup.TOOLS)
                .setISTER(() -> ShovelItemRenderer::new));
        setRegistryName(overrideVanilla ? "minecraft" : Mythria.MODID, name);
        this.toolHead = toolHead;
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }
}
