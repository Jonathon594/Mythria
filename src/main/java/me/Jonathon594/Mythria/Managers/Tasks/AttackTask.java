package me.Jonathon594.Mythria.Managers.Tasks;

import me.Jonathon594.Mythria.Enum.EnumAttackType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;

public class AttackTask extends AbstractTask {
    private PlayerEntity player;
    private LivingEntity target;
    private Hand hand;
    private EnumAttackType type;
    private EquipmentSlotType slotHit;

    public AttackTask(int delay, PlayerEntity player, LivingEntity target, Hand hand, EnumAttackType type, EquipmentSlotType slotHit) {
        super(delay);
        this.player = player;
        this.target = target;
        this.type = type;
        this.hand = hand;
        this.slotHit = slotHit;
    }

    @Override
    public void execute() {
        //CombatKeyManager.attackEntityServer(player, target, hand, type, slotHit, true, true);
        //PacketUtil.swingPlayerArm(player, hand); todo
    }
}
