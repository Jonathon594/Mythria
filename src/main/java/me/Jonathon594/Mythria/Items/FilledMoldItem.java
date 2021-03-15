package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableProvider;
import me.Jonathon594.Mythria.Capability.Mold.Mold;
import me.Jonathon594.Mythria.Capability.Mold.MoldProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class FilledMoldItem extends HeatableItem {
    public FilledMoldItem(String name) {
        super(name, new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Mold mold = MoldProvider.getMold(stack);
        ItemStack result = mold.getResultStack();
        ItemStack originalMoldStack = mold.getOriginalMoldStack();
        if (HeatableProvider.getHeatable(stack).getTemperature() >= 100) return ActionResult.resultPass(stack);
        if (!playerIn.addItemStackToInventory(originalMoldStack.copy())) {
            playerIn.dropItem(originalMoldStack.copy(), false);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, result.copy());
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return ActionResultType.SUCCESS;
    }
}
