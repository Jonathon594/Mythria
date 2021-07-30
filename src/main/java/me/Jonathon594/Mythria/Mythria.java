package me.Jonathon594.Mythria;

import me.Jonathon594.Mythria.Capability.CapabilityHandler;
import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.Entity.MythriaEntityType;
import me.Jonathon594.Mythria.Entity.NetherChickenEntity;
import me.Jonathon594.Mythria.Listener.BiomeListener;
import me.Jonathon594.Mythria.Managers.Crafting.ConstructionManager;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.Network.MythriaSerializers;
import me.Jonathon594.Mythria.TileEntity.MythriaTileEntities;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.spongepowered.asm.launch.MixinBootstrap;

@Mod("mythria")
public class Mythria {
    public static final String MODID = "mythria";

    public Mythria() {
        MixinBootstrap.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onSetup);
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onServerSetup);
        MythriaTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
    }

    public void onServerSetup(FMLDedicatedServerSetupEvent event) {
        //ServerGlobalConfig.init();
        //DiscordManager.init();
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        ClientManager.clientSetup();
    }

    public void onSetup(FMLCommonSetupEvent event) {
        MythriaPacketHandler.register();
        CapabilityHandler.init();

        TimeManager.init();
        HealthManager.init();
        FoodManager.init();

        VanillaManager.init();
        ConstructionManager.init();
        SmeltingManager.init();

        MythriaSerializers.init();

        BiomeListener.addEntityReplacement(EntityType.STRIDER, MythriaEntityType.STRIDER);

        EntitySpawnPlacementRegistry.register(
                MythriaEntityType.NETHER_CHICKEN,
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                NetherChickenEntity::canNetherChickenSpawn);
    }
}
