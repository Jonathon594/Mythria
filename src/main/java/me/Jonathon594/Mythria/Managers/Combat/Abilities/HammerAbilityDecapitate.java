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

public class HammerAbilityDecapitate implements ICombatAbility {
    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.HAMMER_ABILITY_DECAPITATE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 3;
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
    }

    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        if (target instanceof LivingEntity) {
            LivingEntity LivingEntity = (LivingEntity) target;
            ItemStack helmet = LivingEntity.getItemStackFromSlot(EquipmentSlotType.HEAD);
            if (helmet.isEmpty()) {
                preEvent.setDamage(preEvent.getDamage() * 8);
                preEvent.setForceCrit(true);
            } else {
                helmet.damageItem((int) (preEvent.getDamage() + 15), LivingEntity,
                        (playerEntity) -> playerEntity.sendBreakAnimation(new ItemUseContext(player, Hand.MAIN_HAND, null).getHand()));
                LivingEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 80, 2, false, false));
                target.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f);

                if (LivingEntity instanceof PlayerEntity) {
                    Profile targetProfile = ProfileProvider.getProfile((PlayerEntity) LivingEntity);
                    targetProfile.setConsumable(Consumable.TORPOR, targetProfile.getConsumables().get(Consumable.TORPOR) + preEvent.getDamage());
                }
            }
        }
    }
}
