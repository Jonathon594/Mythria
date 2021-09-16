package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Mythria.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MythriaContainerType {
    public static final ContainerType<SawhorseContainer> SAWHORSE = null;
    public static final ContainerType<WoodCarvingContainer> WOOD_CARVING = null;
    public static final ContainerType<StoneCarvingContainer> STONE_CARVING = null;
    public static final ContainerType<ToolHandleContainer> TOOL_HANDLE = null;
    public static final ContainerType<SimpleCraftingContainer> SIMPLE_CRAFTING = null;
    public static final ContainerType<CrucibleContainer> CRUCIBLE = null;
    public static final ContainerType<CrucibleContainerFull> CRUCIBLE_FULL = null;
    public static final ContainerType<BowstringContainer> BOWSTRING = null;

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(new ContainerType<CrafterContainer>(SawhorseContainer::new).setRegistryName(Mythria.MODID, "sawhorse"));
        event.getRegistry().register(new ContainerType<CrafterContainer>(WoodCarvingContainer::new).setRegistryName(Mythria.MODID, "wood_carving"));
        event.getRegistry().register(new ContainerType<CrafterContainer>(StoneCarvingContainer::new).setRegistryName(Mythria.MODID, "stone_carving"));
        event.getRegistry().register(new ContainerType<>(ToolHandleContainer::new).setRegistryName(Mythria.MODID, "tool_handle"));
        event.getRegistry().register(new ContainerType<CrafterContainer>(SimpleCraftingContainer::new).setRegistryName(Mythria.MODID, "simple_crafting"));
        event.getRegistry().register(new ContainerType<>(CrucibleContainer::new).setRegistryName(Mythria.MODID, "crucible"));
        event.getRegistry().register(new ContainerType<>(CrucibleContainerFull::new).setRegistryName(Mythria.MODID, "crucible_full"));
        event.getRegistry().register(new ContainerType<>(BowstringContainer::new).setRegistryName(Mythria.MODID, "bowstring"));
    }
}
