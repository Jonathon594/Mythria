package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class FletchingPerks implements IPerkRegistry {
    public static final Perk BASIC_FLETCHING = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("basic_fletching", type, Items.ARROW, MythicSkills.CRAFTING, 5,
                        new ResourceLocation("minecraft:textures/block/spruce_log.png"))
                        .setDisplayName("Basic Fletching").setDescription("It's like a spear, but smaller and propelled with more force.")
                        .addCraftable(MythriaItems.SINEW_BOWSTRING)
                        .addCraftableItemTag(new MythriaResourceLocation("arrows"))
                        .addCraftableItemTag(new MythriaResourceLocation("bows"))
                        .addRequiredAttribute(Attribute.DEXTERITY, 3)
        );
    }
}
