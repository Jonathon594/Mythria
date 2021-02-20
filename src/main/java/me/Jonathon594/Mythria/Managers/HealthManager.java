package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.CureCondition;
import me.Jonathon594.Mythria.DataTypes.HealthConditionType;
import me.Jonathon594.Mythria.DataTypes.HealthConditions.*;
import me.Jonathon594.Mythria.DataTypes.ObtainCondition;
import me.Jonathon594.Mythria.Enum.AnatomySlot;
import me.Jonathon594.Mythria.Enum.ObtainConditionType;
import me.Jonathon594.Mythria.Enum.StatType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public class HealthManager {
    private static final ArrayList<HealthConditionType> conditionTypes = new ArrayList<>();

    public static void init() {
        new HealthConditionType("broken_ankle", "Broken Ankle", 6, 2, BrokenAnkleCondition.class, CureCondition.WRAP)
                .addValidSlot(AnatomySlot.LEFT_ANKLE_BONE)
                .addValidSlot(AnatomySlot.RIGHT_ANKLE_BONE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 4, 0.1, null))
                .addModifier(StatType.MAX_WEIGHT, -20)
                .addModifier(StatType.MAX_HEALTH, -2);

        new HealthConditionType("broken_leg", "Broken Leg", 20, 5, BrokenLegCondition.class, CureCondition.SPLINT)
                .addValidSlot(AnatomySlot.LEFT_LEG_BONE)
                .addValidSlot(AnatomySlot.RIGHT_LEG_BONE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 6, 0.2, null))
                .addModifier(StatType.MAX_WEIGHT, -50)
                .addModifier(StatType.MAX_HEALTH, -5);

        new HealthConditionType("broken_arm", "Broken Arm", 18, 3, BrokenArmCondition.class, CureCondition.WRAP)
                .addValidSlot(AnatomySlot.LEFT_ARM_BONE)
                .addValidSlot(AnatomySlot.RIGHT_ARM_BONE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 6, 0.2, null))
                .addModifier(StatType.MAX_WEIGHT, -50)
                .addModifier(StatType.MAX_HEALTH, -5);

        new HealthConditionType("broken_wrist", "Broken Wrist", 3, 1, BrokenWristCondition.class, CureCondition.WRAP)
                .addValidSlot(AnatomySlot.RIGHT_WRIST_BONE)
                .addValidSlot(AnatomySlot.LEFT_WRIST_BONE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 4, 0.05, null))
                .addModifier(StatType.MAX_WEIGHT, -20)
                .addModifier(StatType.MAX_HEALTH, -2);

        new HealthConditionType("stomach_virus", "Stomach Virus", 1, 0, StomachVirusCondition.class, CureCondition.SLIME)
                .addValidSlot(AnatomySlot.RIGHT_WRIST_BONE)
                .addValidSlot(AnatomySlot.LEFT_WRIST_BONE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.ROTTEN_MEAT, 0, 1.0, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.RAW_CHICKEN, 0, 0.95, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.RAW_FISH, 0, 0.10, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.RAW_RED_MEAT, 0, 0.05, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.RAW_FISH, 0, 0.01, null))
                .addModifier(StatType.MAX_SPEED, -0.01)
                .addModifier(StatType.MAX_STAMINA, -50);

        new HealthConditionType("severed_tendon", "Severed Tendon", 20, 17, BrokenLegCondition.class, CureCondition.WRAP)
                .addValidSlot(AnatomySlot.LEFT_LEG_TENDON)
                .addValidSlot(AnatomySlot.RIGHT_LEG_TENDON)
                .addModifier(StatType.MAX_WEIGHT, -50)
                .addModifier(StatType.MAX_SPEED, -0.05);
    }

    public static void addCondition(HealthConditionType healthConditionType) {
        conditionTypes.add(healthConditionType);
    }

    public static ArrayList<HealthConditionType> getHealthConditions() {
        return conditionTypes;
    }

    public static HealthConditionType getHealthCondition(String s) {
        for (HealthConditionType type : conditionTypes) {
            if (type.getName().equalsIgnoreCase(s)) {
                return type;
            }
        }
        return null;
    }

    public static void curePlayer(PlayerEntity player) {
        ProfileProvider.getProfile(player).getHealthData().cureAll();
    }
}
