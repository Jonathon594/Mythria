package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Recipe.SimpleLeatherRecipe;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Mythria.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MythriaContainerType {
    public static final ContainerType<SawhorseContainer> SAWHORSE = null;
    public static final ContainerType<CuttingStoneContainer> WOOD_CARVING = null;
    public static final ContainerType<ToolHandleContainer> TOOL_HANDLE = null;
    public static final ContainerType<SimplePotteryContainer> SIMPLE_POTTERY = null;
    public static final ContainerType<CrucibleContainer> CRUCIBLE = null;
    public static final ContainerType<CrucibleContainerFull> CRUCIBLE_FULL = null;
    public static final ContainerType<BowstringContainer> BOWSTRING = null;
    public static final ContainerType<SimpleLeatherContainer> SIMPLE_LEATHER = null;

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(new ContainerType<CrafterContainer>(SawhorseContainer::new).setRegistryName(Mythria.MODID, "sawhorse"));
        event.getRegistry().register(new ContainerType<CrafterContainer>(CuttingStoneContainer::new).setRegistryName(Mythria.MODID, "wood_carving"));
        event.getRegistry().register(new ContainerType<>(ToolHandleContainer::new).setRegistryName(Mythria.MODID, "tool_handle"));
        event.getRegistry().register(new ContainerType<CrafterContainer>(SimplePotteryContainer::new).setRegistryName(Mythria.MODID, "simple_pottery"));
        event.getRegistry().register(new ContainerType<>(CrucibleContainer::new).setRegistryName(Mythria.MODID, "crucible"));
        event.getRegistry().register(new ContainerType<>(CrucibleContainerFull::new).setRegistryName(Mythria.MODID, "crucible_full"));
        event.getRegistry().register(new ContainerType<>(BowstringContainer::new).setRegistryName(Mythria.MODID, "bowstring"));
        event.getRegistry().register(new ContainerType<>(SimpleLeatherContainer::new).setRegistryName(Mythria.MODID, "simple_leather"));
    }
}
