package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Client.Renderer.Items.HatchetItemRenderer;
import me.Jonathon594.Mythria.DataTypes.MythriaToolType;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class MythriaHatchetItem extends ToolItem implements IModularTool {
    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.OAK_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LEAVES,
            Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.BAMBOO, Blocks.BAMBOO_SAPLING, Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK);
    private final double weight;
    private final Supplier<Item> toolHead;

    public MythriaHatchetItem(float damage, float speed, IItemTier tier, String name, double weight, Supplier<Item> toolHead) {
        super(damage, speed, tier, EFFECTIVE_ON, new Properties().group(ItemGroup.TOOLS)
                .addToolType(MythriaToolType.HATCHET, tier.getHarvestLevel())
                .setISTER(() -> HatchetItemRenderer::new));
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
        this.toolHead = toolHead;
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
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
        if(nbt == null) return;
        String key = Mythria.MODID + ".tool_sync";
        if(nbt.contains(key)) {
            ToolProvider.getTool(stack).fromNBT(nbt.getCompound(key));
            nbt.remove(key);
        }
        stack.setTag(nbt);
    }
}
