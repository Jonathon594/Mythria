package me.Jonathon594.Mythria.Perk;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class DiggingPerks implements IPerkRegistry {
    public static final Perk DIGGING = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("digging", type, MythriaItems.TIN_SHOVEL, Skill.DIGGING, 0,
                        new ResourceLocation("minecraft:textures/block/dirt.png"))
                        .withDescription("With a shovel you can move loose soils.")
                        .addRequiredAttribute(Attribute.ENDURANCE, 3)
                        .addRequiredAttribute(Attribute.STRENGTH, 1)
                        .addBreakable(Blocks.GRAVEL, Blocks.DIRT, Blocks.COARSE_DIRT,
                                Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRASS_PATH)
        );
    }
}
