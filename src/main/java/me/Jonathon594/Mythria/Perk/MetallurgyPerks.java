package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class MetallurgyPerks implements IPerkRegistry {
    public static final Perk METALLURGY = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("metallurgy", type, MythriaItems.CERAMIC_CRUCIBLE, Skill.METALLURGY, 0,
                        new ResourceLocation("minecraft:textures/block/coal_block.png"))
                        .withDescription("Hot metal? isn't that dangerous?")
                        .addAttributeFlag(AttributeFlag.BASIC_SMELTING)
                        .addRequiredAttribute(Attribute.INTELLIGENCE, 4)
                        .addPerkTypeUnlock(PerkType.MINING)
                        .addPerkTypeUnlock(PerkType.DIGGING)
                        .addPerkTypeUnlock(PerkType.FARMING)
        );
    }
}
