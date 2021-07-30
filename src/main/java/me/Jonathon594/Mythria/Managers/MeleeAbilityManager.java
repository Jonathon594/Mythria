package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;

public class MeleeAbilityManager {
    public static MeleeCombatManager getCombatManager(Item item) {
        if (MythriaUtil.isSword(item)) {
            return MeleeCombatManager.SWORD_MANAGER;
        }
        if (MythriaUtil.isAxe(item)) {
            return MeleeCombatManager.AXE_MANAGER;
        }

        if (item.equals(Items.AIR)) {
            return MeleeCombatManager.UNARMED_MANAGER;
        }
        if (item instanceof ShieldItem) {
            return MeleeCombatManager.SHIELD_MANAGER;
        }
        if (MythriaUtil.isDagger(item)) {
            return MeleeCombatManager.DAGGER_MANAGER;
        }
        return null;
    }

    public static void disarm(LivingEntity entity, Hand hand) {
        ItemStack is = entity.getHeldItem(hand).copy();
        entity.setHeldItem(hand, new ItemStack(Items.AIR));
        ItemEntity item = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), is);
        item.setPickupDelay(30);
        entity.world.addEntity(item);
        entity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
    }

    public static void disarmor(LivingEntity entity, EquipmentSlotType slot) {
        ItemStack is = entity.getItemStackFromSlot(slot).copy();
        entity.setItemStackToSlot(slot, new ItemStack(Items.AIR));
        ItemEntity item = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), is);
        item.setPickupDelay(80);
        entity.world.addEntity(item);
        entity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
    }
}
