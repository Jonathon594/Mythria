package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;

public class HammerAbilityDent implements ICombatAbility {
    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.HAMMER_ABILITY_DENT;
    }

    @Override
    public double getStaminaMultiplier() {
        return 2;
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
    }

    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        if (target instanceof LivingEntity) {
            LivingEntity LivingEntity = (LivingEntity) target;
            ItemStack armor = null;
            ArrayList<EquipmentSlotType> slotOrder = new ArrayList<>();
            slotOrder.add(EquipmentSlotType.CHEST);
            slotOrder.add(EquipmentSlotType.HEAD);
            slotOrder.add(EquipmentSlotType.LEGS);
            EquipmentSlotType slotPicked = null;
            for (EquipmentSlotType slot : slotOrder) {
                ItemStack itemStackFromSlot = LivingEntity.getItemStackFromSlot(slot);
                if (!itemStackFromSlot.isEmpty()) {
                    armor = itemStackFromSlot;
                    slotPicked = slot;
                    break;
                }
            }
            if (armor == null) {
                preEvent.setDamage(preEvent.getDamage() + 4);
                if (LivingEntity instanceof PlayerEntity) {
                    Profile targetProfile = ProfileProvider.getProfile((PlayerEntity) LivingEntity);
                    targetProfile.setConsumable(Consumable.PAIN, targetProfile.getConsumables().get(Consumable.PAIN) + 4);
                }
            } else {
                armor.damageItem((int) (preEvent.getDamage() + 35), LivingEntity,
                        (playerEntity) -> playerEntity.sendBreakAnimation(new ItemUseContext(player, Hand.MAIN_HAND, null).getHand()));
                if (slotPicked.equals(EquipmentSlotType.HEAD)) {
                    LivingEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 80, 2, false, false));
                }
                target.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            }
        }
    }
}
