package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class LeatherWorkingPerks implements IPerkRegistry {
    public static final Perk LEATHER_WORKING = null;
    public static final Perk ADVANCED_LEATHER_WORKING = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("leather_working", type, MythriaItems.PRIMITIVE_BANDOLIER, Skill.CRAFTING, 10,
                        new ResourceLocation("minecraft:textures/block/brown_wool.png"))
                        .withDescription("This material seems strong and flexible.")
                        .addCraftable(MythriaItems.PRIMITIVE_BANDOLIER, MythriaItems.PRIMITIVE_BELT)
                        .addRequiredAttribute(Attribute.DEXTERITY, 3).addRequiredAttribute(Attribute.INTELLIGENCE, 3),

                new Perk("advanced_leather_working", type, Items.SADDLE, Skill.CRAFTING, 20, () -> LEATHER_WORKING)
                        .setDisplayName("Advanced Leather Working")
                        .addCraftable(Items.SADDLE)
                        .addRequiredAttribute(Attribute.DEXTERITY, 5).addRequiredAttribute(Attribute.INTELLIGENCE, 4),

                new Perk("leather_armor_crafting", type, Items.LEATHER_CHESTPLATE, Skill.CRAFTING, 25, () -> ADVANCED_LEATHER_WORKING)
                        .setDisplayName("Leather Armor Crafting")
                        .addCraftable(Items.LEATHER_BOOTS, Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET, Items.LEATHER_LEGGINGS)
                        .addRequiredAttribute(Attribute.DEXTERITY, 6)
        );
    }
}
