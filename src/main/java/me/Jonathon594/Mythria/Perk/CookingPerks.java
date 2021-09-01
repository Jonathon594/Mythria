package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class CookingPerks implements IPerkRegistry {
    public static final Perk COOKING = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("cooking", type, Items.RABBIT, MythicSkills.COOKING, 0,
                        new ResourceLocation("minecraft:textures/block/terracotta.png"))
                        .addDescriptionLine("You want me to, heat my food?")
                        .addAttributeFlag(AttributeFlag.BASIC_COOKING)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 3)
        );
    }
}
