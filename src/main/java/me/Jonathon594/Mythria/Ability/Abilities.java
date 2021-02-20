package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Mythria;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class Abilities {
    public static final Ability ELF_HEALING = null;
    public static final Ability FAE_FLIGHT = null;
    public static final Ability DRYAD_GROWTH = null;

    @SubscribeEvent
    public static void onRegisterAbilities(RegistryEvent.Register<Ability> event) {
        event.getRegistry().registerAll(
                new PassiveHealingAbility("elf_healing", 10, Consumable.MANA, 1),
                new PassiveFaeFlightAbility("fae_flight"),
                new PassiveDryadGrowthAbility("dryad_growth")
        );
    }
}
