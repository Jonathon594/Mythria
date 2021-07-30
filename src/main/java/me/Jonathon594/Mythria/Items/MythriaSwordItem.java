package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Renderer.Items.SwordItemRenderer;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

import java.util.function.Supplier;

public class MythriaSwordItem extends SwordItem implements IModularTool {
    private final double weight;
    private final Supplier<Item> toolHead;

    public MythriaSwordItem(String name, IItemTier tier, double weight, Supplier<Item> toolHead, boolean overrideVanilla) {
        this(name, tier, weight, new Item.Properties().setISTER(() -> SwordItemRenderer::new), toolHead, overrideVanilla);
    }

    public MythriaSwordItem(String name, IItemTier tier, double weight, Properties properties, Supplier<Item> toolHead, boolean overrideVanilla) {
        super(tier, 3, -2.4f, properties.group(ItemGroup.COMBAT));
        setRegistryName(overrideVanilla ? "minecraft" : Mythria.MODID, name);
        this.weight = weight;
        this.toolHead = toolHead;
    }

    @Override
    public Item getDefaultHandle() {
        return MythriaItems.OAK_BLADE_HANDLE;
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }

    @Override
    public Item[] getValidHandles() {
        return MythriaUtil.getItemsFromTag(new MythriaResourceLocation("blade_handles"));
    }

//    @Nullable
//    @Override
//    public CompoundNBT getShareTag(ItemStack stack) {
//        CompoundNBT tag = stack.getOrCreateTag();
//        tag.put(Mythria.MODID + ".tool_sync", ToolProvider.getTool(stack).toNBT());
//        return tag;
//    }
//
//    @Override
//    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
//        if (nbt == null) return;
//        String key = Mythria.MODID + ".tool_sync";
//        if (nbt.contains(key)) {
//            ToolProvider.getTool(stack).fromNBT(nbt.getCompound(key));
//            nbt.remove(key);
//        }
//        stack.setTag(nbt);
//    }
}
