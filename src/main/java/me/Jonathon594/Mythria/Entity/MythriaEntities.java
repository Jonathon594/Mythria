package me.Jonathon594.Mythria.Entity;

import me.Jonathon594.Mythria.Client.Renderer.MythriaArrorRenderer;
import me.Jonathon594.Mythria.Client.Renderer.NPCRenderer;
import me.Jonathon594.Mythria.Client.Renderer.NetherChickenRenderer;
import me.Jonathon594.Mythria.Client.Renderer.SpearRenderer;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MythriaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Mythria.MODID);

    public static final RegistryObject<EntityType<NPCEntity>> NPC =
            ENTITY_TYPES.register("npc", () -> EntityType.Builder.<NPCEntity>create((entityType, world) -> new NPCEntity(entityType, world), EntityClassification.CREATURE)
                    .size(0.6F, 1.8F).build(Mythria.MODID + ":npc"));

    public static final RegistryObject<EntityType<SpearEntity>> SPEAR =
            ENTITY_TYPES.register("spear", () -> EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F).build(Mythria.MODID + ":spear"));

    public static final RegistryObject<EntityType<MythriaArrowEntity>> ARROW =
            ENTITY_TYPES.register("arrow", () -> EntityType.Builder.<MythriaArrowEntity>create(MythriaArrowEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F).build(Mythria.MODID + ":arrow"));

    public static final RegistryObject<EntityType<NetherChickenEntity>> NETHER_CHICKEN =
            ENTITY_TYPES.register("nether_chicken", () -> EntityType.Builder.create(NetherChickenEntity::new, EntityClassification.CREATURE)
                    .size(0.5F, 0.5F).build(Mythria.MODID + ":nether_chicken"));

    public static void registerRendersClient() {
        RenderingRegistry.registerEntityRenderingHandler(NPC.get(), NPCRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SPEAR.get(), SpearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ARROW.get(), MythriaArrorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NETHER_CHICKEN.get(), NetherChickenRenderer::new);
    }

    public static void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(NPC.get(), LivingEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(NETHER_CHICKEN.get(), NetherChickenEntity.registerAttributes().create());
    }
}
