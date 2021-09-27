package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.entity.player.PlayerEntity;

public class PassiveFaeFlightAbility extends PassiveElytraPropulsionAbility {
    public PassiveFaeFlightAbility(String name) {
        super(name);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    protected Consumable getConsumable() {
        return Consumable.STAMINA;
    }

    @Override
    protected double getCost() {
        return -1;
    }

    @Override
    protected float getSpeed(PlayerEntity player, Profile profile) {
        float boost = 0.03f * ((float) profile.getAttributeLevel(Attribute.STRENGTH) / 25.0f);
        return 0.02f + boost;
    }
}
