package me.Jonathon594.Mythria.Managers.Tasks;

import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Managers.CombatManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;

public class AttackTask extends AbstractTask {
    private final PlayerEntity player;
    private final LivingEntity target;
    private final Hand hand;
    private final EnumAttackType type;
    private final AttackClass attackClass;

    public AttackTask(int delay, PlayerEntity player, LivingEntity target, Hand hand, EnumAttackType type, AttackClass attackClass) {
        super(delay);
        this.player = player;
        this.target = target;
        this.type = type;
        this.hand = hand;
        this.attackClass = attackClass;
    }

    @Override
    public void execute() {
        CombatManager.attackEntity(player, target, hand, type, attackClass, true, true);
        player.swingArm(hand);
        player.getAttributeManager().reapplyModifiers(player.getHeldItem(hand).getAttributeModifiers(EquipmentSlotType.MAINHAND));
        player.resetCooldown();
    }
}
