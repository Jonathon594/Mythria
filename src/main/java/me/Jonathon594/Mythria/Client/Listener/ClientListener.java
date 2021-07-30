package me.Jonathon594.Mythria.Client.Listener;

import me.Jonathon594.Mythria.Capability.Crucible.Crucible;
import me.Jonathon594.Mythria.Capability.Crucible.CrucibleProvider;
import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Manager.ClientCombatManager;
import me.Jonathon594.Mythria.Client.Manager.InputManager;
import me.Jonathon594.Mythria.Items.CrucibleItem;
import me.Jonathon594.Mythria.Items.HeatableItem;
import me.Jonathon594.Mythria.Managers.LimitedInventoryManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketOpenInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientListener {
    @SubscribeEvent
    public static void onFog(EntityViewRenderEvent.FogDensity event) {
        if (event.getInfo().getFluidState().isTagged(FluidTags.LAVA)) {
            if (ProfileProvider.getProfile(Minecraft.getInstance().player).getGenetic().isImmune(DamageSource.LAVA))
                event.setDensity(0.05f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        if (item instanceof HeatableItem) {
            HeatableItem heatableItem = (HeatableItem) item;
            me.Jonathon594.Mythria.Capability.HeatableItem.HeatableItem heatable = HeatableProvider.getHeatable(itemStack);
            if (heatable != null) {
                if (item instanceof CrucibleItem) {
                    Crucible crucible = CrucibleProvider.getCrucible(itemStack);
                    event.getToolTip().addAll(((CrucibleItem) item).getToolTips(crucible, heatable));
                }

                event.getToolTip().addAll(heatableItem.getHeatToolTips(heatable.getTemperature()));
            }
        }
    }

    @SubscribeEvent
    public static void onOpenGui(final GuiOpenEvent event) {
        Screen gui = event.getGui();
        if (gui instanceof ContainerScreen) {
            ContainerScreen container = (ContainerScreen) gui;
            LimitedInventoryManager.onOpenContainer(Minecraft.getInstance().player, container.getContainer());
            if (event.getGui() instanceof InventoryScreen)
                MythriaPacketHandler.sendToServer(new CPacketOpenInventory());
        }
    }

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        if (event.getOverlayType().equals(RenderBlockOverlayEvent.OverlayType.FIRE) &&
                ProfileProvider.getProfile(Minecraft.getInstance().player).getGenetic().isImmune(DamageSource.ON_FIRE))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onClickMouse(InputEvent.ClickInputEvent event) {
        InputManager.onClickMouse(event);
    }
}
