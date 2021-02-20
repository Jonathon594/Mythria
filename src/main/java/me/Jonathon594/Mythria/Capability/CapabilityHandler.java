package me.Jonathon594.Mythria.Capability;

import me.Jonathon594.Mythria.Capability.Bow.Bow;
import me.Jonathon594.Mythria.Capability.Bow.BowStorage;
import me.Jonathon594.Mythria.Capability.Bow.IBow;
import me.Jonathon594.Mythria.Capability.Crucible.Crucible;
import me.Jonathon594.Mythria.Capability.Crucible.CrucibleStorage;
import me.Jonathon594.Mythria.Capability.Crucible.ICrucible;
import me.Jonathon594.Mythria.Capability.Food.Food;
import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Capability.Food.FoodStorage;
import me.Jonathon594.Mythria.Capability.Food.IFood;
import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableItem;
import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableProvider;
import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableStorage;
import me.Jonathon594.Mythria.Capability.HeatableItem.IHeatable;
import me.Jonathon594.Mythria.Capability.Mold.IMold;
import me.Jonathon594.Mythria.Capability.Mold.Mold;
import me.Jonathon594.Mythria.Capability.Mold.MoldStorage;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.IMythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerDataStorage;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileStorage;
import me.Jonathon594.Mythria.Capability.Tool.ITool;
import me.Jonathon594.Mythria.Capability.Tool.Tool;
import me.Jonathon594.Mythria.Capability.Tool.ToolStorage;
import me.Jonathon594.Mythria.Entity.NPCEntity;
import me.Jonathon594.Mythria.Interface.IHeatableItem;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    public static final ResourceLocation PROFILE_CAP = new MythriaResourceLocation("profile_capability");
    public static final ResourceLocation FOOD_CAP = new MythriaResourceLocation("food_capability");
    public static final ResourceLocation CRUCIBLE_CAP = new MythriaResourceLocation("crucible_capability");
    public static final ResourceLocation METAL_CAP = new MythriaResourceLocation("metal_capability");
    public static final ResourceLocation POTTERY_CAP = new MythriaResourceLocation("pottery_capability");
    public static final ResourceLocation GLASS_CAP = new MythriaResourceLocation("glass_capability");
    public static final ResourceLocation PLAYER_CAP = new MythriaResourceLocation("synced_data_capability");
    public static final ResourceLocation FARM_ANIMAL_CAP = new MythriaResourceLocation("farm_animal_capability");
    public static final ResourceLocation HEATABLE_CAP = new MythriaResourceLocation("heatable_item_capability");
    public static final ResourceLocation TOOL_CAP = new MythriaResourceLocation("tool_capability");
    public static final ResourceLocation BOW_CAP = new MythriaResourceLocation("bow_capability");

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void attachCapabilityEntity(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(PROFILE_CAP, new ProfileProvider((PlayerEntity) event.getObject()));
            event.addCapability(PLAYER_CAP, new MythriaPlayerProvider());
        }
        if (event.getObject() instanceof NPCEntity) {
            event.addCapability(PLAYER_CAP, new MythriaPlayerProvider());
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void attachCapabilityItem(final AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack itemStack = event.getObject();
        if (itemStack.isFood()) {
            event.addCapability(FOOD_CAP, new FoodProvider());
        }

        if (itemStack.getItem() instanceof IHeatableItem) {
            event.addCapability(HEATABLE_CAP, new HeatableProvider());
        }
    }

    public static void init() {
        CapabilityManager.INSTANCE.register(IProfile.class, new ProfileStorage(), () -> new Profile());
        CapabilityManager.INSTANCE.register(IMythriaPlayer.class, new MythriaPlayerDataStorage(), () -> new MythriaPlayer());
        CapabilityManager.INSTANCE.register(IFood.class, new FoodStorage(), () -> new Food());
        CapabilityManager.INSTANCE.register(IHeatable.class, new HeatableStorage(), () -> new HeatableItem());
        CapabilityManager.INSTANCE.register(ICrucible.class, new CrucibleStorage(), () -> new Crucible());
        CapabilityManager.INSTANCE.register(ITool.class, new ToolStorage(), () -> new Tool());
        CapabilityManager.INSTANCE.register(IMold.class, new MoldStorage(), () -> new Mold());
        CapabilityManager.INSTANCE.register(IBow.class, new BowStorage(), () -> new Bow());
    }
}
