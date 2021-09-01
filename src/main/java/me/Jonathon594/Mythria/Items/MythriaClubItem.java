package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.DataTypes.MythriaToolType;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Interface.IWeapon;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;

import java.util.Set;

public class MythriaClubItem extends ToolItem implements IWeapon {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();
    private final double weight;

    public MythriaClubItem(String name, float damage, float speed, IItemTier tier, double weight) {
        super(damage, speed, tier, EFFECTIVE_ON, new Properties().group(ItemGroup.COMBAT)
                .addToolType(MythriaToolType.HAMMER, tier.getHarvestLevel()));
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
    }

    @Override
    public MeleeCombatManager getCombatManager() {
        return null;
    }

    @Override
    public MythicSkills getUsageSkill() {
        return MythicSkills.HEAVY_WEAPONS;
    }

    @Override
    public AttributeFlag getFlagForParrying() {
        return AttributeFlag.CLUB_ABILITY_PARRY;
    }
}
