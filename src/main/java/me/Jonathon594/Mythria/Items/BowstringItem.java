package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.Container.BowstringContainer;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class BowstringItem extends MythriaItem {
    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.bowstring");

    public BowstringItem(String name) {
        super(name, new Properties().group(ItemGroup.TOOLS).maxStackSize(1));

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            for (int i = 0; i < 4; i++) {
                ClientManager.addTextureToStitch(new MythriaResourceLocation("items/bows/string/sinew_bowstring_" + i));
            }
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final PlayerEntity playerIn, final Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.openContainer(new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) -> new BowstringContainer(windowID, invPlayer), CONTAINER_NAME));
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
