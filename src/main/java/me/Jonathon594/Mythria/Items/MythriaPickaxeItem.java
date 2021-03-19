package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Client.Renderer.Items.PickaxeItemRenderer;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
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

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        tag.put(Mythria.MODID + ".tool_sync", ToolProvider.getTool(stack).toNBT());
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) return;
        String key = Mythria.MODID + ".tool_sync";
        if (nbt.contains(key)) {
            ToolProvider.getTool(stack).fromNBT(nbt.getCompound(key));
            nbt.remove(key);
        }
        stack.setTag(nbt);
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }
}
