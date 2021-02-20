package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Container.ToolHandleContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ToolHandleItem extends MythriaItem {
    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.tool_handle");

    protected ToolHandleItem(String name, double weight) {
        super(name, weight, new Properties().maxStackSize(4).group(ItemGroup.MATERIALS));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final PlayerEntity playerIn, final Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.openContainer(new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) -> new ToolHandleContainer(windowID, invPlayer), CONTAINER_NAME));
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
