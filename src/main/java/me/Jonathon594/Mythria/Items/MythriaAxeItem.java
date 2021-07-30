package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Renderer.Items.AxeItemRenderer;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.function.Supplier;

public class MythriaAxeItem extends AxeItem implements IModularTool {
    private final Supplier<Item> toolHead;

    public MythriaAxeItem(String name, IItemTier tier, float speed, float damage, Supplier<Item> toolHead, boolean overrideVanilla) {
        super(tier, damage, speed, new Item.Properties().group(ItemGroup.TOOLS).setISTER(() -> AxeItemRenderer::new));
        setRegistryName(overrideVanilla ? "minecraft" : Mythria.MODID, name);
        this.toolHead = toolHead;
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }
}
