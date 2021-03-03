package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Entity.MythriaEntityType;
import me.Jonathon594.Mythria.Entity.MythriaStriderEntity;
import me.Jonathon594.Mythria.Entity.NetherChickenEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityListenerModBus {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(MythriaEntityType.NPC, LivingEntity.registerAttributes().create());
        event.put(MythriaEntityType.NETHER_CHICKEN, NetherChickenEntity.registerAttributes().create());
        event.put(MythriaEntityType.STRIDER, MythriaStriderEntity.func_234317_eK_().create());
    }
}
