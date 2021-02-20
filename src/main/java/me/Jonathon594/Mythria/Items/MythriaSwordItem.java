package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Client.Renderer.Items.SwordItemRenderer;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MythriaSwordItem extends SwordItem implements IItemData, IModularTool {
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
    public double getWeight() {
        return weight;
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }

    @Override
    public Item[] getValidHandles() {
        return MythriaUtil.getItemsFromTag(new MythriaResourceLocation("blade_handles"));
    }

    @Override
    public Item getDefaultHandle() {
        return MythriaItems.OAK_BLADE_HANDLE;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ToolProvider(this);
    }
}
