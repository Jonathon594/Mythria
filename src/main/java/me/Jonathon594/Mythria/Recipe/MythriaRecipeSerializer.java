package me.Jonathon594.Mythria.Recipe;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Mythria.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MythriaRecipeSerializer {
    public static final IRecipeSerializer<CarpentryRecipe> CARPENTRY = null;
    public static final IRecipeSerializer<WoodCarvingRecipe> WOOD_CARVING = null;
    public static final IRecipeSerializer<SimpleCraftingRecipe> SIMPLE_CRAFTING = null;
    public static final IRecipeSerializer<StoneCarvingRecipe> STONE_CARVING = null;
    @SubscribeEvent
    public static void registerRecipes(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll(
                new CrafterRecipe.Serializer<>(CarpentryRecipe::new).setRegistryName(Mythria.MODID, "carpentry"),
                new CrafterRecipe.Serializer<>(WoodCarvingRecipe::new).setRegistryName(Mythria.MODID, "wood_carving"),
                new CrafterRecipe.Serializer<>(SimpleCraftingRecipe::new).setRegistryName(Mythria.MODID, "simple_crafting"),
                new CrafterRecipe.Serializer<>(StoneCarvingRecipe::new).setRegistryName(Mythria.MODID, "stone_carving")
        );
    }
}