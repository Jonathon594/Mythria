package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Client.Renderer.Items.HatchetItemRenderer;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class MythriaHatchetItem extends ToolItem implements IItemData, IModularTool {
    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.OAK_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LEAVES,
            Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.BAMBOO, Blocks.BAMBOO_SAPLING, Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK);
    private final double weight;
    private final Supplier<Item> toolHead;

    public MythriaHatchetItem(float damage, float speed, IItemTier tier, String name, double weight, Supplier<Item> toolHead) {
        super(damage, speed, tier, EFFECTIVE_ON, new Properties().group(ItemGroup.TOOLS)
                .setISTER(() -> HatchetItemRenderer::new));
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
        this.toolHead = toolHead;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (getToolTypes(stack).stream().anyMatch(e -> state.isToolEffective(e))) return efficiency;
        return this.EFFECTIVE_ON.contains(state.getBlock()) ? this.efficiency : 1.0F;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public Item getToolHeadItem() {
        return toolHead.get();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ToolProvider(this);
    }
}
