package me.Jonathon594.Mythria.SpawnGifts;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Tags.MythriaItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class SpawnGifts {
    public static final SpawnGift SAPLINGS = null;
    public static final SpawnGift BANDOLIER = null;
    public static final SpawnGift OLD_AXE = null;
    public static final SpawnGift OLD_PICKAXE = null;
    public static final SpawnGift OLD_HOE = null;
    public static final SpawnGift OLD_AXE_NETHER = null;
    public static final SpawnGift OLD_PICKAXE_NETHER = null;
    public static final SpawnGift OLD_SWORD = null;
    public static final SpawnGift OLD_SWORD_NETHER = null;

    @SubscribeEvent
    public static void onRegisterGifts(RegistryEvent.Register<SpawnGift> event) {
        event.getRegistry().registerAll(
                new RandomItemsSpawnGift("saplings", () -> BlockTags.SAPLINGS.getAllElements(), 3)
                        .withDisplayName("Saplings"),
                new SingleItemSpawnGift("bandolier", () -> MythriaItems.PRIMITIVE_BANDOLIER)
                        .withDisplayName("Bandolier"),
                new MythriaToolSpawnGift("old_axe", () -> ImmutableList.of(MythriaItems.TIN_AXE,
                        MythriaItems.COPPER_AXE, MythriaItems.BRONZE_AXE),
                        () -> MythriaItemTags.TOOL_HANDLES_OVERWORLD.getAllElements())
                        .withNameFactory(SpawnGifts::OldToolNameFactory)
                        .withDamageFactory(SpawnGifts::OldToolDamageFactory)
                        .withDisplayName("Old Axe").withPerkType(PerkType.LUMBERING),
                new MythriaToolSpawnGift("old_pickaxe", () -> ImmutableList.of(MythriaItems.TIN_PICKAXE,
                        MythriaItems.COPPER_PICKAXE, MythriaItems.BRONZE_PICKAXE),
                        () -> MythriaItemTags.TOOL_HANDLES_OVERWORLD.getAllElements())
                        .withNameFactory(SpawnGifts::OldToolNameFactory)
                        .withDamageFactory(SpawnGifts::OldToolDamageFactory)
                        .withDisplayName("Old Pickaxe").withPerkType(PerkType.MINING),
                new MythriaToolSpawnGift("old_hoe", () -> ImmutableList.of(MythriaItems.TIN_HOE,
                        MythriaItems.COPPER_HOE, MythriaItems.BRONZE_HOE),
                        () -> MythriaItemTags.TOOL_HANDLES_OVERWORLD.getAllElements())
                        .withNameFactory(SpawnGifts::OldToolNameFactory)
                        .withDamageFactory(SpawnGifts::OldToolDamageFactory)
                        .withDisplayName("Old Hoe").withPerkType(PerkType.FARMING),
                new MythriaToolSpawnGift("old_axe_nether", () -> ImmutableList.of(Items.GOLDEN_AXE),
                        () -> MythriaItemTags.TOOL_HANDLES_NETHER.getAllElements())
                        .withNameFactory(SpawnGifts::OldToolNameFactory)
                        .withDamageFactory(SpawnGifts::OldToolDamageFactory)
                        .withDisplayName("Old Axe").withPerkType(PerkType.LUMBERING),
                new MythriaToolSpawnGift("old_pickaxe_nether", () -> ImmutableList.of(Items.GOLDEN_PICKAXE),
                        () -> MythriaItemTags.TOOL_HANDLES_NETHER.getAllElements())
                        .withNameFactory(SpawnGifts::OldToolNameFactory)
                        .withDamageFactory(SpawnGifts::OldToolDamageFactory)
                        .withDisplayName("Old Pickaxe").withPerkType(PerkType.MINING),

                new MythriaToolSpawnGift("old_sword", () -> ImmutableList.of(MythriaItems.TIN_SWORD,
                        MythriaItems.COPPER_SWORD, MythriaItems.BRONZE_SWORD),
                        () -> MythriaItemTags.TOOL_HANDLES_OVERWORLD.getAllElements())
                        .withNameFactory(SpawnGifts::OldToolNameFactory)
                        .withDamageFactory(SpawnGifts::OldToolDamageFactory)
                        .withDisplayName("Old Sword"),
                new MythriaToolSpawnGift("old_sword_nether", () -> ImmutableList.of(Items.GOLDEN_SWORD),
                        () -> MythriaItemTags.TOOL_HANDLES_NETHER.getAllElements())
                        .withNameFactory(SpawnGifts::OldToolNameFactory)
                        .withDamageFactory(SpawnGifts::OldToolDamageFactory)
                        .withDisplayName("Old Sword")
        );
    }

    private static Integer OldToolDamageFactory(ItemStack itemStack) {
        return itemStack.getMaxDamage() / 2;
    }

    private static ITextComponent OldToolNameFactory(ItemStack itemStack) {
        return new StringTextComponent(TextFormatting.WHITE + "Old " + itemStack.getDisplayName().getString());
    }
}
