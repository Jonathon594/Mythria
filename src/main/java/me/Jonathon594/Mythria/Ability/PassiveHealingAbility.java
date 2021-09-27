package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Managers.StatManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PassiveHealingAbility extends PassiveAbility {
    private final double cost;
    private final Consumable consumable;
    private final float maxHealing;

    public PassiveHealingAbility(String name, double cost, Consumable consumable, float maxHealing) {
        super(name);
        this.cost = cost;
        this.consumable = consumable;
        this.maxHealing = maxHealing;
    }

    @Override
    protected void onPassiveTick(AbilityInstance abilityInstance) {
        PlayerEntity player = abilityInstance.getOwner();
        if (player.getShouldBeDead()) return;
        World world = player.world;
        if (world.isRemote) return;
        if (world.getGameTime() % 20 == 0) {
            float maxHealth = player.getMaxHealth();
            float currentHealth = player.getHealth();
            float healing = MathHelper.clamp(maxHealth - currentHealth, 0, maxHealing);
            if (healing == 0) return;
            Profile profile = ProfileProvider.getProfile(player);
            double mana = profile.getConsumable(consumable);
            double manaCost = healing * cost;
            if (mana < manaCost) {
                double percent = mana / manaCost;
                healing *= percent;
                manaCost = mana;
            }
            profile.addConsumable(consumable, -manaCost);
            player.setHealth(currentHealth + healing);
        }
    }
}
