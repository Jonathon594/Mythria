package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.CombatPhase;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.IWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MeleeAbilityManager {
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

    public static MeleeCombatManager getCombatManager(Item item) {
        if (item.equals(Items.AIR)) return MeleeCombatManager.UNARMED_MANAGER;
        if (item instanceof IWeapon) return ((IWeapon) item).getCombatManager();
        return null;
    }

    @SubscribeEvent
    public static void onCombatEventPre(CombatEvent.Pre event) {
        onCombat(CombatPhase.PRE, event);
    }

    @SubscribeEvent
    public static void onCombatEventPost(CombatEvent.Post event) {
        onCombat(CombatPhase.POST, event);
    }

    private static void onCombat(CombatPhase phase, CombatEvent event) {
        Item item = event.getWeapon().getItem();
        Item otherItem = event.getOtherWeapon().getItem();
        PlayerEntity player = event.getPlayer();
        Profile profile = ProfileProvider.getProfile(player);
        Entity target = event.getTarget();
        MeleeCombatManager manager = getCombatManager(item);
        if(manager == null) return;

        boolean dual = false;
        MeleeCombatManager otherManager = getCombatManager(otherItem);
        if(otherManager != null && otherManager == manager) dual = true;

        MythicSkills skill = getRelatedSkill(item);
        if(skill == null) return;

        manager.onCombat(player, profile, target, phase, event, dual, skill);
    }

    private static MythicSkills getRelatedSkill(Item item) {
        if(item.equals(Items.AIR)) return MythicSkills.UNARMED;
        if(item instanceof IWeapon) return ((IWeapon) item).getUsageSkill();
        return null;
    }
}
