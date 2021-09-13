package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class SurvivalPerks implements IPerkRegistry {
    public static final Perk SURVIVAL = null;
    public static final Perk PRIMITIVE_CRAFTING = null;
    public static final Perk PRIMITIVE_TOOLS = null;
    public static final Perk BONE_TOOL_CRAFTING = null;
    public static final Perk FIRE_MAKING = null;
    public static final Perk PRIMITIVE_FURNACES = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("survival", type, Items.CAMPFIRE, null, 0,
                        new ResourceLocation("minecraft:textures/block/stone.png"))
                        .addDescriptionLine("Basic survival skills needed to brave the world of Mythria."),

                new Perk("primitive_crafting", type, MythriaItems.THATCH, MythicSkills.CRAFTING, 0, () -> SURVIVAL)
                        .setDisplayName("Primitive Crafting")
                        .addDescriptionLine("Crafting is the ability to turn objects into more useful objects.")
                        .addCraftable(MythriaBlocks.THATCH_BLOCK, MythriaBlocks.THATCH_STAIR)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 1),

                new Perk("primitive_storage", type, MythriaBlocks.THATCH_BASKET, MythicSkills.CRAFTING, 5, () -> PRIMITIVE_CRAFTING)
                        .setDisplayName("Primitive Storage")
                        .addDescriptionLine("Now I don't have to carry everything with me.")
                        .addCraftable(MythriaBlocks.THATCH_BASKET)
                        .addRequiredAttribute(Attribute.DEXTERITY, 2),

                new Perk("primitive_tools", type, MythriaItems.CUTTING_STONE, MythicSkills.CRAFTING, 0, () -> SURVIVAL)
                        .setDisplayName("Primitive Tools")
                        .addDescriptionLine("Tools can make otherwise impossible things possible.")
                        .addCraftable(MythriaItems.CUTTING_STONE)
                        .addPerkTypeUnlock(PerkType.WOODWORKING)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 1)
                        .addRequiredAttribute(Attribute.DEXTERITY, 1),

                new Perk("bone_tool_crafting", type, MythriaItems.BONE_AXE, MythicSkills.CRAFTING, 5, () -> PRIMITIVE_TOOLS)
                        .setDisplayName("Bone Tools")
                        .addDescriptionLine("Not very durable, but bone tools can be quite effective.")
                        .addPerkTypeUnlock(PerkType.MINING)
                        .addPerkTypeUnlock(PerkType.DIGGING)
                        .addPerkTypeUnlock(PerkType.LUMBERING)
                        .addCraftable(MythriaItems.BONE_AXE_HEAD, MythriaItems.BONE_HOE_HEAD, MythriaItems.BONE_DAGGER_BLADE, MythriaItems.BONE_SHOVEL_HEAD)
                        .addRequiredAttribute(Attribute.DEXTERITY, 2),

                new Perk("fire_making", type, MythriaItems.OAK_STICK, MythicSkills.FIREMAKING, 0, () -> SURVIVAL)
                        .setDisplayName("Fire!")
                        .addDescriptionLine("Fire is a very useful thing for survival.")
                        .addPerkTypeUnlock(PerkType.COOKING)
                        .addAttributeFlag(AttributeFlag.FIREMAKING1)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 2),

                new Perk("primitive_furnaces", type, MythriaItems.OAK_LOG, MythicSkills.FIREMAKING, 5, () -> FIRE_MAKING)
                        .setDisplayName("Primitive Furnaces")
                        .addDescriptionLine("Placing thatch and logs in a hole can allow fire to burn hotter in a concentrated area.")
                        .addAttributeFlag(AttributeFlag.PRIMITIVE_FURNACES)
                        .addPerkTypeUnlock(PerkType.POTTERY)
                        .addPerkTypeUnlock(PerkType.METALLURGY)
        );
    }
}
