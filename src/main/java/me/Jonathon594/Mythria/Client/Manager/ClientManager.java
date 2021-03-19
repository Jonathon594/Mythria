package me.Jonathon594.Mythria.Client.Manager;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Blocks.MythriaOre;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Keybindings;
import me.Jonathon594.Mythria.Client.Model.Loader.MythriaPropertyGetter;
import me.Jonathon594.Mythria.Client.Screen.*;
import me.Jonathon594.Mythria.Container.MythriaContainerType;
import me.Jonathon594.Mythria.Entity.MythriaEntityType;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Managers.IngameGuiManager;
import me.Jonathon594.Mythria.TileEntity.MythriaTileEntities;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ClientManager {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final Collection<ResourceLocation> texturesToStitch = new ArrayList<>();
    private static final ArrayList<ResourceLocation> specialModels = new ArrayList<>();
    private static final HashMap<Item, Collection<MythriaPropertyGetter>> propertyOverrideMap = new HashMap<>();
    private static final HashMap<Ability, KeyBinding> keyAbilityTriggers = new HashMap<>();

    public static void addSpecialModel(ResourceLocation resourceLocation) {
        if (specialModels.contains(resourceLocation)) throw new IllegalArgumentException();
        specialModels.add(resourceLocation);
    }

    public static void addTextureToStitch(ResourceLocation location) {
        if (!texturesToStitch.contains(location)) texturesToStitch.add(location);
    }

    public static void clientSetup() {
        Keybindings.init();
        MythriaEntityType.registerRendersClient();
        MythriaTileEntities.registerRendersClient();
        IngameGuiManager.addOverlay(new ScreenHud(Minecraft.getInstance()));

        ScreenManager.registerFactory(MythriaContainerType.SAWHORSE, SawhorseScreen::new);
        ScreenManager.registerFactory(MythriaContainerType.WOOD_CARVING, WoodCarvingScreen::new);
        ScreenManager.registerFactory(MythriaContainerType.TOOL_HANDLE, ToolHandleScreen::new);
        ScreenManager.registerFactory(MythriaContainerType.SIMPLE_CRAFTING, SimpleCraftingScreen::new);
        ScreenManager.registerFactory(MythriaContainerType.CRUCIBLE, CrucibleScreen::new);
        ScreenManager.registerFactory(MythriaContainerType.CRUCIBLE_FULL, CrucibleFullScreen::new);
        ScreenManager.registerFactory(MythriaContainerType.BOWSTRING, BowstringScreen::new);

        RenderTypeLookup.setRenderLayer(MythriaBlocks.CAMPFIRE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MythriaBlocks.PIT_FURNACE, RenderType.getCutout());

        for (Block block : MythriaUtil.getAllBlocksOfType(MythriaOre.class)) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }

        for (Map.Entry<Item, Collection<MythriaPropertyGetter>> e : propertyOverrideMap.entrySet()) {
            for (MythriaPropertyGetter getter : e.getValue()) {
                ItemModelsProperties.registerProperty(e.getKey(), getter.getName(), getter);
            }
        }
    }

    public static ArrayList<ResourceLocation> getSpecialModels() {
        return specialModels;
    }

    public static Collection<ResourceLocation> getTexturesToStitch() {
        return texturesToStitch;
    }

    public static void onPlayerTickClient() {
        ClientPlayerEntity player = minecraft.player;
        Profile profile = ProfileProvider.getProfile(player);
        GameSettings gameSettings = minecraft.gameSettings;
        double stamina = profile.getConsumable(Consumable.STAMINA);
        if (!player.isCreative()) {
            if (stamina <= 0) {
                if (player.isSprinting()) {
                    player.setSprinting(false);
                }
                gameSettings.keyBindSprint.setPressed(false);
                gameSettings.keyBindJump.setPressed(false);
                ObfuscationReflectionHelper.setPrivateValue(ClientPlayerEntity.class, minecraft.player, 0, "field_189812_cs");
            }

            if (player.isActualySwimming() || (player.isInWater() && !player.isOnGround())) {
                if (stamina <= 0) {
                    player.setSwimming(false);
                    gameSettings.keyBindForward.setPressed(false);
                    gameSettings.keyBindBack.setPressed(false);
                    gameSettings.keyBindLeft.setPressed(false);
                    gameSettings.keyBindRight.setPressed(false);
                }
            }
        }
    }

    public static void registerOverride(Item item, MythriaPropertyGetter property) {
        if (!propertyOverrideMap.containsKey(item)) propertyOverrideMap.put(item, new ArrayList<>());
        Collection<MythriaPropertyGetter> list = propertyOverrideMap.get(item);
        if (!list.contains(property)) list.add(property);
    }
}
