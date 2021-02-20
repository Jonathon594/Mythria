package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Client.Renderer.Items.SawItemRenderer;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class MythriaSawItem extends ToolItem implements IItemData, IModularTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();
    private final double weight;
    private final Supplier<Item> toolHead;

    public MythriaSawItem(String name, float damage, float speed, IItemTier tier, double weight, Supplier<Item> toolHead) {
        super(damage, speed, tier, EFFECTIVE_ON, new Item.Properties().group(ItemGroup.TOOLS)
                .setISTER(() -> SawItemRenderer::new));
        setRegistryName(Mythria.MODID, name);
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
        return MythriaUtil.getItemsFromTag(new MythriaResourceLocation("saw_handles"));
    }

    @Override
    public Item getDefaultHandle() {
        return MythriaItems.OAK_SAW_HANDLE;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ToolProvider(this);
    }
}
