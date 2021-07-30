package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class DaggerAbilityAssassinate implements ICombatAbility {
    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        if (target instanceof LivingEntity) {
            LivingEntity LivingEntity = (LivingEntity) target;
            ItemStack chest = LivingEntity.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (chest.isEmpty()) {
                preEvent.setDamage(preEvent.getDamage() + 10 + player.fallDistance * 2);
            } else {
                preEvent.setFail(true);
            }
        }
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
    }

    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.DAGGER_ABILITY_ASSASSINATE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 2;
    }
}
