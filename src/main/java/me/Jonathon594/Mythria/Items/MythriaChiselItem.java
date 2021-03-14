package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.Capability.Tool.ToolProvider;
import me.Jonathon594.Mythria.Client.Renderer.Items.ChiselItemRenderer;
import me.Jonathon594.Mythria.DataTypes.MythriaToolType;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class MythriaChiselItem extends ToolItem implements IModularTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.STONE);
    private final double weight;
    private final Supplier<Item> toolHead;

    public MythriaChiselItem(float damage, float speed, IItemTier tier, String name, double weight, Supplier<Item> toolHead) {
        super(damage, speed, tier, EFFECTIVE_ON, new Item.Properties().group(ItemGroup.TOOLS)
                .addToolType(MythriaToolType.CHISEL, tier.getHarvestLevel())
                .setISTER(() -> ChiselItemRenderer::new));
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
        this.toolHead = toolHead;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.getMaterial().equals(Material.ROCK)) {
            return this.efficiency;
        }
        return 0f;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack chisel = playerIn.getHeldItem(handIn);
        if (playerIn.world.isRemote) return new ActionResult<>(ActionResultType.PASS, chisel);
        //todo playerIn.openGui(Mythria.instance, MythriaGui.CHISEL_GUI.ordinal(), worldIn, 0, 0, 0);
        return new ActionResult<>(ActionResultType.SUCCESS, chisel);
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

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return ActionResultType.SUCCESS;
    }
}
