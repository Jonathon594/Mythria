package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Container.SimpleLeatherContainer;
import me.Jonathon594.Mythria.Container.SimplePotteryContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class LeatherItem extends MythriaItem {
    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.simple_leather");

    public LeatherItem(String name, double weight, Properties properties) {
        super(name, weight, properties, true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.openContainer(new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) ->
                new SimpleLeatherContainer(windowID, invPlayer), CONTAINER_NAME));
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
