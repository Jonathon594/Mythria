package me.Jonathon594.Mythria.SpawnGifts;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class SpawnGift extends ForgeRegistryEntry<SpawnGift> {
    private String displayName;
    private List<PerkType> giftPerkTypes = new ArrayList<>();

    public SpawnGift(String name) {
        setRegistryName(new MythriaResourceLocation(name));
        this.displayName = name;
    }

    public void apply(ServerPlayerEntity serverPlayer, Profile profile) {
        for (ItemStack itemStack : getGiftItems()) {
            if (!serverPlayer.addItemStackToInventory(itemStack.copy())) {
                serverPlayer.dropItem(itemStack.copy(), false);
            }
        }
        for (PerkType perkType : getGiftPerkTypes()) {
            profile.unlockPerkType(perkType);
        }
    }

    public ITextComponent getDescription() {
        return new TranslationTextComponent("gifts." + getRegistryName().getPath() + ".description");
    }

    public String getDisplayName() {
        return displayName;
    }

    public SpawnGift withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public SpawnGift withPerkType(PerkType perkType) {
        giftPerkTypes.add(perkType);
        return this;
    }

    private List<PerkType> getGiftPerkTypes() {
        return giftPerkTypes;
    }

    protected List<ItemStack> getGiftItems() {
        return ImmutableList.of();
    }
}
