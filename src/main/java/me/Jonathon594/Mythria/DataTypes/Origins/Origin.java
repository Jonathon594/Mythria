package me.Jonathon594.Mythria.DataTypes.Origins;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class Origin extends ForgeRegistryEntry<Origin> {
    protected Random random;

    private String displayName;

    public Origin(String name) {
        setRegistryName(new MythriaResourceLocation(name));
        this.displayName = name;
        random = new Random();
    }

    public void apply(ServerPlayerEntity serverPlayer, Profile profile) {
        for (ItemStack itemStack : getItems()) {
            if (!serverPlayer.addItemStackToInventory(itemStack.copy())) {
                serverPlayer.dropItem(itemStack.copy(), false);
            }
        }
        for (PerkType perkType : getPerkTypes()) {
            profile.unlockPerkType(perkType);
        }
        for (Map.Entry<MythicSkills, Integer> entry : getSkillLevels().entrySet()) {
            profile.getSkillLevels().put(entry.getKey(), MythriaUtil.getExperienceForLevel(entry.getValue()));
        }
    }


    public ITextComponent getDescription() {
        return new TranslationTextComponent("origins." + getRegistryName().getPath() + ".description");
    }

    public String getDisplayName() {
        return displayName;
    }

    public abstract int getStartingAge(Genetic genetic);

    public Origin withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    protected List<PerkType> getPerkTypes() {
        return ImmutableList.of();
    }

    protected List<ItemStack> getItems() {
        return ImmutableList.of();
    }

    protected Map<MythicSkills, Integer> getSkillLevels() {
        return Maps.newHashMap();
    }
}
