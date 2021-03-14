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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@ObjectHolder(Mythria.MODID)
public class MiningPerks implements IPerkRegistry {
    public static final Perk BASIC_MINING = null;

    @Override
    public List<Perk> getPerks(PerkType type) {
        return ImmutableList.of(
                new RootPerk("basic_mining", type, MythriaItems.TIN_PICKAXE, MythicSkills.MINING, 0,
                        new ResourceLocation("minecraft:textures/block/stone.png"))
                        .setDisplayName("Basic Mining")
                        .setDescription("With a pickaxe even something as hard as stone can break.")
                        .addRequiredAttribute(Attribute.ENDURANCE, 1)
                        .addRequiredAttribute(Attribute.STRENGTH, 3)
                        .addBreakableBlockTag(BlockTags.BASE_STONE_OVERWORLD.getName())
                        .addBreakableBlockTag(BlockTags.BASE_STONE_NETHER.getName())
        );
    }
}
