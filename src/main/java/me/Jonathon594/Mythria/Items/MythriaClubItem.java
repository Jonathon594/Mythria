package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.DataTypes.MythriaToolType;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;
import net.minecraft.item.*;

import java.util.Set;

public class MythriaClubItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();
    private final double weight;

    public MythriaClubItem(String name, float damage, float speed, IItemTier tier, double weight) {
        super(damage, speed, tier, EFFECTIVE_ON, new Properties().group(ItemGroup.COMBAT)
                .addToolType(MythriaToolType.HAMMER, tier.getHarvestLevel()));
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
    }
}
