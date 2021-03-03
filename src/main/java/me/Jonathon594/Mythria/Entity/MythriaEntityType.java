package me.Jonathon594.Mythria.Entity;

import me.Jonathon594.Mythria.Client.Renderer.MythriaArrorRenderer;
import me.Jonathon594.Mythria.Client.Renderer.NPCRenderer;
import me.Jonathon594.Mythria.Client.Renderer.NetherChickenRenderer;
import me.Jonathon594.Mythria.Client.Renderer.SpearRenderer;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class MythriaEntityType {
    public static final EntityType<NPCEntity> NPC = null;
    public static final EntityType<SpearEntity> SPEAR = null;
    public static final EntityType<MythriaArrowEntity> ARROW = null;
    public static final EntityType<NetherChickenEntity> NETHER_CHICKEN = null;
    public static final EntityType<MythriaStriderEntity> STRIDER = null;

    public static void registerRendersClient() {
        RenderingRegistry.registerEntityRenderingHandler(NPC, NPCRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SPEAR, SpearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ARROW, MythriaArrorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NETHER_CHICKEN, NetherChickenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(STRIDER, StriderRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                buildEntityType(EntityType.Builder.<NPCEntity>create(NPCEntity::new, EntityClassification.CREATURE)
                        .size(0.6F, 1.8F), Mythria.MODID + ":npc"),
                buildEntityType(EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC)
                        .size(0.5F, 0.5F), Mythria.MODID + ":spear"),
                buildEntityType(EntityType.Builder.<MythriaArrowEntity>create(MythriaArrowEntity::new, EntityClassification.MISC)
                        .size(0.5F, 0.5F), Mythria.MODID + ":arrow"),
                buildEntityType(EntityType.Builder.create(NetherChickenEntity::new, EntityClassification.CREATURE)
                        .size(0.5F, 0.5F), Mythria.MODID + ":nether_chicken"),
                buildEntityType(EntityType.Builder.create(MythriaStriderEntity::new, EntityClassification.CREATURE)
                        .immuneToFire().size(0.9F, 1.7F).trackingRange(10), Mythria.MODID + ":strider")
        );
    }

    protected static EntityType<?> buildEntityType(EntityType.Builder<?> builder, String id) {
        EntityType<?> entityType = builder.build(id);
        entityType.setRegistryName(new ResourceLocation(id));
        return entityType;
    }
}
