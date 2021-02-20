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
    public static final Item DARK_BOW = null;
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
    public static final Item IRON_INGOT = null;
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
    public static final Item GOLD_INGOT = null;
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
    public static final Item DUMMY_ELYTRA = null;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new MythriaItem("sinew", 0.25, new Item.Properties().maxStackSize(6).group(ItemGroup.MATERIALS)),

                new StickItem("oak_stick", 0.25),
                new StickItem("spruce_stick", 0.25),
                new StickItem("birch_stick", 0.25),
                new StickItem("jungle_stick", 0.25),
                new StickItem("acacia_stick", 0.25),
                new StickItem("dark_oak_stick", 0.25),
                new StickItem("crimson_stick", 0.25),
                new StickItem("warped_stick", 0.25),

                new MythriaBowItem("oak_bow", ItemTier.WOOD, 1),
                new MythriaBowItem("birch_bow", ItemTier.WOOD, 1),
                new MythriaBowItem("spruce_bow", ItemTier.WOOD, 1),
                new MythriaBowItem("jungle_bow", ItemTier.WOOD, 1),
                new MythriaBowItem("acacia_bow", ItemTier.WOOD, 1),
                new MythriaBowItem("dark_oak_bow", ItemTier.WOOD, 1),
                new MythriaBowItem("warped_bow", ItemTier.WOOD, 1),
                new MythriaBowItem("crimson_bow", ItemTier.WOOD, 1),
                new BowstringItem("sinew_bowstring", 0.25),

                new MythriaShieldItem("tin_shield", MythriaItemTier.TIN, 20),
                new MythriaShieldItem("copper_shield", MythriaItemTier.COPPER, 40),
                new MythriaShieldItem("bronze_shield", MythriaItemTier.BRONZE, 35),
                new MythriaShieldItem("iron_shield", ItemTier.IRON, 40),
                new MythriaShieldItem("steel_shield", MythriaItemTier.STEEL, 55),
                new MythriaShieldItem("titanium_shield", MythriaItemTier.TITANIUM, 35),
                new MythriaShieldItem("tungsten_shield", MythriaItemTier.TUNGSTEN, 65),

                //PrimitiveTools
                new CuttingStone("cutting_stone", 1, MythriaItemTier.PRIMITIVE, () -> PRIMITIVE_HATCHET),
                new MythriaHatchetItem(1, -2f, MythriaItemTier.PRIMITIVE, "primitive_hatchet", 2, () -> CUTTING_STONE),

                new MythriaDaggerItem("tin_dagger", MythriaItemTier.TIN, 4, () -> TIN_DAGGER_BLADE),
                new MythriaDaggerItem("copper_dagger", MythriaItemTier.COPPER, 7, () -> COPPER_DAGGER_BLADE),
                new MythriaDaggerItem("bronze_dagger", MythriaItemTier.BRONZE, 6, () -> BRONZE_DAGGER_BLADE),
                new MythriaDaggerItem("bone_dagger", MythriaItemTier.BONE, 8, () -> BONE_DAGGER_BLADE),
                new MythriaDaggerItem("iron_dagger", ItemTier.IRON, 2, () -> IRON_DAGGER_BLADE),
                new MythriaDaggerItem("steel_dagger", MythriaItemTier.STEEL, 2, () -> STEEL_DAGGER_BLADE),
                new MythriaDaggerItem("titanium_dagger", MythriaItemTier.TITANIUM, 2, () -> TITANIUM_DAGGER_BLADE),
                new MythriaDaggerItem("tungsten_dagger", MythriaItemTier.TUNGSTEN, 2, () -> TUNGSTEN_DAGGER_BLADE),

                //Tin Tools
                new MythriaShovelItem("tin_shovel", MythriaItemTier.TIN, 5, () -> TIN_SHOVEL_HEAD, false),
                new MythriaAxeItem("tin_axe", MythriaItemTier.TIN, -3.1f, 5, 8f, () -> TIN_AXE_HEAD, false),
                new MythriaPickaxeItem("tin_pickaxe", MythriaItemTier.TIN, 5, () -> TIN_PICKAXE_HEAD, false),
                new MythriaSwordItem("tin_sword", MythriaItemTier.TIN, 5, () -> TIN_SWORD_BLADE, false),
                new MythriaHoeItem("tin_hoe", MythriaItemTier.TIN, -2.0f, 3, () -> TIN_HOE_HEAD, false),
                new MythriaChiselItem(1, -2.0f, MythriaItemTier.TIN, "tin_chisel", 1, () -> TIN_CHISEL_HEAD),
                new MythriaHammerItem(0, -3.7f, MythriaItemTier.TIN, "tin_hammer", 6, () -> TIN_HAMMER_HEAD),
                new MythriaSawItem("tin_saw", -2.5f, -1.5f, MythriaItemTier.TIN, 6, () -> TIN_SAW_BLADE),

                //Copper Tools
                new MythriaShovelItem("copper_shovel", MythriaItemTier.COPPER, 10, () -> COPPER_SHOVEL_HEAD, false),
                new MythriaAxeItem("copper_axe", MythriaItemTier.COPPER, -3.1f, 10, 8f, () -> COPPER_AXE_HEAD, false),
                new MythriaPickaxeItem("copper_pickaxe", MythriaItemTier.COPPER, 10, () -> COPPER_PICKAXE_HEAD, false),
                new MythriaSwordItem("copper_sword", MythriaItemTier.COPPER, 10, () -> COPPER_SWORD_BLADE, false),
                new MythriaHoeItem("copper_hoe", MythriaItemTier.COPPER, -2.0f, 6, () -> COPPER_HOE_HEAD, false),
                new MythriaChiselItem(1, -2.0f, MythriaItemTier.COPPER, "copper_chisel", 3, () -> COPPER_CHISEL_HEAD),
                new MythriaHammerItem(0, -3.7f, MythriaItemTier.COPPER, "copper_hammer", 12, () -> COPPER_HAMMER_HEAD),
                new MythriaSawItem("copper_saw", -2.5f, -1.5f, MythriaItemTier.COPPER, 12, () -> COPPER_SAW_BLADE),

                //Bronze Tools
                new MythriaShovelItem("bronze_shovel", MythriaItemTier.BRONZE, 9, () -> BRONZE_SHOVEL_HEAD, false),
                new MythriaAxeItem("bronze_axe", MythriaItemTier.BRONZE, -3.1f, 9, 8f, () -> BRONZE_AXE_HEAD, false),
                new MythriaPickaxeItem("bronze_pickaxe", MythriaItemTier.BRONZE, 9, () -> BRONZE_PICKAXE_HEAD, false),
                new MythriaSwordItem("bronze_sword", MythriaItemTier.BRONZE, 9, () -> BRONZE_SWORD_BLADE, false),
                new MythriaHoeItem("bronze_hoe", MythriaItemTier.BRONZE, -2.0f, 5, () -> BRONZE_HOE_HEAD, false),
                new MythriaChiselItem(1, -2.0f, MythriaItemTier.BRONZE, "bronze_chisel", 2, () -> BRONZE_CHISEL_HEAD),
                new MythriaHammerItem(0, -3.7f, MythriaItemTier.BRONZE, "bronze_hammer", 10, () -> BRONZE_HAMMER_HEAD),
                new MythriaSawItem("bronze_saw", -2.5f, -1.5f, MythriaItemTier.BRONZE, 10, () -> BRONZE_SAW_BLADE),
                //Bone Tools
                new MythriaShovelItem("bone_shovel", MythriaItemTier.BONE, 11, () -> BONE_SHOVEL_HEAD, false),
                new MythriaAxeItem("bone_axe", MythriaItemTier.BONE, -3.1f, 11, 8f, () -> BONE_AXE_HEAD, false),
                new MythriaHoeItem("bone_hoe", MythriaItemTier.BONE, -2.0f, 9, () -> BONE_HOE_HEAD, false),
                //Iron
                new MythriaHoeItem("iron_hoe", ItemTier.IRON, -1.0f, 5, () -> IRON_HOE_HEAD, true),
                new MythriaShovelItem("iron_shovel", ItemTier.IRON, 9, () -> IRON_SHOVEL_HEAD, true),
                new MythriaAxeItem("iron_axe", ItemTier.IRON, -3.1f, 9, 6f, () -> IRON_AXE_HEAD, true),
                new MythriaPickaxeItem("iron_pickaxe", ItemTier.IRON, 9, () -> IRON_PICKAXE_HEAD, true),
                new MythriaSwordItem("iron_sword", ItemTier.IRON, 9, () -> IRON_SWORD_BLADE, true),
                new MythriaChiselItem(1, -1.5f, ItemTier.IRON, "iron_chisel", 2, () -> IRON_CHISEL_HEAD),
                new MythriaHammerItem(1, -3.7f, ItemTier.IRON, "iron_hammer", 10, () -> IRON_HAMMER_HEAD),
                new MythriaSawItem("iron_saw", -2.5f, -1, ItemTier.IRON, 10, () -> IRON_SAW_BLADE),
                //Steel Tools
                new MythriaShovelItem("steel_shovel", MythriaItemTier.STEEL, 9, () -> STEEL_SHOVEL_HEAD, false),
                new MythriaAxeItem("steel_axe", MythriaItemTier.STEEL, -3.1f, 9, 8.5f, () -> STEEL_AXE_HEAD, false),
                new MythriaPickaxeItem("steel_pickaxe", MythriaItemTier.STEEL, 9, () -> STEEL_PICKAXE_HEAD, false),
                new MythriaSwordItem("steel_sword", MythriaItemTier.STEEL, 9, () -> STEEL_SWORD_BLADE, false),
                new MythriaHoeItem("steel_hoe", MythriaItemTier.STEEL, -1.8f, 5, () -> STEEL_HOE_HEAD, false),
                new MythriaChiselItem(1, -1.5f, MythriaItemTier.STEEL, "steel_chisel", 2, () -> STEEL_CHISEL_HEAD),
                new MythriaHammerItem(1, -3.7f, MythriaItemTier.STEEL, "steel_hammer", 10, () -> STEEL_HAMMER_HEAD),
                new MythriaSawItem("steel_saw", -2.5f, 1, MythriaItemTier.STEEL, 10, () -> STEEL_SAW_BLADE),
                //Titanium Tools
                new MythriaShovelItem("titanium_shovel", MythriaItemTier.TITANIUM, 8, () -> TITANIUM_SHOVEL_HEAD, false),
                new MythriaAxeItem("titanium_axe", MythriaItemTier.TITANIUM, -3.1f, 8, 8.5f, () -> TITANIUM_AXE_HEAD, false),
                new MythriaPickaxeItem("titanium_pickaxe", MythriaItemTier.TITANIUM, 8, () -> TITANIUM_PICKAXE_HEAD, false),
                new MythriaSwordItem("titanium_sword", MythriaItemTier.TITANIUM, 8, () -> TITANIUM_SWORD_BLADE, false),
                new MythriaHoeItem("titanium_hoe", MythriaItemTier.TITANIUM, -1.6f, 4, () -> TITANIUM_HOE_HEAD, false),
                new MythriaChiselItem(1, -1.5f, MythriaItemTier.TITANIUM, "titanium_chisel", 1, () -> TITANIUM_CHISEL_HEAD),
                new MythriaHammerItem(2, -3.7f, MythriaItemTier.TITANIUM, "titanium_hammer", 9, () -> TITANIUM_HAMMER_HEAD),
                new MythriaSawItem("titanium_saw", -2.5f, 1, MythriaItemTier.TITANIUM, 9, () -> TITANIUM_SAW_BLADE),
                new MythriaShovelItem("tungsten_shovel", MythriaItemTier.TUNGSTEN, 8, () -> TUNGSTEN_SHOVEL_HEAD, false),
                new MythriaAxeItem("tungsten_axe", MythriaItemTier.TUNGSTEN, -3.0f, 8, 8.5f, () -> TUNGSTEN_AXE_HEAD, false),
                new MythriaPickaxeItem("tungsten_pickaxe", MythriaItemTier.TUNGSTEN, 8, () -> TUNGSTEN_PICKAXE_HEAD, false),
                new MythriaSwordItem("tungsten_sword", MythriaItemTier.TUNGSTEN, 8, () -> TUNGSTEN_SWORD_BLADE, false),
                new MythriaHoeItem("tungsten_hoe", MythriaItemTier.TUNGSTEN, -1.4f, 4, () -> TUNGSTEN_HOE_HEAD, false),
                new MythriaChiselItem(1, -1.2f, MythriaItemTier.TUNGSTEN, "tungsten_chisel", 1, () -> TUNGSTEN_CHISEL_HEAD),
                new MythriaHammerItem(2, -3.7f, MythriaItemTier.TUNGSTEN, "tungsten_hammer", 9, () -> TUNGSTEN_HAMMER_HEAD),
                new MythriaSawItem("tungsten_saw", -2.2f, 1, MythriaItemTier.TUNGSTEN, 9, () -> TUNGSTEN_SAW_BLADE),
                //Ingots
                new IngotItem("tin_ingot", 1, MythriaMaterial.TIN, EnumMetalShape.INGOT),
                new IngotItem("tin_double_ingot", 2, MythriaMaterial.TIN, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("tin_sheet", 2, MythriaMaterial.TIN, EnumMetalShape.SHEET),
                new IngotItem("tin_double_sheet", 4, MythriaMaterial.TIN, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("copper_ingot", 3, MythriaMaterial.COPPER, EnumMetalShape.INGOT),
                new IngotItem("copper_double_ingot", 6, MythriaMaterial.COPPER, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("copper_sheet", 6, MythriaMaterial.COPPER, EnumMetalShape.SHEET),
                new IngotItem("copper_double_sheet", 12, MythriaMaterial.COPPER, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("bronze_ingot", 2, MythriaMaterial.BRONZE, EnumMetalShape.INGOT),
                new IngotItem("bronze_double_ingot", 4, MythriaMaterial.BRONZE, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("bronze_sheet", 4, MythriaMaterial.BRONZE, EnumMetalShape.SHEET),
                new IngotItem("bronze_double_sheet", 8, MythriaMaterial.BRONZE, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("iron_ingot", 2, MythriaMaterial.IRON, EnumMetalShape.INGOT),
                new IngotItem("iron_double_ingot", 4, MythriaMaterial.IRON, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("iron_sheet", 4, MythriaMaterial.IRON, EnumMetalShape.SHEET),
                new IngotItem("iron_double_sheet", 8, MythriaMaterial.IRON, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("steel_ingot", 2, MythriaMaterial.STEEL, EnumMetalShape.INGOT),
                new IngotItem("steel_double_ingot", 4, MythriaMaterial.STEEL, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("steel_sheet", 4, MythriaMaterial.STEEL, EnumMetalShape.SHEET),
                new IngotItem("steel_double_sheet", 8, MythriaMaterial.STEEL, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("titanium_ingot", 2, MythriaMaterial.TITANIUM, EnumMetalShape.INGOT),
                new IngotItem("titanium_double_ingot", 4, MythriaMaterial.TITANIUM, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("titanium_sheet", 4, MythriaMaterial.TITANIUM, EnumMetalShape.SHEET),
                new IngotItem("titanium_double_sheet", 8, MythriaMaterial.TITANIUM, EnumMetalShape.DOUBLE_SHEET),
                new GlassIngotItem("glass_ingot", 4, EnumGlassShape.INGOT),
                new IngotItem("silver_ingot", 3, MythriaMaterial.SILVER, EnumMetalShape.INGOT),
                new IngotItem("gold_ingot", 3, MythriaMaterial.GOLD, EnumMetalShape.INGOT),
                new IngotItem("gold_double_ingot", 6, MythriaMaterial.GOLD, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("gold_sheet", 6, MythriaMaterial.GOLD, EnumMetalShape.SHEET),
                new IngotItem("gold_double_sheet", 12, MythriaMaterial.GOLD, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("tungsten_ingot", 4, MythriaMaterial.TUNGSTEN, EnumMetalShape.INGOT),
                new IngotItem("tungsten_double_ingot", 8, MythriaMaterial.TUNGSTEN, EnumMetalShape.DOUBLE_INGOT),
                new IngotItem("tungsten_sheet", 8, MythriaMaterial.TUNGSTEN, EnumMetalShape.SHEET),
                new IngotItem("tungsten_double_sheet", 16, MythriaMaterial.TUNGSTEN, EnumMetalShape.DOUBLE_SHEET),
                new IngotItem("platinum_ingot", 4, MythriaMaterial.PLATINUM, EnumMetalShape.INGOT),
                new CoinItem("gold_coin", 0.03),
                new CoinItem("silver_coin", 0.018),
                new CoinItem("copper_coin", 0.015),
                new CoinItem("platinum_coin", 0.03),
                new EmptyMoldItem("ceramic_mold_ingot", 1, 500, EnumMetalShape.INGOT),
                new EmptyMoldItem("ceramic_mold_axe", 1, 500, EnumMetalShape.AXE),
                new EmptyMoldItem("ceramic_mold_pick", 1, 500, EnumMetalShape.PICK),
                new EmptyMoldItem("ceramic_mold_shovel", 1, 500, EnumMetalShape.SHOVEL),
                new EmptyMoldItem("ceramic_mold_sword", 1, 500, EnumMetalShape.SWORD),
                new EmptyMoldItem("ceramic_mold_hoe", 1, 300, EnumMetalShape.HOE),
                new EmptyMoldItem("ceramic_mold_dagger", 1, 200, EnumMetalShape.DAGGER),
                new EmptyMoldItem("ceramic_mold_hammer", 1, 500, EnumMetalShape.HAMMER),
                new EmptyMoldItem("ceramic_mold_chisel", 1, 100, EnumMetalShape.CHISEL),
                new EmptyMoldItem("ceramic_mold_saw", 1, 100, EnumMetalShape.SAW),
                new CrucibleItem("ceramic_crucible", 1.2),
                //Molds
                new PotteryItem("clay_mold_ingot", 1, () -> CERAMIC_MOLD_INGOT),
                new PotteryItem("clay_mold_axe", 1, () -> CERAMIC_MOLD_AXE),
                new PotteryItem("clay_mold_pick", 1, () -> CERAMIC_MOLD_PICK),
                new PotteryItem("clay_mold_shovel", 1, () -> CERAMIC_MOLD_SHOVEL),
                new PotteryItem("clay_mold_sword", 1, () -> CERAMIC_MOLD_SWORD),
                new PotteryItem("clay_mold_hoe", 1, () -> CERAMIC_MOLD_HOE),
                new PotteryItem("clay_mold_dagger", 1, () -> CERAMIC_MOLD_DAGGER),
                new PotteryItem("clay_mold_hammer", 1, () -> CERAMIC_MOLD_HAMMER),
                new PotteryItem("clay_mold_chisel", 1, () -> CERAMIC_MOLD_CHISEL),
                new PotteryItem("clay_mold_saw", 1, () -> CERAMIC_MOLD_SAW),
                new PotteryItem("clay_brick", 1, () -> Items.BRICK),
                new PotteryItem("clay_crucible", 2, () -> MythriaItems.CERAMIC_CRUCIBLE),

                //Tool Heads
                new ToolHeadItem("tin_sword_blade", 4, () -> MythriaItems.TIN_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.TIN),
                new ToolHeadItem("tin_axe_head", 4, () -> MythriaItems.TIN_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.TIN),
                new ToolHeadItem("tin_pickaxe_head", 4, () -> MythriaItems.TIN_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.TIN),
                new ToolHeadItem("tin_shovel_head", 4, () -> MythriaItems.TIN_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.TIN),
                new ToolHeadItem("tin_hoe_head", 3, () -> MythriaItems.TIN_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.TIN),
                new ToolHeadItem("tin_dagger_blade", 2.5, () -> MythriaItems.TIN_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.TIN),
                new ToolHeadItem("tin_hammer_head", 6, () -> MythriaItems.TIN_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.TIN),
                new ToolHeadItem("tin_chisel_head", 0.5, () -> MythriaItems.TIN_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.TIN),
                new ToolHeadItem("tin_saw_blade", 0.5, () -> MythriaItems.TIN_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.TIN),

                new ToolHeadItem("copper_sword_blade", 9, () -> MythriaItems.COPPER_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_axe_head", 9, () -> MythriaItems.COPPER_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_pickaxe_head", 9, () -> MythriaItems.COPPER_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_shovel_head", 9, () -> MythriaItems.COPPER_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_hoe_head", 7.5, () -> MythriaItems.COPPER_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_dagger_blade", 6.25, () -> MythriaItems.COPPER_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_hammer_head", 12, () -> MythriaItems.COPPER_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_chisel_head", 1.15, () -> MythriaItems.COPPER_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.COPPER),
                new ToolHeadItem("copper_saw_blade", 1.15, () -> MythriaItems.COPPER_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.COPPER),
                new ToolHeadItem("bronze_sword_blade", 8, () -> MythriaItems.BRONZE_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_axe_head", 8, () -> MythriaItems.BRONZE_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_pickaxe_head", 8, () -> MythriaItems.BRONZE_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_shovel_head", 8, () -> MythriaItems.BRONZE_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_hoe_head", 6, () -> MythriaItems.BRONZE_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_dagger_blade", 5, () -> MythriaItems.BRONZE_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_hammer_head", 10, () -> MythriaItems.BRONZE_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_chisel_head", 1, () -> MythriaItems.BRONZE_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.BRONZE),
                new ToolHeadItem("bronze_saw_blade", 1, () -> MythriaItems.BRONZE_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.BRONZE),
                new ToolHeadItem("bone_axe_head", 10, () -> MythriaItems.BONE_AXE, new Item.Properties()),
                new ToolHeadItem("bone_shovel_head", 10, () -> MythriaItems.BONE_SHOVEL, new Item.Properties()),
                new ToolHeadItem("bone_hoe_head", 8, () -> MythriaItems.BONE_HOE, new Item.Properties()),
                new ToolHeadItem("bone_dagger_blade", 7, () -> MythriaItems.BONE_DAGGER, new Item.Properties()),
                new ToolHeadItem("iron_sword_blade", 8, () -> Items.IRON_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.IRON),
                new ToolHeadItem("iron_axe_head", 8, () -> Items.IRON_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.IRON),
                new ToolHeadItem("iron_pickaxe_head", 8, () -> Items.IRON_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.IRON),
                new ToolHeadItem("iron_shovel_head", 8, () -> Items.IRON_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.IRON),
                new ToolHeadItem("iron_hoe_head", 6, () -> Items.IRON_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.IRON),
                new ToolHeadItem("iron_dagger_blade", 5, () -> MythriaItems.IRON_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.IRON),
                new ToolHeadItem("iron_hammer_head", 10, () -> MythriaItems.IRON_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.IRON),
                new ToolHeadItem("iron_chisel_head", 1, () -> MythriaItems.IRON_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.IRON),
                new ToolHeadItem("iron_saw_blade", 1, () -> MythriaItems.IRON_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.IRON),
                new ToolHeadItem("steel_sword_blade", 8, () -> MythriaItems.STEEL_SWORD, new Item.Properties()),
                new ToolHeadItem("steel_axe_head", 8, () -> MythriaItems.STEEL_AXE, new Item.Properties()),
                new ToolHeadItem("steel_pickaxe_head", 8, () -> MythriaItems.STEEL_PICKAXE, new Item.Properties()),
                new ToolHeadItem("steel_shovel_head", 8, () -> MythriaItems.STEEL_SHOVEL, new Item.Properties()),
                new ToolHeadItem("steel_hoe_head", 6, () -> MythriaItems.STEEL_HOE, new Item.Properties()),
                new ToolHeadItem("steel_dagger_blade", 5, () -> MythriaItems.STEEL_DAGGER, new Item.Properties()),
                new ToolHeadItem("steel_hammer_head", 10, () -> MythriaItems.STEEL_HAMMER, new Item.Properties()),
                new ToolHeadItem("steel_chisel_head", 1, () -> MythriaItems.STEEL_CHISEL, new Item.Properties()),
                new ToolHeadItem("steel_saw_blade", 1, () -> MythriaItems.STEEL_SAW, new Item.Properties()),
                new ToolHeadItem("titanium_sword_blade", 8, () -> MythriaItems.TITANIUM_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_axe_head", 8, () -> MythriaItems.TITANIUM_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_pickaxe_head", 8, () -> MythriaItems.TITANIUM_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_shovel_head", 8, () -> MythriaItems.TITANIUM_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_hoe_head", 6, () -> MythriaItems.TITANIUM_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_dagger_blade", 5, () -> MythriaItems.TITANIUM_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_hammer_head", 10, () -> MythriaItems.TITANIUM_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_chisel_head", 1, () -> MythriaItems.TITANIUM_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.TITANIUM),
                new ToolHeadItem("titanium_saw_blade", 1, () -> MythriaItems.TITANIUM_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.TITANIUM),
                new ToolHeadItem("tungsten_sword_blade", 8, () -> MythriaItems.TUNGSTEN_SWORD, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SWORD, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_axe_head", 8, () -> MythriaItems.TUNGSTEN_AXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.AXE, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_pickaxe_head", 8, () -> MythriaItems.TUNGSTEN_PICKAXE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.PICK, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_shovel_head", 8, () -> MythriaItems.TUNGSTEN_SHOVEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SHOVEL, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_hoe_head", 6, () -> MythriaItems.TUNGSTEN_HOE, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HOE, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_dagger_blade", 5, () -> MythriaItems.TUNGSTEN_DAGGER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.DAGGER, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_hammer_head", 10, () -> MythriaItems.TUNGSTEN_HAMMER, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.HAMMER, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_chisel_head", 1, () -> MythriaItems.TUNGSTEN_CHISEL, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.CHISEL, MythriaMaterial.TUNGSTEN),
                new ToolHeadItem("tungsten_saw_blade", 1, () -> MythriaItems.TUNGSTEN_SAW, new Item.Properties())
                        .createCastingRecipe(EnumMetalShape.SAW, MythriaMaterial.TUNGSTEN),

                new FilledMoldItem("ceramic_mold_closed", 9),

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
                new ToolHandleItem("oak_tool_handle", 1),
                new ToolHandleItem("birch_tool_handle", 1),
                new ToolHandleItem("jungle_tool_handle", 1),
                new ToolHandleItem("acacia_tool_handle", 1),
                new ToolHandleItem("spruce_tool_handle", 1),
                new ToolHandleItem("dark_oak_tool_handle", 1),
                new ToolHandleItem("warped_tool_handle", 1),
                new ToolHandleItem("crimson_tool_handle", 1),
                new ToolHandleItem("bone_tool_handle", 0.5),

                new ToolHandleItem("oak_blade_handle", 1),
                new ToolHandleItem("birch_blade_handle", 1),
                new ToolHandleItem("jungle_blade_handle", 1),
                new ToolHandleItem("acacia_blade_handle", 1),
                new ToolHandleItem("spruce_blade_handle", 1),
                new ToolHandleItem("dark_oak_blade_handle", 1),
                new ToolHandleItem("warped_blade_handle", 1),
                new ToolHandleItem("crimson_blade_handle", 1),
                new ToolHandleItem("bone_blade_handle", 0.25),

                new ToolHandleItem("oak_saw_handle", 1),
                new ToolHandleItem("birch_saw_handle", 1),
                new ToolHandleItem("jungle_saw_handle", 1),
                new ToolHandleItem("acacia_saw_handle", 1),
                new ToolHandleItem("spruce_saw_handle", 1),
                new ToolHandleItem("dark_oak_saw_handle", 1),
                new ToolHandleItem("warped_saw_handle", 1),
                new ToolHandleItem("crimson_saw_handle", 1),

                new MythriaItemThrowable("rock", 4, 4, ItemGroup.MATERIALS, 2),

                new MythriaItem("stone_brick", 1, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(8)),
                new LogItem("oak_log", 10),
                new LogItem("birch_log", 10),
                new LogItem("spruce_log", 10),
                new LogItem("acacia_log", 10),
                new LogItem("jungle_log", 10),
                new LogItem("dark_oak_log", 10),
                new LogItem("warped_log", 10),
                new LogItem("crimson_log", 10),
                new PlankItem("oak_plank", 5),
                new PlankItem("birch_plank", 5),
                new PlankItem("spruce_plank", 5),
                new PlankItem("acacia_plank", 5),
                new PlankItem("jungle_plank", 5),
                new PlankItem("dark_oak_plank", 5),
                new PlankItem("warped_plank", 5),
                new PlankItem("crimson_plank", 5),
                //Ore
                new OreItem("copper_ore_item", 3),
                new OreItem("tin_ore_item", 2),
                new OreItem("iron_ore_item", 5),
                new OreItem("silver_ore_item", 5),
                new OreItem("gold_ore_item", 20),
                new OreItem("platinum_ore_item", 8),
                new OreItem("titanium_ore_item", 12),
                new OreItem("tungsten_ore_item", 14),
                new OreItem("mithril_ore_item", 14),
                new OreItem("violacium_ore_item", 32),

                new ThatchItem("thatch", 0.5),
                new OreItem("sand", 1),
                new MythriaItem("wheat_dough", 1, new Item.Properties().group(ItemGroup.FOOD)),

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

                new MythriaItem("oak_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("spruce_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("birch_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("jungle_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("acacia_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("dark_oak_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("warped_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("crimson_arrow_shaft", 0.1f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),

                new MythriaItem("animal_hide", 10, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(6)),

                new MythriaItem("ruby", 0.05f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("onyx", 0.05f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("sapphire", 0.05f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),
                new MythriaItem("topaz", 0.05f, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(16)),

                //Overrides
                new ClayBallItem("clay_ball", 1f, new Item.Properties().group(ItemGroup.MATERIALS)),
                new HeatableItem("brick", 0.9f, new Item.Properties().group(ItemGroup.MATERIALS), true),

                new SpearItem("oak_spear", MythriaItemTier.PRIMITIVE, 1, 0, 0.0),
                new SpearItem("birch_spear", MythriaItemTier.PRIMITIVE, 0.8, 0.2, 0.0),
                new SpearItem("spruce_spear", MythriaItemTier.PRIMITIVE, 1.1, 0.0, 1.0),
                new SpearItem("jungle_spear", MythriaItemTier.PRIMITIVE, 0.9, 0.1, 0.0),
                new SpearItem("acacia_spear", MythriaItemTier.PRIMITIVE, 0.9, 0.1, 0.0),
                new SpearItem("dark_oak_spear", MythriaItemTier.PRIMITIVE, 1.2, 0, 2.0),
                new SpearItem("warped_spear", MythriaItemTier.PRIMITIVE, 0.9, 0.3, 0.0),
                new SpearItem("crimson_spear", MythriaItemTier.PRIMITIVE, 1.2, 0, 4.0),

                new LeatherItem("leather", 2, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(4))
        );
    }
}
