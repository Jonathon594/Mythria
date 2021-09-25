package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Renderer.Items.ChiselItemRenderer;
import me.Jonathon594.Mythria.Container.StoneCarvingContainer;
import me.Jonathon594.Mythria.DataTypes.MythriaToolType;
import me.Jonathon594.Mythria.Interface.IModularTool;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Collections;
import java.util.function.Supplier;

public class MythriaChiselItem extends ToolItem implements IModularTool {
    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.chisel");
    private final double weight;
    private final Supplier<Item> toolHead;

    public MythriaChiselItem(float damage, float speed, IItemTier tier, String name, double weight, Supplier<Item> toolHead) {
        super(damage, speed, tier, Collections.EMPTY_SET, new Item.Properties().group(ItemGroup.TOOLS)
                .addToolType(MythriaToolType.CHISEL, tier.getHarvestLevel())
                .setISTER(() -> ChiselItemRenderer::new));
        setRegistryName(Mythria.MODID, name);
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
        if (!worldIn.isRemote)
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) ->
                    new StoneCarvingContainer(windowID, invPlayer), CONTAINER_NAME));
        return new ActionResult<>(ActionResultType.SUCCESS, chisel);
    }
}
