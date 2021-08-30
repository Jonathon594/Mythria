package me.Jonathon594.Mythria.Managers.Tasks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;

public class SweepTask extends AbstractTask {
    private final PlayerEntity player;
    private final LivingEntity target;
    private final float damage;
    private final Hand hand;
    private EntityDamageSource damageSource;

    public SweepTask(int delay, PlayerEntity player, LivingEntity target, EntityDamageSource damageSource, float damage, Hand hand) {
        super(delay);
        this.player = player;
        this.target = target;
        this.damageSource = damageSource;
        this.damage = damage;
        this.hand = hand;

        if (hand == Hand.OFF_HAND) this.damageSource = new EntityDamageSource("playeroffhand", player);
    }

    @Override
    public void execute() {
        //CombatKeyManager.attackEntityServer(player, target, hand, EnumAttackType.BASIC, null, true, true);
        //CombatKeyManager.triggerSweep(player, target, damageSource, damage / 3.0f);
        //PacketUtil.swingPlayerArm(player, hand);
    }
}
