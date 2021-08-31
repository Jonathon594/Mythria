package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Event.ParryEvent;
import me.Jonathon594.Mythria.Interface.IWeapon;
import me.Jonathon594.Mythria.Items.MythriaDaggerItem;
import me.Jonathon594.Mythria.Items.MythriaHammerItem;
import me.Jonathon594.Mythria.Managers.SoundManager;
import me.Jonathon594.Mythria.Util.CombatUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ParryManager {
    public static void onPacket(ServerPlayerEntity player, boolean parry) {
        Profile profile = ProfileProvider.getProfile(player);
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);
        if (parry) {
            if (canParry(player, profile)) {
                mythriaPlayer.setParrying(true);
                SoundManager.playForAllNearby(player, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
            }
        } else {
            if (mythriaPlayer.isParrying()) {
                mythriaPlayer.setParrying(false);
                updateAndResetCooldown(player);
            }
        }
    }

    public static void onPlayerTickServer(ServerPlayerEntity player, Profile profile, MythriaPlayer mythriaPlayer) {
        boolean parrying = mythriaPlayer.isParrying();
        if (!canParry(player, profile) && parrying) mythriaPlayer.setParrying(false);
        if (player.isSwingInProgress && parrying) mythriaPlayer.setParrying(false);

        if (parrying) {
            mythriaPlayer.setTicksParrying(mythriaPlayer.getTicksParrying() + 1);
        } else {
            mythriaPlayer.setTicksParrying(0);
        }
    }

    public static void onReceiveDamage(PlayerEntity player, Profile profile, LivingHurtEvent event, LivingEntity trueSource) {
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);
        if (CombatUtil.canBlockDamage(player, event.getSource()) && mythriaPlayer.isParrying()) {
            float cost = getStaminaCost(event.getAmount());
            double stamina = profile.getConsumables().get(Consumable.STAMINA);
            if (stamina >= cost) {
                Hand hand = Hand.MAIN_HAND;
                if (event.getSource().damageType.equalsIgnoreCase("playeroffhand")) hand = Hand.OFF_HAND;
                ParryEvent parryEvent = new ParryEvent(player, profile, trueSource, hand, event.getAmount() / 2.0f);
                MinecraftForge.EVENT_BUS.post(parryEvent);
                profile.setConsumable(Consumable.STAMINA, stamina - cost);
                event.setAmount(parryEvent.getDamage());
                if (parryEvent.getDamage() == 0) event.setCanceled(true);
                mythriaPlayer.setParrying(false);
            } else {
                profile.setConsumable(Consumable.STAMINA, 0);
                mythriaPlayer.setParrying(false);
            }
            updateAndResetCooldown(player);
        }
    }

    private static boolean canParry(ServerPlayerEntity player, Profile profile) {
        if (player.getCooledAttackStrength(0) != 1.0) return false;
        if (player.isSwingInProgress) return false;
        if (player.isCreative()) return true;
        Item mainHand = player.getHeldItemMainhand().getItem();
        Item offHand = player.getHeldItemOffhand().getItem();

        boolean canParryMain = canParryWith(mainHand, profile);
        boolean canParryOff = canParryWith(offHand, profile) || (!mainHand.equals(Items.AIR)) && offHand.equals(Items.AIR);

        return canParryMain && canParryOff;
    }

    private static boolean canParryWith(Item item, Profile profile) {
        AttributeFlag flagToParryWith = getFlagToParryWith(item);
        return flagToParryWith != null && profile.hasFlag(flagToParryWith);
    }

    private static AttributeFlag getFlagToParryWith(Item item) {
        if(item.equals(Items.AIR)) return AttributeFlag.UNARMED_ABILITY_PARRY;
        if(item instanceof IWeapon) return ((IWeapon) item).getFlagForParrying();
        return null;
    }

    private static float getStaminaCost(float amount) {
        return amount * 2;
    }

    private static void updateAndResetCooldown(PlayerEntity player) {
        //todo Calculate shorter cooldown
        player.getAttributeManager().reapplyModifiers(player.getHeldItemOffhand().getAttributeModifiers(EquipmentSlotType.MAINHAND));
        player.resetCooldown();
    }
}
