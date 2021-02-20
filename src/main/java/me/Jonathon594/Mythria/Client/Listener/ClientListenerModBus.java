package me.Jonathon594.Mythria.Client.Listener;

import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.Client.Model.Loader.BowModelLoader;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientListenerModBus {
    @SubscribeEvent
    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            for (MythriaMaterial material : new MythriaMaterial[]{MythriaMaterial.TIN, MythriaMaterial.COPPER, MythriaMaterial.BRONZE,
                    MythriaMaterial.IRON, MythriaMaterial.STEEL, MythriaMaterial.TITANIUM, MythriaMaterial.TUNGSTEN}) {
                event.addSprite(new MythriaResourceLocation("entity/" + material.name().toLowerCase() + "_shield_base_nopattern"));
                event.addSprite(new MythriaResourceLocation("entity/" + material.name().toLowerCase() + "_shield_base"));
            }

            for (ResourceLocation location : ClientManager.getTexturesToStitch()) {
                event.addSprite(location);
            }
        }
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
//        for (ResourceLocation resourceLocation : ClientManager.getSpecialModels()) {
//            ModelLoader.addSpecialModel(resourceLocation);
//        }

        ModelLoaderRegistry.registerLoader(new MythriaResourceLocation("bow"), BowModelLoader.Loader.INSTANCE);
    }
}
