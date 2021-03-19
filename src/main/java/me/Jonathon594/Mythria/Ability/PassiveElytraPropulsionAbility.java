package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Managers.StatManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public abstract class PassiveElytraPropulsionAbility extends PassiveTickAbility {
    public PassiveElytraPropulsionAbility(String name) {
        super(name);
    }

    @Override
    protected void onPassiveTick(AbilityInstance abilityInstance) {
        PlayerEntity player = abilityInstance.getOwner();
        Profile profile = ProfileProvider.getProfile(player);
        if (player.isElytraFlying() && player.isSprinting()) {
            if (profile.getConsumable(getConsumable()) >= getCost()) {
                if (player.world.isRemote) {
                    float rotationYaw = player.rotationYaw;
                    float rotationPitch = MathHelper.clamp(player.rotationPitch, -10, 90);
                    Vector3f propulsion = Vector3f.ZP.copy();
                    propulsion.transform(Vector3f.XP.rotationDegrees(rotationPitch));
                    propulsion.transform(Vector3f.YN.rotationDegrees(rotationYaw));
                    propulsion.mul(getSpeed(player, profile));
                    player.addVelocity(propulsion.getX(), propulsion.getY(), propulsion.getZ());
                } else {
                    StatManager.chargeConsumable(player, getCost(), getConsumable());
                }
            } else {
                player.setSprinting(false);
            }
        }
    }

    protected abstract Consumable getConsumable();

    protected abstract double getCost();

    protected abstract float getSpeed(PlayerEntity player, Profile profile);
}
