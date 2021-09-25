package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class RootPerk extends Perk {
    private final ResourceLocation background;

    public RootPerk(final String name, final PerkType type, final IItemProvider icon, final Skill relatedSkill, final int requiredLevel,
                    final ResourceLocation background) {
        super(name, type, icon, relatedSkill, requiredLevel, () -> null);
        this.background = background;
    }

    public ResourceLocation getBackground() {
        return background;
    }

    @Override
    public String getDisplayName() {
        return MythriaUtil.capitalizeWords(getType().name().replace("_", " "));
    }

    @Override
    public Perk setDisplayName(String displayName) {
        System.out.println("Setting display name on RootPerk will have no effect.");
        return this;
    }
}
