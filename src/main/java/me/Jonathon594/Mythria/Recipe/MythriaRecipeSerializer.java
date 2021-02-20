package me.Jonathon594.Mythria.Recipe;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Mythria.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MythriaRecipeSerializer {
    public static final IRecipeSerializer<CarpentryRecipe> CARPENTRY = null;
    public static final IRecipeSerializer<WoodCarvingRecipe> WOOD_CARVING = null;
    public static final IRecipeSerializer<SimplePotteryRecipe> SIMPLE_POTTERY = null;
    public static final IRecipeSerializer<SimpleLeatherRecipe> SIMPLE_LEATHER = null;

    @SubscribeEvent
    public static void registerRecipes(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll(
                new CrafterRecipe.Serializer<>((resourceLocation, group, ingredient, cost, itemStack, tier) ->
                        new CarpentryRecipe(resourceLocation, group, ingredient, cost, itemStack, tier)).setRegistryName(Mythria.MODID, "carpentry"),
                new CrafterRecipe.Serializer<>((resourceLocation1, group1, ingredient1, cost, itemStack1, tier) ->
                        new WoodCarvingRecipe(resourceLocation1, group1, ingredient1, cost, itemStack1, tier)).setRegistryName(Mythria.MODID, "wood_carving"),
                new CrafterRecipe.Serializer<>((resourceLocation1, group1, ingredient1, cost, itemStack1, tier) ->
                        new SimplePotteryRecipe(resourceLocation1, group1, ingredient1, cost, itemStack1, tier)).setRegistryName(Mythria.MODID, "simple_pottery"),
                new CrafterRecipe.Serializer<>((resourceLocation1, group1, ingredient1, cost, itemStack1, tier) ->
                        new SimpleLeatherRecipe(resourceLocation1, group1, ingredient1, cost, itemStack1, tier)).setRegistryName(Mythria.MODID, "simple_leather")
        );
    }
}