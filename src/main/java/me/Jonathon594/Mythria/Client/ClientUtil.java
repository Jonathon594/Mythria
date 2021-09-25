package me.Jonathon594.Mythria.Client;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.Client.Model.Loader.MythriaPropertyGetter;
import me.Jonathon594.Mythria.Client.Screen.LockedSlotWidget;
import me.Jonathon594.Mythria.Client.Screen.ScreenPerks;
import me.Jonathon594.Mythria.Items.MythriaBowItem;
import me.Jonathon594.Mythria.Items.MythriaShieldItem;
import me.Jonathon594.Mythria.Items.SpearItem;
import me.Jonathon594.Mythria.Managers.LimitedInventoryManager;
import me.Jonathon594.Mythria.Packet.SPacketProfileCache;
import me.Jonathon594.Mythria.Packet.SPacketUpdateExperience;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;

import javax.annotation.Nullable;

public class ClientUtil {
    private static final Minecraft instance = Minecraft.getInstance();

    public static void drawLockedInventorySlots(final GuiScreenEvent.InitGuiEvent.Post event) {
        final Minecraft mc = Minecraft.getInstance();
        if (event.getGui() instanceof ContainerScreen) {
            final Screen screen = event.getGui();
            final ContainerScreen containerScreen = (ContainerScreen) screen;
            if (mc.player != null) {
                final PlayerEntity player = mc.player;
                if (!player.isCreative() && !player.isSpectator() && !LimitedInventoryManager.isAtMaxSlots(player))
                    event.addWidget(new LockedSlotWidget(containerScreen));
            }
        }
    }

    public static Profile getClientProfile() {
        return ProfileProvider.getProfile(instance.player);
    }

    public static void handleUpdateExperience(SPacketUpdateExperience msg) {
        final Profile profile = ProfileProvider.getProfile(Minecraft.getInstance().player);
        profile.getSkillLevels().put(msg.getSkill(), msg.getValue());
        profile.calculateProgressTowardPlayerLevel();
    }

    public static void handleUpdateProfileCache(SPacketProfileCache msg) {
        final Profile p = ProfileProvider.getProfile(Minecraft.getInstance().player);
        p.fromNBT(msg.getNbt());
        updadeGuiScreens();
    }

    public static boolean hasSaerkiTail(MythriaPlayer mythriaPlayer) {
        return mythriaPlayer.getSkinPart(SkinPart.Type.SAERKI_TAIL) != null;
    }

    public static void registerBowProperty(MythriaBowItem bowItem) {
        ClientManager.registerOverride(bowItem, new MythriaPropertyGetter(new MythriaResourceLocation("pull")) {
            @Override
            public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
                return entity.getActiveItemStack() == stack ? (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0f : 0.0f;
            }
        });
    }

    public static void registerShieldProperty(MythriaShieldItem mythriaShieldItem) {
        ClientManager.registerOverride(mythriaShieldItem, new MythriaPropertyGetter(new MythriaResourceLocation("blocking")) {
            @Override
            public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
                return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    public static void registerSpearProperty(SpearItem spearItem) {
        ClientManager.registerOverride(spearItem, new MythriaPropertyGetter(new MythriaResourceLocation("throwing")) {
            @Override
            public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
                return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0f : 0;
            }
        });
    }

    public static void updadeGuiScreens() {
        Minecraft mc = Minecraft.getInstance();
//        if (mc.currentScreen instanceof GuiAttribute) {
//            final GuiAttribute gui = (GuiAttribute) mc.currentScreen;
//            final Container c = gui.getInventory();
//            if (c instanceof ContainerAttribute) {
//                final ContainerAttribute pmc = (ContainerAttribute) c;
//                pmc.setContents();
//            }
//        }
        if (mc.currentScreen instanceof ScreenPerks) {
            ((ScreenPerks) mc.currentScreen).refreshTabs();
        }
    }
}
