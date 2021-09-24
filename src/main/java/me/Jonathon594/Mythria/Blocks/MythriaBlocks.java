package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Items.MythriaBlockItem;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class MythriaBlocks {
    public static final MythriaOre TIN_ORE = null;
    public static final MythriaOre COPPER_ORE = null;
    public static final MythriaOre SILVER_ORE = null;
    public static final MythriaOre PLATINUM_ORE = null;
    public static final MythriaOre TITANIUM_ORE = null;
    public static final MythriaOre TUNGSTEN_ORE = null;
    public static final MythriaOre IRON_ORE = null;
    public static final MythriaOre GOLD_ORE = null;
    public static final MythriaOre MITHRIL_ORE = null;
    public static final MythriaOre VIOLACIUM_ORE = null;
    public static final MythriaOre EMERALD_ORE = null;
    public static final MythriaOre TOPAZ_ORE = null;
    public static final MythriaOre RUBY_ORE = null;
    public static final MythriaOre ONYX_ORE = null;
    public static final MythriaOre SAPPHIRE_ORE = null;
    public static final MythriaOre REDSTONE_ORE = null;
    public static final MythriaOre DIAMOND_ORE = null;
    public static final MythriaOre COAL_ORE = null;
    public static final MythriaOre LAPIS_ORE = null;

    public static final MythriaBlock TOPAZ_BLOCK = null;
    public static final MythriaBlock SAPPHIRE_BLOCK = null;
    public static final MythriaBlock ONYX_BLOCK = null;
    public static final MythriaBlock RUBY_BLOCK = null;

    public static final Block THATCH_BLOCK = null;
    public static final StairsBlock THATCH_STAIR = null;
    public static final PitFurnaceBlock PIT_FURNACE = null;
    public static final BlockSawhorse OAK_SAW_HORSE = null;
    public static final BlockSawhorse SPRUCE_SAW_HORSE = null;
    public static final BlockSawhorse BIRCH_SAW_HORSE = null;
    public static final BlockSawhorse JUNGLE_SAW_HORSE = null;
    public static final BlockSawhorse ACACIA_SAW_HORSE = null;
    public static final BlockSawhorse DARK_OAK_SAW_HORSE = null;
    public static final BlockSawhorse WARPED_SAW_HORSE = null;
    public static final BlockSawhorse CRIMSON_SAW_HORSE = null;
    public static final CobblestoneFurnaceBlock COBBLESTONE_FURNACE = null;
    public static final StoneFurnaceBlock STONE_FURNACE = null;
    public static final NetherFurnaceBlock NETHER_FURNACE = null;
    public static final BlockAnvil BRICK_ANVIL = null;
    public static final BlockAnvil BRONZE_ANVIL = null;
    public static final BlockAnvil IRON_ANVIL = null;
    public static final BlockAnvil STEEL_ANVIL = null;
    public static final BlockAnvil TITANIUM_ANVIL = null;
    public static final BlockGlassBlowingTable GLASS_BLOWING_TABLE = null;
    public static final Block KILN = null;
    public static final BlockStoneOven STONE_OVEN = null;
    public static final BlockCharcoalKiln CHARCOAL_KILN = null;
    public static final BlockForge FORGE = null;
    public static final BlockGroundCover GROUND_COAL = null;
    public static final BlockGroundCover GROUND_COPPER = null;
    public static final BlockGroundCover GROUND_TIN = null;
    public static final BlockGroundCover GROUND_IRON = null;
    public static final BlockGroundCover GROUND_SILVER = null;
    public static final BlockGroundCover GROUND_GOLD = null;
    public static final BlockGroundCover GROUND_PLATINUM = null;
    public static final BlockGroundCover GROUND_TITANIUM = null;
    public static final BlockGroundCover GROUND_TUNGSTEN = null;
    public static final BlockGroundCover GROUND_MITHRIL = null;
    public static final BlockGroundCover GROUND_VIOLACIUM = null;
    public static final BlockGroundCover OAK_GROUND_STICK = null;
    public static final BlockGroundCover SPRUCE_GROUND_STICK = null;
    public static final BlockGroundCover JUNGLE_GROUND_STICK = null;
    public static final BlockGroundCover BIRCH_GROUND_STICK = null;
    public static final BlockGroundCover ACACIA_GROUND_STICK = null;
    public static final BlockGroundCover DARK_OAK_GROUND_STICK = null;
    public static final BlockGroundCover CRIMSON_GROUND_STICK = null;
    public static final BlockGroundCover WARPED_GROUND_STICK = null;
    public static final BlockGroundCover GROUND_ROCK = null;
    public static final BlockGroundCover GROUND_FLINT = null;
    public static final BlockBookshelf BOOKSHELF = null;
    public static final CampfireBlock CAMPFIRE = null;
    public static final TanningRackBlock OAK_TANNING_RACK = null;
    public static final TanningRackBlock SPRUCE_TANNING_RACK = null;
    public static final TanningRackBlock BIRCH_TANNING_RACK = null;
    public static final TanningRackBlock JUNGLE_TANNING_RACK = null;
    public static final TanningRackBlock ACACIA_TANNING_RACK = null;
    public static final TanningRackBlock DARK_OAK_TANNING_RACK = null;
    public static final TanningRackBlock WARPED_TANNING_RACK = null;
    public static final TanningRackBlock CRIMSON_TANNING_RACK = null;
    public static final Block THATCH_BASKET = null;

    @SubscribeEvent
    public static void RegisterBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                new MythriaOre(Material.ROCK, "coal_ore", 10, () -> GROUND_COAL),
                new MythriaOre(Material.ROCK, "tin_ore", 10, () -> GROUND_TIN),
                new MythriaOre(Material.ROCK, "copper_ore", 10, () -> GROUND_COPPER),
                new MythriaOre(Material.ROCK, "silver_ore", 10, () -> GROUND_SILVER),
                new MythriaOre(Material.ROCK, "platinum_ore", 10, () -> GROUND_PLATINUM),
                new MythriaOre(Material.ROCK, "titanium_ore", 10, () -> GROUND_TITANIUM),
                new MythriaOre(Material.ROCK, "tungsten_ore", 10, () -> GROUND_TUNGSTEN),
                new MythriaOre(Material.ROCK, "iron_ore", 10, () -> GROUND_IRON),
                new MythriaOre(Material.ROCK, "gold_ore", 10, () -> GROUND_GOLD),
                new MythriaOre(Material.ROCK, "mithril_ore", 10, () -> GROUND_MITHRIL),
                new MythriaOre(Material.ROCK, "violacium_ore", 10, () -> GROUND_VIOLACIUM),
                new MythriaOre(Material.ROCK, "redstone_ore", 10, null),
                new MythriaOre(Material.ROCK, "lapis_ore", 10, null),
                new MythriaOre(Material.ROCK, "emerald_ore", 10, null),
                new MythriaOre(Material.ROCK, "topaz_ore", 10, null),
                new MythriaOre(Material.ROCK, "sapphire_ore", 10, null),
                new MythriaOre(Material.ROCK, "onyx_ore", 10, null),
                new MythriaOre(Material.ROCK, "ruby_ore", 10, null),
                new MythriaOre(Material.ROCK, "diamond_ore", 10, null),

                new MythriaBlock("ruby_block", 40, Block.Properties.create(Material.IRON, MaterialColor.RED).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)),
                new MythriaBlock("sapphire_block", 40, Block.Properties.create(Material.IRON, MaterialColor.BLUE).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)),
                new MythriaBlock("topaz_block", 40, Block.Properties.create(Material.IRON, MaterialColor.YELLOW).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)),
                new MythriaBlock("onyx_block", 40, Block.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)),

                new MythriaBlock("thatch_block", 8, Block.Properties.create(Material.WOOD).hardnessAndResistance(5f, 10f).sound(SoundType.PLANT)),
                new MythriaStairs(() -> THATCH_BLOCK.getDefaultState(), "thatch_stair", 6, Block.Properties.create(Material.WOOD).hardnessAndResistance(5f, 10f).sound(SoundType.PLANT)),
                new BlockGroundCover("oak_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.OAK_STICK),
                new BlockGroundCover("spruce_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.SPRUCE_STICK),
                new BlockGroundCover("jungle_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.JUNGLE_STICK),
                new BlockGroundCover("birch_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.BIRCH_STICK),
                new BlockGroundCover("acacia_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.ACACIA_STICK),
                new BlockGroundCover("dark_oak_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.DARK_OAK_STICK),
                new BlockGroundCover("warped_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.WARPED_STICK),
                new BlockGroundCover("crimson_ground_stick", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.CRIMSON_STICK),
                //new BlockGroundCover("ground_stick_pile", SoundType.WOOD, 8, Material.WOOD, () -> MythriaItems.BUNDLE_OF_STICKS),
                new BlockGroundCover("ground_rock", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.ROCK),
                new BlockGroundCover("ground_log_oak", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.OAK_LOG),
                new BlockGroundCover("ground_log_birch", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.BIRCH_LOG),
                new BlockGroundCover("ground_log_spruce", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.SPRUCE_LOG),
                new BlockGroundCover("ground_log_jungle", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.JUNGLE_LOG),
                new BlockGroundCover("ground_log_acacia", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.ACACIA_LOG),
                new BlockGroundCover("ground_log_dark_oak", SoundType.WOOD, 2, Material.WOOD, () -> MythriaItems.DARK_OAK_LOG),
                new BlockGroundCover("ground_coal", SoundType.STONE, 4, Material.ROCK, () -> Items.COAL),
                new BlockGroundCover("ground_copper", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.COPPER_ORE_ITEM),
                new BlockGroundCover("ground_tin", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.TIN_ORE_ITEM),
                new BlockGroundCover("ground_iron", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.IRON_ORE_ITEM),
                new BlockGroundCover("ground_silver", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.SILVER_ORE_ITEM),
                new BlockGroundCover("ground_gold", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.GOLD_ORE_ITEM),
                new BlockGroundCover("ground_platinum", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.PLATINUM_ORE_ITEM),
                new BlockGroundCover("ground_titanium", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.TITANIUM_ORE_ITEM),
                new BlockGroundCover("ground_tungsten", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.TUNGSTEN_ORE_ITEM),
                new BlockGroundCover("ground_mithril", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.MITHRIL_ORE_ITEM),
                new BlockGroundCover("ground_violacium", SoundType.STONE, 4, Material.ROCK, () -> MythriaItems.VIOLACIUM_ORE_ITEM),
                new BlockGroundCover("ground_flint", SoundType.STONE, 4, Material.ROCK, () -> Items.FLINT),
                new PitFurnaceBlock("pit_furnace"),
                new BlockSawhorse("oak_saw_horse", 50),
                new BlockSawhorse("spruce_saw_horse", 50),
                new BlockSawhorse("birch_saw_horse", 50),
                new BlockSawhorse("jungle_saw_horse", 50),
                new BlockSawhorse("dark_oak_saw_horse", 50),
                new BlockSawhorse("acacia_saw_horse", 50),
                new BlockSawhorse("warped_saw_horse", 50),
                new BlockSawhorse("crimson_saw_horse", 50),
                new CobblestoneFurnaceBlock(Material.ROCK, "cobblestone_furnace", 80, SoundType.STONE),
                new StoneFurnaceBlock(Material.ROCK, "stone_furnace", 80, SoundType.STONE),
                new NetherFurnaceBlock(Material.ROCK, "nether_furnace", 130, SoundType.STONE),
                new BlockAnvil(Material.ROCK, "brick_anvil", SoundType.STONE, 50, 0),
                new BlockAnvil(Material.IRON, "bronze_anvil", SoundType.METAL, 50, 1),
                new BlockAnvil(Material.IRON, "iron_anvil", SoundType.METAL, 50, 2),
                new BlockAnvil(Material.IRON, "steel_anvil", SoundType.METAL, 50, 3),
                new BlockAnvil(Material.IRON, "titanium_anvil", SoundType.METAL, 50, 4),
                new BlockGlassBlowingTable(Material.WOOD, "glass_blowing_table", 22, SoundType.WOOD),
                new BlockKiln(Material.ROCK, "kiln", 50, SoundType.STONE),
                new BlockStoneOven("stone_oven", AttributeFlag.COOKING2, 50),
                new BlockCharcoalKiln(Material.ROCK, "charcoal_kiln", 50, SoundType.STONE),
                new BlockForge("forge"),
                new BlockBookshelf("bookshelf", 50),
                new CampfireBlock("campfire"),
                new TanningRackBlock("oak_tanning_rack", 20),
                new TanningRackBlock("spruce_tanning_rack", 20),
                new TanningRackBlock("birch_tanning_rack", 20),
                new TanningRackBlock("acacia_tanning_rack", 20),
                new TanningRackBlock("jungle_tanning_rack", 20),
                new TanningRackBlock("dark_oak_tanning_rack", 20),
                new TanningRackBlock("warped_tanning_rack", 20),
                new TanningRackBlock("crimson_tanning_rack", 20),
                new MythriaBarrelBlock("thatch_basket", 3)
        );
    }

    @SubscribeEvent
    public static void RegisterItemBlocks(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new MythriaBlockItem(TIN_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(COPPER_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(SILVER_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(PLATINUM_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(TITANIUM_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(TUNGSTEN_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(IRON_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(GOLD_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(MITHRIL_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(VIOLACIUM_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(ONYX_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(RUBY_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(TOPAZ_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(SAPPHIRE_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(REDSTONE_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(LAPIS_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(DIAMOND_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(COAL_ORE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),

                new MythriaBlockItem(SAPPHIRE_BLOCK, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(RUBY_BLOCK, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(TOPAZ_BLOCK, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(ONYX_BLOCK, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),

                new MythriaBlockItem(THATCH_BLOCK, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(THATCH_STAIR, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),

                new MythriaBlockItem(COBBLESTONE_FURNACE, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(STONE_FURNACE, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(NETHER_FURNACE, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(OAK_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(BIRCH_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(SPRUCE_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(ACACIA_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(JUNGLE_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(DARK_OAK_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(WARPED_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(CRIMSON_SAW_HORSE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),

                new MythriaBlockItem(BRICK_ANVIL, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(BRONZE_ANVIL, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(IRON_ANVIL, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(STEEL_ANVIL, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(TITANIUM_ANVIL, new Item.Properties().group(ItemGroup.DECORATIONS)),

                new MythriaBlockItem(GLASS_BLOWING_TABLE, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(KILN, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(STONE_OVEN, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(CHARCOAL_KILN, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(BOOKSHELF, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)),
                new MythriaBlockItem(OAK_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(BIRCH_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(SPRUCE_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(JUNGLE_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(ACACIA_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(DARK_OAK_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(WARPED_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(CRIMSON_TANNING_RACK, new Item.Properties().group(ItemGroup.DECORATIONS)),
                new MythriaBlockItem(THATCH_BASKET, new Item.Properties().group(ItemGroup.DECORATIONS))
        );
    }
}