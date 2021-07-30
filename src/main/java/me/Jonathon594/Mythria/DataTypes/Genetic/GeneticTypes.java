package me.Jonathon594.Mythria.DataTypes.Genetic;

import me.Jonathon594.Mythria.DataTypes.Genetic.Types.*;
import me.Jonathon594.Mythria.Mythria;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class GeneticTypes {
    public static final GeneticType HUMAN = null;
    public static final GeneticType ELF = null;
    public static final GeneticType ORC = null;
    public static final GeneticType FAE = null;
    public static final GeneticType DRYAD = null;
    public static final GeneticType SKAEREN = null;
    public static final GeneticType KATANA = null;

    @SubscribeEvent
    public static void onRegisterGeneticBuilders(RegistryEvent.Register<GeneticType> event) {
        event.getRegistry().registerAll(
                new HumanType(),
                new ElfType(),
                new OrcType(),
                new FaeType(),
                new DryadType(),
                new SkaerenType(),
                new KatanaType()
        );
    }
}
