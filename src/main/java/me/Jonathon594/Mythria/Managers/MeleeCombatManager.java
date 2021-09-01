package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.BasicAttackAbility;
import me.Jonathon594.Mythria.Managers.Combat.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public abstract class MeleeCombatManager {
    public static final SwordManager SWORD_MANAGER = new SwordManager();
    public static final AxeManager AXE_MANAGER = new AxeManager();
    public static final UnarmedManager UNARMED_MANAGER = new UnarmedManager();
    public static final ShieldManager SHIELD_MANAGER = new ShieldManager();
    public static final DaggerManager DAGGER_MANAGER = new DaggerManager();
    public static final HammerManager HAMMER_MANAGER = new HammerManager();

    protected static final BasicAttackAbility BASIC_ATTACK_ABILITY = new BasicAttackAbility();

    public abstract ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking, AttackClass attackClass);

    public void onCombat(PlayerEntity player, Profile profile, Entity target, CombatPhase phase, CombatEvent event, boolean isDual, MythicSkills skill) {
        EnumAttackType type = event.getType();

        ICombatAbility ability = getAbility(type, isDual, player.isActiveItemStackBlocking(), event.getAttackClass());
        if (phase.equals(CombatPhase.PRE)) {
            CombatEvent.Pre preEvent = (CombatEvent.Pre) event;


            double staminaCost = WeightManager.getWeight(event.getWeapon());
            double currentStamina = profile.getConsumables().get(Consumable.STAMINA);
            AttributeFlag requiredFlag = ability.getRequiredFlag();
            staminaCost *= ability.getStaminaMultiplier();

            if (currentStamina < staminaCost || (requiredFlag != null && !profile.hasFlag(requiredFlag))) {
                event.setFail(true);
                return;
            }

            ability.onCombatPre(player, profile, target, preEvent);

            //MythriaPlayerProvider.getMythriaPlayer(player).addComboHistory(ability.getClass());

            StatManager.chargeConsumable(player, staminaCost, Consumable.STAMINA);
        } else if (phase.equals(CombatPhase.POST)) {
            CombatEvent.Post postEvent = (CombatEvent.Post) event;
            ability.onCombatPost(player, profile, target, postEvent);

            double experience = postEvent.getDamage() * EXPConst.MELEE_DAMAGE;
            profile.addSkillExperience(skill, experience, (ServerPlayerEntity) player, 0);
        }
    }
}
