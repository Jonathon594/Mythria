package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Container.WoodCarvingContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

public class CuttingStone extends ToolHeadItem {
    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.cutting_stone");

    protected CuttingStone(String name, IItemTier tier, final Supplier<Item> result) {
        super(name, result, new Properties().maxDamage(tier.getMaxUses()));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final PlayerEntity playerIn, final Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote)
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) ->
                    new WoodCarvingContainer(windowID, invPlayer), CONTAINER_NAME));

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
