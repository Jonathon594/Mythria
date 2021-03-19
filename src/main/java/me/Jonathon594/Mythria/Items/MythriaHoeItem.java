package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Client.Renderer.Items.HoeItemRenderer;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MythriaHoeItem extends HoeItem implements IModularTool {
    private final Supplier<Item> toolHead;

    public MythriaHoeItem(String name, IItemTier tier, float speed, Supplier<Item> toolHead, boolean overrideVanilla) {
        super(tier, 0, speed, new Item.Properties().group(ItemGroup.TOOLS)
                .setISTER(() -> HoeItemRenderer::new));
        setRegistryName(overrideVanilla ? "minecraft" : Mythria.MODID, name);
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
