package me.Jonathon594.Mythria.Managers.Combat.Abilities.ParryAbilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.ParryEvent;
import me.Jonathon594.Mythria.Interface.IParryAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Hand;

public class HammerAbilityShatter implements IParryAbility {
    @Override
    public void onTrigger(PlayerEntity player, Profile profile, LivingEntity target, Hand hand, ParryEvent event) {
        if (profile.hasFlag(AttributeFlag.HAMMER_ABILITY_SHATTER)) {
            ItemStack is = target.getHeldItem(event.getHand());
            is.damageItem((int) (event.getDamage() + 15), target,
                    (playerEntity) -> playerEntity.sendBreakAnimation(new ItemUseContext(player, Hand.MAIN_HAND, null).getHand()));

            event.setDamage(0);
        }
    }
}
