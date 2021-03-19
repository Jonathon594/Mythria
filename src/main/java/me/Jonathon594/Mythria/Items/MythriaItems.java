package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class MythriaItems {
    //Bows
    public static final Item OAK_BOW = null;
    public static final Item SPRUCE_BOW = null;
    public static final Item BIRCH_BOW = null;
    public static final Item ACACIA_BOW = null;
    public static final Item JUNGLE_BOW = null;
    public static final Item DARK_OAK_BOW = null;
    public static final Item WARPED_BOW = null;
    public static final Item CRIMSON_BOW = null;

    //Shields
    public static final MythriaShieldItem TIN_SHIELD = null;
    public static final MythriaShieldItem COPPER_SHIELD = null;
    public static final MythriaShieldItem BRONZE_SHIELD = null;
    public static final MythriaShieldItem IRON_SHIELD = null;
    public static final MythriaShieldItem STEEL_SHIELD = null;
    public static final MythriaShieldItem TITANIUM_SHIELD = null;
    public static final MythriaShieldItem TUNGSTEN_SHIELD = null;

    //Primitive
    public static final Item CUTTING_STONE = null;
    public static final Item PRIMITIVE_HATCHET = null;

    //Daggers
    public static final Item TIN_DAGGER = null;
    public static final Item COPPER_DAGGER = null;
    public static final Item BRONZE_DAGGER = null;
    public static final Item IRON_DAGGER = null;
    public static final Item GOLDEN_DAGGER = null;
    public static final Item STEEL_DAGGER = null;
    public static final Item TITANIUM_DAGGER = null;
    public static final Item TUNGSTEN_DAGGER = null;

    //Tin Tools
    public static final Item TIN_SHOVEL = null;
    public static final Item TIN_AXE = null;
    public static final Item TIN_PICKAXE = null;
    public static final Item TIN_SWORD = null;
    public static final Item TIN_HOE = null;
    public static final Item TIN_CHISEL = null;
    public static final Item TIN_HAMMER = null;
    public static final Item TIN_SAW = null;


    //Copper Tools
    public static final Item COPPER_SHOVEL = null;
    public static final Item COPPER_AXE = null;
    public static final Item COPPER_PICKAXE = null;
    public static final Item COPPER_SWORD = null;
    public static final Item COPPER_HOE = null;
    public static final Item COPPER_CHISEL = null;
    public static final Item COPPER_HAMMER = null;
    public static final Item COPPER_SAW = null;

    //Bronze Tools
    public static final Item BRONZE_SHOVEL = null;
    public static final Item BRONZE_AXE = null;
    public static final Item BRONZE_PICKAXE = null;
    public static final Item BRONZE_SWORD = null;
    public static final Item BRONZE_HOE = null;
    public static final Item BRONZE_CHISEL = null;
    public static final Item BRONZE_HAMMER = null;
    public static final Item BRONZE_SAW = null;

    //Bone Tools
    public static final Item BONE_SHOVEL = null;
    public static final Item BONE_AXE = null;
    public static final Item BONE_HOE = null;
    public static final Item BONE_DAGGER = null;

    //Iron
    public static final Item IRON_CHISEL = null;
    public static final Item IRON_HAMMER = null;
    public static final Item IRON_SAW = null;

    public static final Item GOLDEN_CHISEL = null;
    public static final Item GOLDEN_HAMMER = null;
    public static final Item GOLDEN_SAW = null;

    //Steel Tools
    public static final Item STEEL_SHOVEL = null;
    public static final Item STEEL_AXE = null;
    public static final Item STEEL_PICKAXE = null;
    public static final Item STEEL_SWORD = null;
    public static final Item STEEL_HOE = null;
    public static final Item STEEL_CHISEL = null;
    public static final Item STEEL_HAMMER = null;
    public static final Item STEEL_SAW = null;
    //Titanium Tools
    public static final Item TITANIUM_SHOVEL = null;
    public static final Item TITANIUM_AXE = null;
    public static final Item TITANIUM_PICKAXE = null;
    public static final Item TITANIUM_SWORD = null;
    public static final Item TITANIUM_HOE = null;
    public static final Item TITANIUM_CHISEL = null;
    public static final Item TITANIUM_HAMMER = null;
    public static final Item TITANIUM_SAW = null;
    public static final Item TUNGSTEN_SHOVEL = null;
    public static final Item TUNGSTEN_AXE = null;
    public static final Item TUNGSTEN_PICKAXE = null;
    public static final Item TUNGSTEN_SWORD = null;
    public static final Item TUNGSTEN_HOE = null;
    public static final Item TUNGSTEN_CHISEL = null;
    public static final Item TUNGSTEN_HAMMER = null;
    public static final Item TUNGSTEN_SAW = null;
    //Ingots
    public static final Item TIN_INGOT = null;
    public static final Item TIN_DOUBLE_INGOT = null;
    public static final Item TIN_SHEET = null;
    public static final Item TIN_DOUBLE_SHEET = null;
    public static final Item BRONZE_INGOT = null;
    public static final Item BRONZE_DOUBLE_INGOT = null;
    public static final Item BRONZE_SHEET = null;
    public static final Item BRONZE_DOUBLE_SHEET = null;
    public static final Item IRON_DOUBLE_INGOT = null;
    public static final Item IRON_SHEET = null;
    public static final Item IRON_DOUBLE_SHEET = null;
    public static final Item STEEL_INGOT = null;
    public static final Item STEEL_DOUBLE_INGOT = null;
    public static final Item STEEL_SHEET = null;
    public static final Item STEEL_DOUBLE_SHEET = null;
    public static final Item TITANIUM_INGOT = null;
    public static final Item TITANIUM_DOUBLE_INGOT = null;
    public static final Item TITANIUM_SHEET = null;
    public static final Item TITANIUM_DOUBLE_SHEET = null;
    public static final Item GLASS_INGOT = null;
    public static final Item SILVER_INGOT = null;
    public static final Item COPPER_INGOT = null;
    public static final Item COPPER_DOUBLE_INGOT = null;
    public static final Item COPPER_SHEET = null;
    public static final Item COPPER_DOUBLE_SHEET = null;
    public static final Item GOLD_DOUBLE_INGOT = null;
    public static final Item GOLD_SHEET = null;
    public static final Item GOLD_DOUBLE_SHEET = null;
    public static final Item TUNGSTEN_INGOT = null;
    public static final Item TUNGSTEN_DOUBLE_INGOT = null;
    public static final Item TUNGSTEN_SHEET = null;
    public static final Item TUNGSTEN_DOUBLE_SHEET = null;
    public static final Item PLATINUM_INGOT = null;
    public static final Item GOLD_COIN = null;
    public static final Item SILVER_COIN = null;
    public static final Item COPPER_COIN = null;
    public static final Item PLATINUM_COIN = null;
    public static final Item CERAMIC_MOLD_INGOT = null;
    public static final Item CERAMIC_MOLD_AXE = null;
    public static final Item CERAMIC_MOLD_PICK = null;
    public static final Item CERAMIC_MOLD_SHOVEL = null;
    public static final Item CERAMIC_MOLD_SWORD = null;
    public static final Item CERAMIC_MOLD_HOE = null;
    public static final Item CERAMIC_MOLD_DAGGER = null;
    public static final Item CERAMIC_MOLD_HAMMER = null;
    public static final Item CERAMIC_MOLD_CHISEL = null;
    public static final Item CERAMIC_MOLD_SAW = null;
    public static final Item CERAMIC_CRUCIBLE = null;
    public static final Item CERAMIC_MOLD_CLOSED = null;
    //Molds
    public static final Item CLAY_MOLD_INGOT = null;
    public static final Item CLAY_MOLD_AXE = null;
    public static final Item CLAY_MOLD_PICK = null;
    public static final Item CLAY_MOLD_SHOVEL = null;
    public static final Item CLAY_MOLD_SWORD = null;
    public static final Item CLAY_MOLD_HOE = null;
    public static final Item CLAY_MOLD_DAGGER = null;
    public static final Item CLAY_MOLD_HAMMER = null;
    public static final Item CLAY_MOLD_CHISEL = null;
    public static final Item CLAY_MOLD_SAW = null;
    public static final Item CLAY_BRICK = null;
    public static final Item CLAY_CRUCIBLE = null;

    //Tin Tool Heads
    public static final Item TIN_SWORD_BLADE = null;
    public static final Item TIN_AXE_HEAD = null;
    public static final Item TIN_PICKAXE_HEAD = null;
    public static final Item TIN_SHOVEL_HEAD = null;
    public static final Item TIN_HOE_HEAD = null;
    public static final Item TIN_DAGGER_BLADE = null;
    public static final Item TIN_HAMMER_HEAD = null;
    public static final Item TIN_CHISEL_HEAD = null;
    public static final Item TIN_SAW_BLADE = null;

    //Copper Tool Heads
    public static final Item COPPER_SWORD_BLADE = null;
    public static final Item COPPER_AXE_HEAD = null;
    public static final Item COPPER_PICKAXE_HEAD = null;
    public static final Item COPPER_SHOVEL_HEAD = null;
    public static final Item COPPER_HOE_HEAD = null;
    public static final Item COPPER_DAGGER_BLADE = null;
    public static final Item COPPER_HAMMER_HEAD = null;
    public static final Item COPPER_CHISEL_HEAD = null;
    public static final Item COPPER_SAW_BLADE = null;

    //Bronze Tool Heads
    public static final Item BRONZE_SWORD_BLADE = null;
    public static final Item BRONZE_AXE_HEAD = null;
    public static final Item BRONZE_PICKAXE_HEAD = null;
    public static final Item BRONZE_SHOVEL_HEAD = null;
    public static final Item BRONZE_HOE_HEAD = null;
    public static final Item BRONZE_DAGGER_BLADE = null;
    public static final Item BRONZE_HAMMER_HEAD = null;
    public static final Item BRONZE_CHISEL_HEAD = null;
    public static final Item BRONZE_SAW_BLADE = null;

    //Bone Tool Heads
    //Bone Tools
    public static final Item BONE_SHOVEL_HEAD = null;
    public static final Item BONE_AXE_HEAD = null;
    public static final Item BONE_HOE_HEAD = null;
    public static final Item BONE_DAGGER_BLADE = null;


    //Iron Tool Heads
    public static final Item IRON_SWORD_BLADE = null;
    public static final Item IRON_AXE_HEAD = null;
    public static final Item IRON_PICKAXE_HEAD = null;
    public static final Item IRON_SHOVEL_HEAD = null;
    public static final Item IRON_HOE_HEAD = null;
    public static final Item IRON_DAGGER_BLADE = null;
    public static final Item IRON_HAMMER_HEAD = null;
    public static final Item IRON_CHISEL_HEAD = null;
    public static final Item IRON_SAW_BLADE = null;

    //Gold Tool Heads
    public static final Item GOLDEN_SWORD_BLADE = null;
    public static final Item GOLDEN_AXE_HEAD = null;
    public static final Item GOLDEN_PICKAXE_HEAD = null;
    public static final Item GOLDEN_SHOVEL_HEAD = null;
    public static final Item GOLDEN_HOE_HEAD = null;
    public static final Item GOLDEN_DAGGER_BLADE = null;
    public static final Item GOLDEN_HAMMER_HEAD = null;
    public static final Item GOLDEN_CHISEL_HEAD = null;
    public static final Item GOLDEN_SAW_BLADE = null;

    public static final Item STEEL_SWORD_BLADE = null;
    public static final Item STEEL_AXE_HEAD = null;
    public static final Item STEEL_PICKAXE_HEAD = null;
    public static final Item STEEL_SHOVEL_HEAD = null;
    public static final Item STEEL_HOE_HEAD = null;
    public static final Item STEEL_DAGGER_BLADE = null;
    public static final Item STEEL_HAMMER_HEAD = null;
    public static final Item STEEL_CHISEL_HEAD = null;
    public static final Item STEEL_SAW_BLADE = null;
    public static final Item TITANIUM_SWORD_BLADE = null;
    public static final Item TITANIUM_AXE_HEAD = null;
    public static final Item TITANIUM_PICKAXE_HEAD = null;
    public static final Item TITANIUM_SHOVEL_HEAD = null;
    public static final Item TITANIUM_HOE_HEAD = null;
    public static final Item TITANIUM_DAGGER_BLADE = null;
    public static final Item TITANIUM_HAMMER_HEAD = null;
    public static final Item TITANIUM_CHISEL_HEAD = null;
    public static final Item TITANIUM_SAW_BLADE = null;
    public static final Item TUNGSTEN_SWORD_BLADE = null;
    public static final Item TUNGSTEN_AXE_HEAD = null;
    public static final Item TUNGSTEN_PICKAXE_HEAD = null;
    public static final Item TUNGSTEN_SHOVEL_HEAD = null;
    public static final Item TUNGSTEN_HOE_HEAD = null;
    public static final Item TUNGSTEN_DAGGER_BLADE = null;
    public static final Item TUNGSTEN_HAMMER_HEAD = null;
    public static final Item TUNGSTEN_CHISEL_HEAD = null;
    public static final Item TUNGSTEN_SAW_BLADE = null;
    //Tool Parts
    public static final Item OAK_TOOL_HANDLE = null;
    public static final Item BIRCH_TOOL_HANDLE = null;
    public static final Item SPRUCE_TOOL_HANDLE = null;
    public static final Item JUNGLE_TOOL_HANDLE = null;
    public static final Item ACACIA_TOOL_HANDLE = null;
    public static final Item DARK_OAK_TOOL_HANDLE = null;
    public static final Item WARPED_TOOL_HANDLE = null;
    public static final Item CRIMSON_TOOL_HANDLE = null;
    public static final Item BONE_TOOL_HANDLE = null;

    public static final Item OAK_BLADE_HANDLE = null;
    public static final Item BIRCH_BLADE_HANDLE = null;
    public static final Item SPRUCE_BLADE_HANDLE = null;
    public static final Item JUNGLE_BLADE_HANDLE = null;
    public static final Item ACACIA_BLADE_HANDLE = null;
    public static final Item DARK_OAK_BLADE_HANDLE = null;
    public static final Item WARPED_BLADE_HANDLE = null;
    public static final Item CRIMSON_BLADE_HANDLE = null;
    public static final Item BONE_BLADE_HANDLE = null;

    public static final Item OAK_SAW_HANDLE = null;
    public static final Item BIRCH_SAW_HANDLE = null;
    public static final Item SPRUCE_SAW_HANDLE = null;
    public static final Item JUNGLE_SAW_HANDLE = null;
    public static final Item ACACIA_SAW_HANDLE = null;
    public static final Item DARK_OAK_SAW_HANDLE = null;
    public static final Item WARPED_SAW_HANDLE = null;
    public static final Item CRIMSON_SAW_HANDLE = null;

    public static final Item ROCK = null;
    public static final Item STONE_BRICK = null;
    public static final Item OAK_LOG = null;
    public static final Item BIRCH_LOG = null;
    public static final Item SPRUCE_LOG = null;
    public static final Item ACACIA_LOG = null;
    public static final Item JUNGLE_LOG = null;
    public static final Item DARK_OAK_LOG = null;
    public static final Item CRIMSON_LOG = null;
    public static final Item WARPED_LOG = null;
    public static final Item OAK_PLANK = null;
    public static final Item BIRCH_PLANK = null;
    public static final Item SPRUCE_PLANK = null;
    public static final Item ACACIA_PLANK = null;
    public static final Item JUNGLE_PLANK = null;
    public static final Item DARK_OAK_PLANK = null;
    //Ore
    public static final Item COPPER_ORE_ITEM = null;
    public static final Item TIN_ORE_ITEM = null;
    public static final Item IRON_ORE_ITEM = null;
    public static final Item SILVER_ORE_ITEM = null;
    public static final Item GOLD_ORE_ITEM = null;
    public static final Item PLATINUM_ORE_ITEM = null;
    public static final Item TITANIUM_ORE_ITEM = null;
    public static final Item TUNGSTEN_ORE_ITEM = null;
    public static final Item MITHRIL_ORE_ITEM = null;
    public static final Item VIOLACIUM_ORE_ITEM = null;
    public static final MythriaItem THATCH = null;
    public static final MythriaItem SAND = null;
    public static final Item WHEAT_DOUGH = null;

    public static final MythriaArrowItem OAK_ARROW = null;
    public static final MythriaArrowItem BIRCH_ARROW = null;
    public static final MythriaArrowItem SPRUCE_ARROW = null;
    public static final MythriaArrowItem JUNGLE_ARROW = null;
    public static final MythriaArrowItem ACACIA_ARROW = null;
    public static final MythriaArrowItem DARK_OAK_ARROW = null;
    public static final MythriaArrowItem WARPED_ARROW = null;
    public static final MythriaArrowItem CRIMSON_ARROW = null;

    public static final Item OAK_ARROW_SHAFT = null;
    public static final Item BIRCH_ARROW_SHAFT = null;
    public static final Item SPRUCE_ARROW_SHAFT = null;
    public static final Item JUNGLE_ARROW_SHAFT = null;
    public static final Item ACACIA_ARROW_SHAFT = null;
    public static final Item DARK_OAK_ARROW_SHAFT = null;
    public static final Item WARPED_ARROW_SHAFT = null;
    public static final Item CRIMSON_ARROW_SHAFT = null;

    public static final MythriaArmorItem PRIMITIVE_BANDOLIER = null;
    public static final MythriaArmorItem PRIMITIVE_BELT = null;

    public static final Item TIN_HELMET = null;
    public static final Item TIN_CHESTPLATE = null;
    public static final Item TIN_LEGGINGS = null;
    public static final Item TIN_BOOTS = null;
    public static final Item COPPER_HELMET = null;
    public static final Item COPPER_CHESTPLATE = null;
    public static final Item COPPER_LEGGINGS = null;
    public static final Item COPPER_BOOTS = null;
    public static final Item BRONZE_HELMET = null;
    public static final Item BRONZE_CHESTPLATE = null;
    public static final Item BRONZE_LEGGINGS = null;
    public static final Item BRONZE_BOOTS = null;
    public static final Item STEEL_HELMET = null;
    public static final Item STEEL_CHESTPLATE = null;
    public static final Item STEEL_LEGGINGS = null;
    public static final Item STEEL_BOOTS = null;
    public static final Item TITANIUM_HELMET = null;
    public static final Item TITANIUM_CHESTPLATE = null;
    public static final Item TITANIUM_LEGGINGS = null;
    public static final Item TITANIUM_BOOTS = null;
    public static final Item TUNGSTEN_HELMET = null;
    public static final Item TUNGSTEN_CHESTPLATE = null;
    public static final Item TUNGSTEN_LEGGINGS = null;
    public static final Item TUNGSTEN_BOOTS = null;

    public static final Item ANIMAL_HIDE = null;

    public static final Item OAK_SPEAR = null;
    public static final Item BIRCH_SPEAR = null;
    public static final Item SPRUCE_SPEAR = null;
    public static final Item JUNGLE_SPEAR = null;
    public static final Item DARK_OAK_SPEAR = null;
    public static final Item ACACIA_SPEAR = null;
    public static final Item SINEW_BOWSTRING = null;
    public static final Item OAK_STICK = null;
    public static final Item ACACIA_STICK = null;
    public static final Item JUNGLE_STICK = null;
    public static final Item BIRCH_STICK = null;
    public static final Item SPRUCE_STICK = null;
    public static final Item DARK_OAK_STICK = null;
    public static final Item CRIMSON_STICK = null;
    public static final Item WARPED_STICK = null;
    public static final Item CRIMSON_SPEAR = null;
    public static final Item WARPED_SPEAR = null;
    public static final Item WARPED_FUNGUS = null;
    public static final Item CRIMSON_FUNGUS = null;
    public static final Item RED_MUSHROOM = null;
    public static final Item BROWN_MUSHROOM = null;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new MythriaItem("sinew", new Item.Properties().maxStackSize(6).group(ItemGroup.MATERIALS)),

                new StickItem("oak_stick"),
                new StickItem("spruce_stick"),
                new StickItem("birch_stick"),
                new StickItem("jungle_stick"),
                new StickItem("acacia_stick"),
                new StickItem("dark_oak_stick"),
                new StickItem("crimson_stick"),
                new StickItem("warped_stick"),

                new MythriaBowItem("oak_bow", ItemTier.WOOD),
                new MythriaBowItem("birch_bow", ItemTier.WOOD),
                new MythriaBowItem("spruce_bow", ItemTier.WOOD),
                new MythriaBowItem("jungle_bow", ItemTier.WOOD),
                new MythriaBowItem("acacia_bow", ItemTier.WOOD),
                new MythriaBowItem("dark_oak_bow", ItemTier.WOOD),
                new MythriaBowItem("warped_bow", ItemTier.WOOD),
                new MythriaBowItem("crimson_bow", ItemTier.WOOD),
                new BowstringItem("sinew_bowstring"),

                new MythriaShieldItem("tin_shield", MythriaItemTier.TIN, 20),
                new MythriaShieldItem("copper_shield", MythriaItemTier.COPPER, 40),
                new MythriaShieldItem("bronze_shield", MythriaItemTier.BRONZE, 35),
                new MythriaShieldItem("iron_shield", ItemTier.IRON, 40),
                new MythriaShieldItem("steel_shield", MythriaItemTier.STEEL, 55),
                new MythriaShieldItem("titanium_shield", MythriaItemTier.TITANIUM, 35),
                new MythriaShieldItem("tungsten_shield", MythriaItemTier.TUNGSTEN, 65),

                //PrimitiveTools
                new CuttingStone("cutting_stone", MythriaItemTier.PRIMITIVE, () -> PRIMITIVE_HATCHET),
                new MythriaHatchetItem(1, -2f, MythriaItemTier.PRIMITIVE, "primitive_hatchet", 2, () -> CUTTING_STONE),

                new MythriaDaggerItem("tin_dagger", MythriaItemTier.TIN, 4, () -> TIN_DAGGER_BLADE),
                new MythriaDaggerItem("copper_dagger", MythriaItemTier.COPPER, 7, () -> COPPER_DAGGER_BLADE),
                new MythriaDaggerItem("bronze_dagger", MythriaItemTier.BRONZE, 6, () -> BRONZE_DAGGER_BLADE),
                new MythriaDaggerItem("bone_dagger", MythriaItemTier.BONE, 2, () -> BONE_DAGGER_BLADE),
                new MythriaDaggerItem("iron_dagger", ItemTier.IRON, 2, () -> IRON_DAGGER_BLADE),
                new MythriaDaggerItem("golden_dagger", ItemTier.GOLD, 8, () -> GOLDEN_DAGGER_BLADE),
                new MythriaDaggerItem("steel_dagger", MythriaItemTier.STEEL, 2, () -> STEEL_DAGGER_BLADE),
                new MythriaDaggerItem("titanium_dagger", MythriaItemTier.TITANIUM, 2, () -> TITANIUM_DAGGER_BLADE),
                new MythriaDaggerItem("tungsten_dagger", MythriaItemTier.TUNGSTEN, 2, () -> TUNGSTEN_DAGGER_BLADE),

                //Tin Tools
                new MythriaShovelItem("tin_shovel", MythriaItemTier.TIN, () -> TIN_SHOVEL_HEAD, false),
                new MythriaAxeItem("tin_axe", MythriaItemTier.TIN, -3.1f, 8f, () -> TIN_AXE_HEAD, false),
                new MythriaPickaxeItem("tin_pickaxe", MythriaItemTier.TIN, 5, () -> TIN_PICKAXE_HEAD, false),
                new MythriaSwordItem("tin_sword", MythriaItemTier.TIN, 5, () -> TIN_SWORD_BLADE, false),
                new MythriaHoeItem("tin_hoe", MythriaItemTier.TIN, -2.0f, () -> TIN_HOE_HEAD, false),
                new MythriaChiselItem(1, -2.0f, MythriaItemTier.TIN, "tin_chisel", 1, () -> TIN_CHISEL_HEAD),
                new MythriaHammerItem(0, -3.7f, MythriaItemTier.TIN, "tin_hammer", 6, () -> TIN_HAMMER_HEAD),
                new MythriaSawItem("tin_saw", -2.5f, -1.5f, MythriaItemTier.TIN, () -> TIN_SAW_BLADE),

                //Copper Tools
                new MythriaShovelItem("copper_shovel", MythriaItemTier.COPPER, () -> COPPER_SHOVEL_HEAD, false),
                new MythriaAxeItem("copper_axe", MythriaItemTier.COPPER, -3.1f, 8f, () -> COPPER_AXE_HEAD, false),
                new MythriaPickaxeItem("copper_pickaxe", MythriaItemTier.COPPER, 10, () -> COPPER_PICKAXE_HEAD, false),
                new MythriaSwordItem("copper_sword", MythriaItemTier.COPPER, 10, () -> COPPER_SWORD_BLADE, false),
                new MythriaHoeItem("copper_hoe", MythriaItemTier.COPPER, -2.0f, () -> COPPER_HOE_HEAD, false),
                new MythriaChiselItem(1, -2.0f, MythriaItemTier.COPPER, "copper_chisel", 3, () -> COPPER_CHISEL_HEAD),
                new MythriaHammerItem(0, -3.7f, MythriaItemTier.COPPER, "copper_hammer", 12, () -> COPPER_HAMMER_HEAD),
                new MythriaSawItem("copper_saw", -2.5f, -1.5f, MythriaItemTier.COPPER, () -> COPPER_SAW_BLADE),

                //Bronze Tools
                new MythriaShovelItem("bronze_shovel", MythriaItemTier.BRONZE, () -> BRONZE_SHOVEL_HEAD, false),
                new MythriaAxeItem("bronze_axe", MythriaItemTier.BRONZE, -3.1f, 8f, () -> BRONZE_AXE_HEAD, false),
                new MythriaPickaxeItem("bronze_pickaxe", MythriaItemTier.BRONZE, 9, () -> BRONZE_PICKAXE_HEAD, false),
                new MythriaSwordItem("bronze_sword", MythriaItemTier.BRONZE, 9, () -> BRONZE_SWORD_BLADE, false),
                new MythriaHoeItem("bronze_hoe", MythriaItemTier.BRONZE, -2.0f, () -> BRONZE_HOE_HEAD, false),
                new MythriaChiselItem(1, -2.0f, MythriaItemTier.BRONZE, "bronze_chisel", 2, () -> BRONZE_CHISEL_HEAD),
                new MythriaHammerItem(0, -3.7f, MythriaItemTier.BRONZE, "bronze_hammer", 10, () -> BRONZE_HAMMER_HEAD),
                new MythriaSawItem("bronze_saw", -2.5f, -1.5f, MythriaItemTier.BRONZE, () -> BRONZE_SAW_BLADE),
                //Bone Tools
                new MythriaShovelItem("bone_shovel", MythriaItemTier.BONE, () -> BONE_SHOVEL_HEAD, false),
                new MythriaAxeItem("bone_axe", MythriaItemTier.BONE, -3.1f, 8f, () -> BONE_AXE_HEAD, false),
                new MythriaHoeItem("bone_hoe", MythriaItemTier.BONE, -2.0f, () -> BONE_HOE_HEAD, false),
                //Iron
                new MythriaHoeItem("iron_hoe", ItemTier.IRON, -1.0f, () -> IRON_HOE_HEAD, true),
                new MythriaShovelItem("iron_shovel", ItemTier.IRON, () -> IRON_SHOVEL_HEAD, true),
                new MythriaAxeItem("iron_axe", ItemTier.IRON, -3.1f, 6f, () -> IRON_AXE_HEAD, true),
                new MythriaPickaxeItem("iron_pickaxe", ItemTier.IRON, 9, () -> IRON_PICKAXE_HEAD, true),
                new MythriaSwordItem("iron_sword", ItemTier.IRON, 9, () -> IRON_SWORD_BLADE, true),
                new MythriaChiselItem(1, -1.5f, ItemTier.IRON, "iron_chisel", 2, () -> IRON_CHISEL_HEAD),
                new MythriaHammerItem(1, -3.7f, ItemTier.IRON, "iron_hammer", 10, () -> IRON_HAMMER_HEAD),
                new MythriaSawItem("iron_saw", -2.5f, -1, ItemTier.IRON, () -> IRON_SAW_BLADE),
                //Gold
                new MythriaHoeItem("golden_hoe", ItemTier.GOLD, -1.0f, () -> GOLDEN_HOE_HEAD, true),
                new MythriaShovelItem("golden_shovel", ItemTier.GOLD, () -> GOLDEN_SHOVEL_HEAD, true),
                new MythriaAxeItem("golden_axe", ItemTier.GOLD, -3.1f, 6f, () -> GOLDEN_AXE_HEAD, true),
                new MythriaPickaxeItem("golden_pickaxe", ItemTier.GOLD, 9, () -> GOLDEN_PICKAXE_HEAD, true),
                new MythriaSwordItem("golden_sword", ItemTier.GOLD, 9, () -> GOLDEN_SWORD_BLADE, true),
                new MythriaChiselItem(1, -1.5f, ItemTier.GOLD, "golden_chisel", 2, () -> GOLDEN_CHISEL_HEAD),
                new MythriaHammerItem(1, -3.7f, ItemTier.GOLD, "golden_hammer", 10, () -> GOLDEN_HAMMER_HEAD),
                new MythriaSawItem("golden_saw", -2.5f, -1, ItemTier.GOLD, () -> GOLDEN_SAW_BLADE),
                //Steel Tools
                new MythriaShovelItem("steel_shovel", MythriaItemTier.STEEL, () -> STEEL_SHOVEL_HEAD, false),
                new MythriaAxeItem("steel_axe", MythriaItemTier.STEEL, -3.1f, 8.5f, () -> STEEL_AXE_HEAD, false),
                new MythriaPickaxeItem("steel_pickaxe", MythriaItemTier.STEEL, 9, () -> STEEL_PICKAXE_HEAD, false),
                new MythriaSwordItem("steel_sword", MythriaItemTier.STEEL, 9, () -> STEEL_SWORD_BLADE, false),
                new MythriaHoeItem("steel_hoe", MythriaItemTier.STEEL, -1.8f, () -> STEEL_HOE_HEAD, false),
                new MythriaChiselItem(1, -1.5f, MythriaItemTier.STEEL, "steel_chisel", 2, () -> STEEL_CHISEL_HEAD),
                new MythriaHammerItem(1, -3.7f, MythriaItemTier.STEEL, "steel_hammer", 10, () -> STEEL_HAMMER_HEAD),
                new MythriaSawItem("steel_saw", -2.5f, 1, MythriaItemTier.STEEL, () -> STEEL_SAW_BLADE),
                //Titanium Tools
                new MythriaShovelItem("titanium_shovel", MythriaItemTier.TITANIUM, () -> TITANIUM_SHOVEL_HEAD, false),
                new MythriaAxeItem("titanium_axe", MythriaItemTier.TITANIUM, -3.1f, 8.5f, () -> TITANIUM_AXE_HEAD, false),
                new MythriaPickaxeItem("titanium_pickaxe", MythriaItemTier.TITANIUM, 8, () -> TITANIUM_PICKAXE_HEAD, false),
                new MythriaSwordItem("titanium_sword", MythriaItemTier.TITANIUM, 8, () -> TITANIUM_SWORD_BLADE, false),
                new MythriaHoeItem("titanium_hoe", MythriaItemTier.TITANIUM, -1.6f, () -> TITANIUM_HOE_HEAD, false),
                new MythriaChiselItem(1, -1.5f, MythriaItemTier.TITANIUM, "titanium_chisel", 1, () -> TITANIUM_CHISEL_HEAD),
                new MythriaHammerItem(2, -3.7f, MythriaItemTier.TITANIUM, "titanium_hammer", 9, () -> TITANIUM_HAMMER_HEAD),
                new MythriaSawItem("titanium_saw", -2.5f, 1, MythriaItemTier.TITANIUM, () -> TITANIUM_SAW_BLADE),
                new MythriaShovelItem("tungsten_shovel", MythriaItemTier.TUNGSTEN, () -> TUNGSTEN_SHOVEL_HEAD, false),
                new MythriaAxeItem("tungsten_axe", MythriaItemTier.TUNGSTEN, -3.0f, 8.5f, () -> TUNGSTEN_AXE_HEAD, false),
                new MythriaPickaxeItem("tungsten_pickaxe", MythriaItemTier.TUNGSTEN, 8, () -> TUNGSTEN_PICKAXE_HEAD, false),
                new MythriaSwordItem("tungsten_sword", MythriaItemTier.TUNGSTEN, 8, () -> TUNGSTEN_SWORD_BLADE, false),
                new MythriaHoeItem("tungsten_hoe", MythriaItemTier.TUNGSTEN, -1.4f, () -> TUNGSTEN_HOE_HEAD, false),
                new MythriaChiselItem(1, -1.2f, MythriaItemTier.TUNGSTEN, "tungsten_chisel", 1, () -> TUNGSTEN_CHISEL_HEAD),
                new MythriaHammerItem(2, -3.7f, MythriaItemTier.TUNGSTEN, "tungsten_hammer", 9, () -> TUNGSTEN_HAMMER_HEAD),
                new MythriaSawItem("tungsten_saw", -2.2f, 1, MythriaItemTier.TUNGSTEN, () -> TUNGSTEN_SAW_BLADE),
                //Ingots
                new IngotItem("tin_ingot", MythriaMaterial.TIN, EnumMetalShape.INGOT),
                new IngotItem("tin_double_ingot", MythriaMaterial.TIN, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("tin_sheet", MythriaMaterial.TIN, EnumMetalShape.SHEET),
                new IngotItem("tin_double_sheet", MythriaMaterial.TIN, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("copper_ingot", MythriaMaterial.COPPER, EnumMetalShape.INGOT),
                new IngotItem("copper_double_ingot", MythriaMaterial.COPPER, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("copper_sheet", MythriaMaterial.COPPER, EnumMetalShape.SHEET),
                new IngotItem("copper_double_sheet", MythriaMaterial.COPPER, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("bronze_ingot", MythriaMaterial.BRONZE, EnumMetalShape.INGOT),
                new IngotItem("bronze_double_ingot", MythriaMaterial.BRONZE, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("bronze_sheet", MythriaMaterial.BRONZE, EnumMetalShape.SHEET),
                new IngotItem("bronze_double_sheet", MythriaMaterial.BRONZE, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("iron_ingot", MythriaMaterial.IRON, EnumMetalShape.INGOT, true),
                new IngotItem("iron_double_ingot", MythriaMaterial.IRON, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("iron_sheet", MythriaMaterial.IRON, EnumMetalShape.SHEET),
                new IngotItem("iron_double_sheet", MythriaMaterial.IRON, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("steel_ingot", MythriaMaterial.STEEL, EnumMetalShape.INGOT),
                new IngotItem("steel_double_ingot", MythriaMaterial.STEEL, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("steel_sheet", MythriaMaterial.STEEL, EnumMetalShape.SHEET),
                new IngotItem("steel_double_sheet", MythriaMaterial.STEEL, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("titanium_ingot", MythriaMaterial.TITANIUM, EnumMetalShape.INGOT),
                new IngotItem("titanium_double_ingot", MythriaMaterial.TITANIUM, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("titanium_sheet", MythriaMaterial.TITANIUM, EnumMetalShape.SHEET),
                new IngotItem("titanium_double_sheet", MythriaMaterial.TITANIUM, EnumMetalShape.DOUBLE_SHEET),
                new GlassIngotItem("glass_ingot", EnumGlassShape.INGOT),
                new IngotItem("silver_ingot", MythriaMaterial.SILVER, EnumMetalShape.INGOT),
                new IngotItem("gold_ingot", MythriaMaterial.GOLD, EnumMetalShape.INGOT, true),
                new IngotItem("gold_double_ingot", MythriaMaterial.GOLD, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("gold_sheet", MythriaMaterial.GOLD, EnumMetalShape.SHEET),
                new IngotItem("gold_double_sheet", MythriaMaterial.GOLD, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("tungsten_ingot", MythriaMaterial.TUNGSTEN, EnumMetalShape.INGOT),
                new IngotItem("tungsten_double_ingot", MythriaMaterial.TUNGSTEN, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("tungsten_sheet", MythriaMaterial.TUNGSTEN, EnumMetalShape.SHEET),
                new IngotItem("tungsten_double_sheet", MythriaMaterial.TUNGSTEN, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("platinum_ingot", MythriaMaterial.PLATINUM, EnumMetalShape.INGOT),
                new CoinItem("gold_coin"),
                new CoinItem("silver_coin"),
                new CoinItem("copper_coin"),
                new CoinItem("platinum_coin"),
                new EmptyMoldItem("ceramic_mold_ingot", 500, EnumMetalShape.INGOT),
                new EmptyMoldItem("ceramic_mold_axe", 500, EnumMetalShape.AXE),
                new EmptyMoldItem("ceramic_mold_pick", 500, EnumMetalShape.PICK),
                new EmptyMoldItem("ceramic_mold_shovel", 500, EnumMetalShape.SHOVEL),
                new EmptyMoldItem("ceramic_mold_sword", 500, EnumMetalShape.SWORD),
                new EmptyMoldItem("ceramic_mold_hoe", 300, EnumMetalShape.HOE),
                new EmptyMoldItem("ceramic_mold_dagger", 200, EnumMetalShape.DAGGER),
                new EmptyMoldItem("ceramic_mold_hammer", 500, EnumMetalShape.HAMMER),
                new EmptyMoldItem("ceramic_mold_chisel", 100, EnumMetalShape.CHISEL),
                new EmptyMoldItem("ceramic_mold_saw", 100, EnumMetalShape.SAW),
                new CrucibleItem("ceramic_crucible"),
                //Molds
                new PotteryItem("clay_mold_ingot", () -> CERAMIC_MOLD_INGOT),
                new PotteryItem("clay_mold_axe", () -> CERAMIC_MOLD_AXE),
                new PotteryItem("clay_mold_pick", () -> CERAMIC_MOLD_PICK),
                new PotteryItem("clay_mold_shovel", () -> CERAMIC_MOLD_SHOVEL),
                new PotteryItem("clay_mold_sword", () -> CERAMIC_MOLD_SWORD),
                new PotteryItem("clay_mold_hoe", () -> CERAMIC_MOLD_HOE),
                new PotteryItem("clay_mold_dagger", () -> CERAMIC_MOLD_DAGGER),
                new PotteryItem("clay_mold_hammer", () -> CERAMIC_MOLD_HAMMER),
                new PotteryItem("clay_mold_chisel", () -> CERAMIC_MOLD_CHISEL),
                new PotteryItem("clay_mold_saw", () -> CERAMIC_MOLD_SAW),
                new PotteryItem("clay_brick", () -> Items.BRICK),
                new PotteryItem("clay_crucible", () -> MythriaItems.CERAMIC_CRUCIBLE),

                //Tool Heads
                new ToolHeadItem("tin_sword_blade", () -> MythriaItems.TIN_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.TIN),
                new ToolHeadItem("tin_axe_head", () -> MythriaItems.TIN_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.TIN),
                new ToolHeadItem("tin_pickaxe_head", () -> MythriaItems.TIN_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.TIN),
                new ToolHeadItem("tin_shovel_head", () -> MythriaItems.TIN_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.TIN),
                new ToolHeadItem("tin_hoe_head", () -> MythriaItems.TIN_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.TIN),
                new ToolHeadItem("tin_dagger_blade", () -> MythriaItems.TIN_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.TIN),
                new ToolHeadItem("tin_hammer_head", () -> MythriaItems.TIN_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.TIN),
                new ToolHeadItem("tin_chisel_head", () -> MythriaItems.TIN_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.TIN),
                new ToolHeadItem("tin_saw_blade", () -> MythriaItems.TIN_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.TIN),

                new ToolHeadItem("copper_sword_blade", () -> MythriaItems.COPPER_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_axe_head", () -> MythriaItems.COPPER_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_pickaxe_head", () -> MythriaItems.COPPER_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_shovel_head", () -> MythriaItems.COPPER_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_hoe_head", () -> MythriaItems.COPPER_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_dagger_blade", () -> MythriaItems.COPPER_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_hammer_head", () -> MythriaItems.COPPER_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_chisel_head", () -> MythriaItems.COPPER_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_saw_blade", () -> MythriaItems.COPPER_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.COPPER),
                new ToolHeadItem("bronze_sword_blade", () -> MythriaItems.BRONZE_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_axe_head", () -> MythriaItems.BRONZE_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_pickaxe_head", () -> MythriaItems.BRONZE_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_shovel_head", () -> MythriaItems.BRONZE_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_hoe_head", () -> MythriaItems.BRONZE_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_dagger_blade", () -> MythriaItems.BRONZE_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_hammer_head", () -> MythriaItems.BRONZE_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_chisel_head", () -> MythriaItems.BRONZE_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_saw_blade", () -> MythriaItems.BRONZE_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.BRONZE),
                new ToolHeadItem("bone_axe_head", () -> MythriaItems.BONE_AXE, new Item.Properties()),
                new ToolHeadItem("bone_shovel_head", () -> MythriaItems.BONE_SHOVEL, new Item.Properties()),
                new ToolHeadItem("bone_hoe_head", () -> MythriaItems.BONE_HOE, new Item.Properties()),
                new ToolHeadItem("bone_dagger_blade", () -> MythriaItems.BONE_DAGGER, new Item.Properties()),
                new ToolHeadItem("iron_sword_blade", () -> Items.IRON_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.IRON),
                new ToolHeadItem("iron_axe_head", () -> Items.IRON_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.IRON),
                new ToolHeadItem("iron_pickaxe_head", () -> Items.IRON_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.IRON),
                new ToolHeadItem("iron_shovel_head", () -> Items.IRON_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.IRON),
                new ToolHeadItem("iron_hoe_head", () -> Items.IRON_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.IRON),
                new ToolHeadItem("iron_dagger_blade", () -> MythriaItems.IRON_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.IRON),
                new ToolHeadItem("iron_hammer_head", () -> MythriaItems.IRON_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.IRON),
                new ToolHeadItem("iron_chisel_head", () -> MythriaItems.IRON_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.IRON),
                new ToolHeadItem("iron_saw_blade", () -> MythriaItems.IRON_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.IRON),

                new ToolHeadItem("golden_sword_blade", () -> Items.GOLDEN_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_axe_head", () -> Items.GOLDEN_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_pickaxe_head", () -> Items.GOLDEN_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_shovel_head", () -> Items.GOLDEN_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_hoe_head", () -> Items.GOLDEN_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_dagger_blade", () -> MythriaItems.GOLDEN_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_hammer_head", () -> MythriaItems.GOLDEN_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_chisel_head", () -> MythriaItems.GOLDEN_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.GOLD),
                new ToolHeadItem("golden_saw_blade", () -> MythriaItems.GOLDEN_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.GOLD),

                new ToolHeadItem("steel_sword_blade", () -> MythriaItems.STEEL_SWORD, new Item.Properties()),
                new ToolHeadItem("steel_axe_head", () -> MythriaItems.STEEL_AXE, new Item.Properties()),
                new ToolHeadItem("steel_pickaxe_head", () -> MythriaItems.STEEL_PICKAXE, new Item.Properties()),
                new ToolHeadItem("steel_shovel_head", () -> MythriaItems.STEEL_SHOVEL, new Item.Properties()),
                new ToolHeadItem("steel_hoe_head", () -> MythriaItems.STEEL_HOE, new Item.Properties()),
                new ToolHeadItem("steel_dagger_blade", () -> MythriaItems.STEEL_DAGGER, new Item.Properties()),
                new ToolHeadItem("steel_hammer_head", () -> MythriaItems.STEEL_HAMMER, new Item.Properties()),
                new ToolHeadItem("steel_chisel_head", () -> MythriaItems.STEEL_CHISEL, new Item.Properties()),
                new ToolHeadItem("steel_saw_blade", () -> MythriaItems.STEEL_SAW, new Item.Properties()),
                new ToolHeadItem("titanium_sword_blade", () -> MythriaItems.TITANIUM_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_axe_head", () -> MythriaItems.TITANIUM_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_pickaxe_head", () -> MythriaItems.TITANIUM_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_shovel_head", () -> MythriaItems.TITANIUM_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_hoe_head", () -> MythriaItems.TITANIUM_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_dagger_blade", () -> MythriaItems.TITANIUM_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_hammer_head", () -> MythriaItems.TITANIUM_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_chisel_head", () -> MythriaItems.TITANIUM_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_saw_blade", () -> MythriaItems.TITANIUM_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.TITANIUM),
                new ToolHeadItem("tungsten_sword_blade", () -> MythriaItems.TUNGSTEN_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_axe_head", () -> MythriaItems.TUNGSTEN_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_pickaxe_head", () -> MythriaItems.TUNGSTEN_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_shovel_head", () -> MythriaItems.TUNGSTEN_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_hoe_head", () -> MythriaItems.TUNGSTEN_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_dagger_blade", () -> MythriaItems.TUNGSTEN_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_hammer_head", () -> MythriaItems.TUNGSTEN_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_chisel_head", () -> MythriaItems.TUNGSTEN_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_saw_blade", () -> MythriaItems.TUNGSTEN_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.TUNGSTEN),

                new FilledMoldItem("ceramic_mold_closed"),

//    public static final ArmorMaterial PRIMATIVE_AM EnumHelper.addArmorMaterial("primative", "mythria:primative", 3,
//            new int[]{0, 0, 0, 0}, 2, SoundEvents.BLOCK_CLOTH_PLACE, 0.0f),
//    public static final MythriaArmorItem PRIMATIVE_BANDOLIER new MythriaArmorItem("primative_bandolier", PRIMATIVE_AM,
//            6, EquipmentSlotType.CHEST, 2, 9, 0),
//    public static final MythriaArmorItem PRIMATIVE_BELT new MythriaArmorItem("primative_belt", PRIMATIVE_AM, 6,
//            EquipmentSlotType.LEGS, 1, 3, 5),
//    public static final ArmorMaterial BACKPACK_AM EnumHelper.addArmorMaterial("backpack", "mythria:backpack", 3,
//            new int[]{0, 0, 0, 0}, 2, SoundEvents.BLOCK_CLOTH_PLACE, 0.0f),
//    public static final MythriaArmorItem LEATHER_BACKPACK new MythriaArmorItem("leather_backpack", BACKPACK_AM, -1,
//            EquipmentSlotType.CHEST, 1, 27, 0),

                //Tool Parts
                new ToolHandleItem("oak_tool_handle"),
                new ToolHandleItem("birch_tool_handle"),
                new ToolHandleItem("jungle_tool_handle"),
                new ToolHandleItem("acacia_tool_handle"),
                new ToolHandleItem("spruce_tool_handle"),
                new ToolHandleItem("dark_oak_tool_handle"),
                new ToolHandleItem("warped_tool_handle"),
                new ToolHandleItem("crimson_tool_handle"),
                new ToolHandleItem("bone_tool_handle"),

                new ToolHandleItem("oak_blade_handle"),
                new ToolHandleItem("birch_blade_handle"),
                new ToolHandleItem("jungle_blade_handle"),
                new ToolHandleItem("acacia_blade_handle"),
                new ToolHandleItem("spruce_blade_handle"),
                new ToolHandleItem("dark_oak_blade_handle"),
                new ToolHandleItem("warped_blade_handle"),
                new ToolHandleItem("crimson_blade_handle"),
                new ToolHandleItem("bone_blade_handle"),

                new ToolHandleItem("oak_saw_handle"),
                new ToolHandleItem("birch_saw_handle"),
                new ToolHandleItem("jungle_saw_handle"),
                new ToolHandleItem("acacia_saw_handle"),
                new ToolHandleItem("spruce_saw_handle"),
                new ToolHandleItem("dark_oak_saw_handle"),
                new ToolHandleItem("warped_saw_handle"),
                new ToolHandleItem("crimson_saw_handle"),

                new MythriaItemThrowable("rock", 4, ItemGroup.MATERIALS, 2),

                new MythriaItem("stone_brick", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(8)),
                new LogItem("oak_log"),
                new LogItem("birch_log"),
                new LogItem("spruce_log"),
                new LogItem("acacia_log"),
                new LogItem("jungle_log"),
                new LogItem("dark_oak_log"),
                new LogItem("warped_log"),
                new LogItem("crimson_log"),
                new PlankItem("oak_plank"),
                new PlankItem("birch_plank"),
                new PlankItem("spruce_plank"),
                new PlankItem("acacia_plank"),
                new PlankItem("jungle_plank"),
                new PlankItem("dark_oak_plank"),
                new PlankItem("warped_plank"),
                new PlankItem("crimson_plank"),
                //Ore
                new OreItem("copper_ore_item"),
                new OreItem("tin_ore_item"),
                new OreItem("iron_ore_item"),
                new OreItem("silver_ore_item"),
                new OreItem("gold_ore_item"),
                new OreItem("platinum_ore_item"),
                new OreItem("titanium_ore_item"),
                new OreItem("tungsten_ore_item"),
                new OreItem("mithril_ore_item"),
                new OreItem("violacium_ore_item"),

                new ThatchItem("thatch"),
                new OreItem("sand"),
                new MythriaItem("wheat_dough", new Item.Properties().group(ItemGroup.FOOD)),

                new MythriaArrowItem("oak_arrow", 0.1),
                new MythriaArrowItem("spruce_arrow", 0.1),
                new MythriaArrowItem("birch_arrow", 0.1),
                new MythriaArrowItem("acacia_arrow", 0.1),
                new MythriaArrowItem("jungle_arrow", 0.1),
                new MythriaArrowItem("dark_oak_arrow", 0.1),
                new MythriaArrowItem("warped_arrow", 0.1),
                new MythriaArrowItem("crimson_arrow", 0.1),

//    public static final ItemMedical SPLINT new ItemMedical("splint", 0.3, CureCondition.SPLINT, AttributeFlag.MEDICAL1),
//    public static final ItemMedical WRAP new ItemMedical("wrap", 0.1, CureCondition.WRAP, AttributeFlag.MEDICAL2),
//    public static final ItemBandage PRIMITIVE_BANDAGE new ItemBandage("primitive_bandage", 0.1, 1.0),

                new MythriaArmorItem("primitive_bandolier", MythriaArmorMaterial.PRIMITIVE,
                        EquipmentSlotType.CHEST, 2, 9, 0),
                new MythriaArmorItem("primitive_belt", MythriaArmorMaterial.PRIMITIVE,
                        EquipmentSlotType.LEGS, 1, 3, 5),

                new MythriaArmorItem("tin_helmet", MythriaArmorMaterial.TIN,
                        EquipmentSlotType.HEAD, 12, 0, 0),
                new MythriaArmorItem("tin_chestplate", MythriaArmorMaterial.TIN,
                        EquipmentSlotType.CHEST, 24, 2, 0),
                new MythriaArmorItem("tin_leggings", MythriaArmorMaterial.TIN,
                        EquipmentSlotType.LEGS, 13.5, 3, 2),
                new MythriaArmorItem("tin_boots", MythriaArmorMaterial.TIN,
                        EquipmentSlotType.FEET, 12, 0, 0),

                new MythriaArmorItem("copper_helmet", MythriaArmorMaterial.COPPER,
                        EquipmentSlotType.HEAD, 26, 0, 0),
                new MythriaArmorItem("copper_chestplate", MythriaArmorMaterial.COPPER,
                        EquipmentSlotType.CHEST, 52, 2, 0),
                new MythriaArmorItem("copper_leggings", MythriaArmorMaterial.COPPER,
                        EquipmentSlotType.LEGS, 30, 3, 2),
                new MythriaArmorItem("copper_boots", MythriaArmorMaterial.COPPER,
                        EquipmentSlotType.FEET, 26, 0, 0),

                new MythriaArmorItem("bronze_helmet", MythriaArmorMaterial.BRONZE,
                        EquipmentSlotType.HEAD, 24, 0, 0),
                new MythriaArmorItem("bronze_chestplate", MythriaArmorMaterial.BRONZE,
                        EquipmentSlotType.CHEST, 48, 2, 0),
                new MythriaArmorItem("bronze_leggings", MythriaArmorMaterial.BRONZE,
                        EquipmentSlotType.LEGS, 27, 3, 2),
                new MythriaArmorItem("bronze_boots", MythriaArmorMaterial.BRONZE,
                        EquipmentSlotType.FEET, 24, 0, 0),
                new MythriaArmorItem("steel_helmet", MythriaArmorMaterial.STEEL,
                        EquipmentSlotType.HEAD, 18, 0, 0),
                new MythriaArmorItem("steel_chestplate", MythriaArmorMaterial.STEEL,
                        EquipmentSlotType.CHEST, 36, 2, 0),
                new MythriaArmorItem("steel_leggings", MythriaArmorMaterial.STEEL,
                        EquipmentSlotType.LEGS, 23, 3, 2),
                new MythriaArmorItem("steel_boots", MythriaArmorMaterial.STEEL,
                        EquipmentSlotType.FEET, 18, 0, 0),
                new MythriaArmorItem("titanium_helmet", MythriaArmorMaterial.TITANIUM,
                        EquipmentSlotType.HEAD, 25, 0, 0),
                new MythriaArmorItem("titanium_chestplate", MythriaArmorMaterial.TITANIUM,
                        EquipmentSlotType.CHEST, 55, 2, 0),
                new MythriaArmorItem("titanium_leggings", MythriaArmorMaterial.TITANIUM,
                        EquipmentSlotType.LEGS, 35, 3, 2),
                new MythriaArmorItem("titanium_boots", MythriaArmorMaterial.TITANIUM,
                        EquipmentSlotType.FEET, 25, 0, 0),
                new MythriaArmorItem("tungsten_helmet", MythriaArmorMaterial.TUNGSTEN,
                        EquipmentSlotType.HEAD, 30, 0, 0),
                new MythriaArmorItem("tungsten_chestplate", MythriaArmorMaterial.TUNGSTEN,
                        EquipmentSlotType.CHEST, 60, 2, 0),
                new MythriaArmorItem("tungsten_leggings", MythriaArmorMaterial.TUNGSTEN,
                        EquipmentSlotType.LEGS, 40, 0, 2),
                new MythriaArmorItem("tungsten_boots", MythriaArmorMaterial.TUNGSTEN,
                        EquipmentSlotType.FEET, 30, 0, 0),

                new MythriaItem("oak_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("spruce_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("birch_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("jungle_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("acacia_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("dark_oak_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("warped_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("crimson_arrow_shaft", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),

                new MythriaItem("animal_hide", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(6)),

                new MythriaItem("ruby", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("onyx", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("sapphire", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("topaz", new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),

                //Overrides
                new HeatableItem("brick", new Item.Properties().group(ItemGroup.MATERIALS), true),
                new FoodItem("brown_mushroom", 2, 0.5f),
                new FoodItem("red_mushroom", 2, 0.5f),
                new FoodItem("warped_fungus", 3, 0.5f),
                new FoodItem("crimson_fungus", 3, 0.5f),

                new SpearItem("oak_spear", MythriaItemTier.PRIMITIVE, 1, 0, 0.0),
                new SpearItem("birch_spear", MythriaItemTier.PRIMITIVE, 0.8, 0.2, 0.0),
                new SpearItem("spruce_spear", MythriaItemTier.PRIMITIVE, 1.1, 0.0, 1.0),
                new SpearItem("jungle_spear", MythriaItemTier.PRIMITIVE, 0.9, 0.1, 0.0),
                new SpearItem("acacia_spear", MythriaItemTier.PRIMITIVE, 0.9, 0.1, 0.0),
                new SpearItem("dark_oak_spear", MythriaItemTier.PRIMITIVE, 1.2, 0, 2.0),
                new SpearItem("warped_spear", MythriaItemTier.PRIMITIVE, 0.9, 0.3, 0.0),
                new SpearItem("crimson_spear", MythriaItemTier.PRIMITIVE, 1.2, 0, 4.0)
        );
    }
}
