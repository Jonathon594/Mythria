package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Enum.Skill;
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
                        .withDescription("Basic survival skills needed to brave the world of Mythria.")
                        .addPerkTypeUnlock(PerkType.BLADED_WEAPONS)
                        .addPerkTypeUnlock(PerkType.BLUNT_WEAPONS)
                        .addPerkTypeUnlock(PerkType.MARTIAL_ARTS),

                new Perk("primitive_crafting", type, MythriaItems.THATCH, Skill.CRAFTING, 0, () -> SURVIVAL)
                        .setDisplayName("Primitive Crafting")
                        .withDescription("Crafting is the ability to turn objects into more useful objects.")
                        .addCraftable(MythriaBlocks.THATCH_BLOCK, MythriaBlocks.THATCH_STAIR)
                        .addPerkTypeUnlock(PerkType.MASONRY)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 1),

                new Perk("primitive_storage", type, MythriaBlocks.THATCH_BASKET, Skill.CRAFTING, 5, () -> PRIMITIVE_CRAFTING)
                        .setDisplayName("Primitive Storage")
                        .withDescription("Now I don't have to carry everything with me.")
                        .addCraftable(MythriaBlocks.THATCH_BASKET)
                        .addRequiredAttribute(Attribute.DEXTERITY, 2),

                new Perk("primitive_tools", type, MythriaItems.CUTTING_STONE, Skill.CRAFTING, 0, () -> SURVIVAL)
                        .setDisplayName("Primitive Tools")
                        .withDescription("Tools can make otherwise impossible things possible.")
                        .addCraftable(MythriaItems.CUTTING_STONE)
                        .addPerkTypeUnlock(PerkType.WOODWORKING)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 1)
                        .addRequiredAttribute(Attribute.DEXTERITY, 1),

                new Perk("bone_tool_crafting", type, MythriaItems.BONE_AXE, Skill.CRAFTING, 5, () -> PRIMITIVE_TOOLS)
                        .setDisplayName("Bone Tools")
                        .withDescription("Not very durable, but bone tools can be quite effective.")
                        .addPerkTypeUnlock(PerkType.DIGGING)
                        .addPerkTypeUnlock(PerkType.LUMBERING)
                        .addPerkTypeUnlock(PerkType.FARMING)
                        .addCraftable(MythriaItems.BONE_AXE_HEAD, MythriaItems.BONE_HOE_HEAD, MythriaItems.BONE_DAGGER_BLADE, MythriaItems.BONE_SHOVEL_HEAD)
                        .addRequiredAttribute(Attribute.DEXTERITY, 2),

                new Perk("fire_making", type, MythriaItems.OAK_STICK, Skill.FIREMAKING, 0, () -> SURVIVAL)
                        .setDisplayName("Fire")
                        .withDescription("Fire is a very useful thing for survival.")
                        .addPerkTypeUnlock(PerkType.COOKING)
                        .addAttributeFlag(AttributeFlag.FIREMAKING)
                        .addPerkTypeUnlock(PerkType.POTTERY)
                        .addPerkTypeUnlock(PerkType.METALLURGY)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 2)
        );
    }
}
