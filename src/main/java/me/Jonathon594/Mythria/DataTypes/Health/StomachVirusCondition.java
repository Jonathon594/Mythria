package me.Jonathon594.Mythria.DataTypes.Health;

import me.Jonathon594.Mythria.DataTypes.HealthConditionType;

public class StomachVirusCondition extends AbstractUpsetStomach {
    public StomachVirusCondition(HealthConditionType type) {
        super(type);
    }

    @Override
    protected double getVomitChance() {
        return 0.001;
    }

    @Override
    protected double getCrampChance() {
        return 0.0001;
    }
}
