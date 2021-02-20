package me.Jonathon594.Mythria.DataTypes.HealthConditions;

import me.Jonathon594.Mythria.DataTypes.HealthCondition;
import me.Jonathon594.Mythria.DataTypes.HealthConditionType;
import net.minecraft.entity.player.ServerPlayerEntity;

public abstract class AbstractBrokenBone extends HealthCondition {
    public AbstractBrokenBone(HealthConditionType type) {
        super(type);
    }

    @Override
    protected void uniqueUpdate(ServerPlayerEntity player) {
    }
}
