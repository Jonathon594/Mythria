package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.Enum.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class AttributeManager {
    public static void spendAttribute(PlayerEntity player, Attribute attribute) {
        Profile profile = ProfileProvider.getProfile(player);
        if (profile.getSpendableAttributePoints() <= 0) {
            player.sendMessage(new StringTextComponent(ColorConst.MAIN_COLOR + MythriaConst.NOT_ENOUGH_ATTRIBUTE), Util.DUMMY_UUID);
            return;
        }

        int attributeLevel = profile.getAttributeLevel(attribute);
        if (attributeLevel >= 25) {
            player.sendMessage(new StringTextComponent(ColorConst.MAIN_COLOR + MythriaConst.ATTRIBUTE_MAXED), Util.DUMMY_UUID);
            return;
        }

        profile.setAttributeLevel(attribute, attributeLevel + 1);
        profile.sendDataPacket();
        StatManager.UpdateMaxHealth(profile, player);
        StatManager.UpdateSpeed(profile, player);
    }
}
