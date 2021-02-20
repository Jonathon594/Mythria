package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class RootPerk extends Perk {
    private final ResourceLocation background;

    public RootPerk(final String name, final PerkType type, final IItemProvider icon, final MythicSkills relatedSkill, final int requiredLevel,
                    final ResourceLocation background) {
        super(name, type, icon, relatedSkill, requiredLevel, () -> null);
        this.background = background;
    }

    public ResourceLocation getBackground() {
        return background;
    }
}
