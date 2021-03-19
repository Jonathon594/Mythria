package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.AnatomySlot;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Managers.HealthManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class HealthConditionType {
    private final String name;
    private final ArrayList<AnatomySlot> validSlots = new ArrayList<>();
    private final double naturalDuration;
    private final double cureDuration;
    private final CureCondition[] requiredTreatments;
    private final ArrayList<ObtainCondition> obtainConditions = new ArrayList<>();
    private final Class<? extends HealthCondition> conditionClass;
    private final HashMap<StatType, Double> modifiers = new HashMap<>();
    private final String displayName;

    public HealthConditionType(String name, String displayName, double naturalDuration, double cureDuration, Class<? extends HealthCondition> conditionClass, CureCondition... requiredTreatments) {
        this.name = name;
        this.displayName = displayName;
        this.naturalDuration = naturalDuration;
        this.cureDuration = cureDuration;
        this.conditionClass = conditionClass;
        this.requiredTreatments = requiredTreatments;

        HealthManager.addCondition(this);
    }

    public HealthConditionType addModifier(StatType type, double value) {
        modifiers.put(type, value);
        return this;
    }

    public HealthConditionType addObtainCondition(ObtainCondition condition) {
        this.obtainConditions.add(condition);
        return this;
    }

    public HealthConditionType addValidSlot(AnatomySlot slot) {
        this.validSlots.add(slot);
        return this;
    }

    public HealthCondition createNewInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return conditionClass.getConstructor(HealthConditionType.class).newInstance(this);
    }

    public double getCureDuration() {
        return cureDuration;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public double getNaturalDuration() {
        return naturalDuration;
    }

    public ArrayList<ObtainCondition> getObtainConditions() {
        return obtainConditions;
    }

    public CureCondition[] getRequiredTreatments() {
        return requiredTreatments;
    }

    public HashMap<StatType, Double> getStatModifiers() {
        return modifiers;
    }

    public ArrayList<AnatomySlot> getValidSlots() {
        return validSlots;
    }
}
