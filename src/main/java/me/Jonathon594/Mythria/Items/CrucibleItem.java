package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Crucible.Crucible;
import me.Jonathon594.Mythria.Capability.Crucible.CrucibleProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.Container.CrucibleContainer;
import me.Jonathon594.Mythria.Container.CrucibleContainerFull;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CrucibleItem extends HeatableItem {
    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.crucible");

    public CrucibleItem(String name) {
        super(name, new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Crucible crucible = CrucibleProvider.getCrucible(stack);
        if (crucible == null) return ActionResult.resultPass(stack);
        if (!ProfileProvider.getProfile(playerIn).hasFlag(AttributeFlag.BASIC_SMELTING)) {
            return ActionResult.resultPass(stack);
        }

        if (crucible.hasMeltingContents()) {
            playerIn.openContainer(new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) ->
                    new CrucibleContainerFull(windowID, invPlayer), CONTAINER_NAME));
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        } else {
            playerIn.openContainer(new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) ->
                    new CrucibleContainer(windowID, invPlayer), CONTAINER_NAME));
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return ActionResultType.SUCCESS;
    }

    public Collection<? extends ITextComponent> getToolTips(Crucible crucible, me.Jonathon594.Mythria.Capability.HeatableItem.HeatableItem heatable) {
        List<ITextComponent> lines = new ArrayList<>();
        if (crucible.hasMeltingContents()) {
            double temperature = heatable.getTemperature();
            double meltingPoint = SmeltingManager.getMetalRecipe(crucible.getMaterial()).getMeltingPoint();
            double prop = Math.min(temperature / meltingPoint, 1);
            SmeltingManager.Temperature[] temperatures = SmeltingManager.Temperature.values();
            int index = (int) Math.floor(prop * (temperatures.length - 1));
            SmeltingManager.Temperature temperatureString = temperatures[index];

            lines.add(new StringTextComponent(ColorConst.MAIN_COLOR + temperatureString.getDisplay() + " " +
                    ColorConst.CONT_COLOR + MythriaUtil.capitalize(crucible.getMaterial().name())));
        }
        return lines;
    }
}
