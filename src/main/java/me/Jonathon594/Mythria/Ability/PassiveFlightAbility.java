package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Managers.StatManager;
import net.minecraft.entity.player.PlayerEntity;

public class PassiveFlightAbility extends PassiveAbility {
    private final double cost;
    private final Consumable consumable;
    private final int costInterval;

    public PassiveFlightAbility(String name, double cost, Consumable consumable, int costInterval) {
        super(name);
        this.cost = cost;
        this.consumable = consumable;
        this.costInterval = costInterval;
    }

    @Override
    protected void onPassiveTick(AbilityInstance abilityInstance) {
        PlayerEntity player = abilityInstance.getOwner();
        if (player.isCreative()) return;
        Profile profile = ProfileProvider.getProfile(player);
        if (player.abilities.isFlying) {
            if (!player.world.isRemote) {
                if (costInterval > 0 && player.world.getGameTime() % costInterval == 0)
                    StatManager.chargeConsumable(player, cost, consumable);
            }
        }

        if (profile.getConsumable(consumable) > cost) {
            player.abilities.allowFlying = true;
        } else {
            player.abilities.allowFlying = false;
            player.abilities.isFlying = false;
        }
    }
}
