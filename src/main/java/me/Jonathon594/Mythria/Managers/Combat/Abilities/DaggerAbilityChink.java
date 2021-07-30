package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.MeleeAbilityManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class DaggerAbilityChink implements ICombatAbility {
    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        preEvent.setDamage(0);
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
        if (target instanceof LivingEntity) {
            LivingEntity LivingEntity = (LivingEntity) target;
            EquipmentSlotType slot = EquipmentSlotType.CHEST;
            ItemStack armor = LivingEntity.getItemStackFromSlot(slot);
            if (armor.isEmpty()) {
                slot = EquipmentSlotType.LEGS;
                armor = LivingEntity.getItemStackFromSlot(slot);

                if (armor.isEmpty()) {
                    return;
                }
            }

            if (Math.random() < 0.25) {
                MeleeAbilityManager.disarmor(LivingEntity, slot);
            }
        }
    }

    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.DAGGER_ABILITY_CHINK;
    }

    @Override
    public double getStaminaMultiplier() {
        return 2;
    }
}
