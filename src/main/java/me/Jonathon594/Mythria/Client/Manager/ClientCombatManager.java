package me.Jonathon594.Mythria.Client.Manager;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;

public class ClientCombatManager {
    private static final Minecraft mc = Minecraft.getInstance();

    public static EnumAttackType getAttackType() {
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            return EnumAttackType.FORWARD;
        } else if (mc.gameSettings.keyBindBack.isKeyDown()) {
            return EnumAttackType.BACKWARD;
        } else if (mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
            return EnumAttackType.SIDE;
        } else {
            return EnumAttackType.BASIC;
        }
    }

    public static void meleeAttack(EntityRayTraceResult rayTraceResult, Hand hand, AttackClass attackClass) {
        PlayerEntity player = mc.player;
        Profile profile = ProfileProvider.getProfile(player);
        float cooledAttackStrength = player.getCooledAttackStrength(0);
        if (cooledAttackStrength != 1.0) return;
        boolean blocking = player.isActiveItemStackBlocking();
        if (blocking && (!profile.hasFlag(AttributeFlag.SHIELD_ABILITY_BASH)))
            return;
        double swingCost = 10;
        if (profile.getConsumables().get(Consumable.STAMINA) < swingCost) return;
        if (blocking) player.resetActiveHand();

//            MeleeCombatManager manager = MeleeAbilityManager.getCombatManager(player.getHeldItem(hand).getItem());
//            Hand other = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
//            MeleeCombatManager omanager = MeleeAbilityManager.getCombatManager(player.getHeldItem(other).getItem());

//            if (manager != null && omanager != null) {
//                ICombatAbility ability = manager.getAbility(type, manager == omanager, player.isActiveItemStackBlocking());
//            }

        player.swingArm(hand);
        sendPacket(rayTraceResult != null ? rayTraceResult.getEntity() : null, hand, getAttackType(), attackClass);
        return;
    }

    private static void sendPacket(final Entity entityHit, final Hand hand, EnumAttackType type, AttackClass attackClass) {
        MythriaPacketHandler.sendToServer(new CPacketAttack(entityHit != null ? entityHit.getEntityId() : -1, hand.ordinal(), type, attackClass));
    }
}
