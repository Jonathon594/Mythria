package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Renderer.Items.PickaxeItemRenderer;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;

import java.util.function.Supplier;

public class MythriaPickaxeItem extends PickaxeItem implements IModularTool {
    private final double weight;
    private final Supplier<Item> toolHead;

    public MythriaPickaxeItem(String name, IItemTier tier, double weight, Supplier<Item> toolHead, boolean overrideVanilla) {
        super(tier, 1, -2.8f, new Item.Properties().group(ItemGroup.TOOLS)
                .setISTER(() -> PickaxeItemRenderer::new));
        setRegistryName(overrideVanilla ? "minecraft" : Mythria.MODID, name);
        this.weight = weight;
        this.toolHead = toolHead;
    }


    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }
}
