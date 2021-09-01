package me.Jonathon594.Mythria.Perk;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Managers.MaterialManager;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Perks {
    public static void AttemptBuy(final PlayerEntity player, final Perk pa) {
        final Profile p = ProfileProvider.getProfile(player);
        if (player.isCreative()) {
            addPerk(player, pa, p);
            return;
        }
        if (pa == null)
            return;

        if (p.getPlayerSkills().contains(pa)) {
            player.sendMessage(new StringTextComponent(MythriaConst.ALREADY_PERK), Util.DUMMY_UUID);
            return; // Already Attribute;
        }
        if (!pa.hasRequiredAttribute(p)) {
            player.sendMessage(new StringTextComponent(MythriaConst.NOT_HAVE_REQUIRED_PERK), Util.DUMMY_UUID);
            return; // Not have required;
        }
        if (p.getBirthDay().getYearsFromCurrent() < pa.getMinimumAge()) {
            player.sendMessage(new StringTextComponent(MythriaConst.TOO_YOUNG), Util.DUMMY_UUID);
            return; // Too young;
        }

        if (!pa.hasRequiredSkills(p)) {
            player.sendMessage(new StringTextComponent(MythriaConst.NOT_ENOUGH_LEVEL), Util.DUMMY_UUID);
            return; // Level too low;
        }

        for (Map.Entry<Attribute, Integer> e : pa.getRequiredAttributes().entrySet()) {
            if (p.getAttributeLevel(e.getKey()) < e.getValue()) {
                player.sendMessage(new StringTextComponent(MythriaConst.NOT_ENOUGH_ATTRIBUTE_INVESTMENTS), Util.DUMMY_UUID);
                return;
            }
        }

        if (!pa.hasRequiredFavor(p)) {
            player.sendMessage(new StringTextComponent(MythriaConst.NOT_ENOUGH_FAVOR), Util.DUMMY_UUID);
            return;
        }

        if (pa.isExcluded(p)) {
            player.sendMessage(new StringTextComponent(MythriaConst.PERK_EXCLUDED), Util.DUMMY_UUID);
            return;
        }

        player.sendMessage(new StringTextComponent(String.format(MythriaConst.PERK_OBTAINED, pa.getDisplayName())), Util.DUMMY_UUID);
        addPerk(player, pa, p);
    }

    public static void addPerk(PlayerEntity player, Perk pa, Profile p) {
        p.addPerk(pa);
    }

    public static Perk getPerk(final String s) {
        return MythriaRegistries.PERKS.getValue(new ResourceLocation(s));
    }

    public static List<Perk> getPerksByType(PerkType type) {
        List<Perk> perks = new ArrayList<>();
        for (Perk perk : MythriaRegistries.PERKS.getValues()) {
            if (perk.getType().equals(type)) perks.add(perk);
        }
        return perks;
    }

    @SubscribeEvent
    public static void onRegisterPerks(RegistryEvent.Register<Perk> event) {
        for (PerkType type : PerkType.values()) {
            for (Perk perk : type.getRegistry().getPerks(type)) {
                event.getRegistry().register(perk);
            }
        }
    }

    public static void onTagsUpdated() {
        for (Perk perk : MythriaRegistries.PERKS.getValues()) {
            perk.onTagsUpdated();
        }
    }

    public static boolean requiresPerk(Item i) {
        return MaterialManager.PERKS_FOR_CRAFTING.containsKey(i);
    }
}
