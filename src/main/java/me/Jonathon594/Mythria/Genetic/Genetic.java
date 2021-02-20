package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class Genetic extends ForgeRegistryEntry<Genetic> {
    private final String displayName;
    private final double baseStamina;
    private final double baseSpeed;
    private final double baseWeight;
    private final double baseHealth;
    private final double baseXP;
    private final double baseMana;
    private final double baseManaRegen;
    private final int lifeExpectancy;
    private final int fertileCycleLength;
    private SpawnPos spawnPos;
    private final double idealTemperature;
    private List<EntityType<? extends CreatureEntity>> fleeingEntities = new ArrayList<>();

    public Genetic setBonusUnarmedDamage(float bonusUnarmedDamage) {
        this.bonusUnarmedDamage = bonusUnarmedDamage;
        return this;
    }

    public Genetic addFleeingEntity(EntityType<? extends CreatureEntity> type) {
        if (!this.fleeingEntities.contains(type)) fleeingEntities.add(type);
        return this;
    }

    private float bonusUnarmedDamage = 0.0f;
    private List<EntityType> mobTruces = new ArrayList<>();

    public Genetic setWaterNeeded(boolean waterNeeded) {
        this.waterNeeded = waterNeeded;
        return this;
    }

    private boolean waterNeeded = true;

    public List<String> getDamageImmunity() {
        return damageImmunity;
    }

    private List<String> damageImmunity = new ArrayList<>();

    public Genetic setCanGlide(boolean canGlide) {
        this.canGlide = canGlide;
        return this;
    }

    private boolean canGlide;
    private RegistryKey<World> spawnDimension = World.OVERWORLD;
    private boolean locked = false;
    private SkinPart.Type specialSkinPartType = null;
    private ArrayList<EquipmentSlotType> lockedEquipSlots = new ArrayList<>();
    private double genderBias = 0.5;

    public ArrayList<Ability> getGrantedAbilities() {
        return grantedAbilities;
    }

    private ArrayList<Ability> grantedAbilities = new ArrayList<>();

    public Genetic(final String name, String displayName, double idealTemperature, double baseStamina, double baseSpeed, double baseWeight, double baseHealth, double baseXP, double baseMana, double baseManaRegen, int lifeExpectancy,
                   int fertileCycleLength, SpawnPos spawnPos) {
        this.displayName = displayName;
        this.baseStamina = baseStamina;
        this.baseSpeed = baseSpeed;
        this.baseWeight = baseWeight;
        this.baseHealth = baseHealth;
        this.baseXP = baseXP;
        this.baseMana = baseMana;
        this.baseManaRegen = baseManaRegen;
        this.lifeExpectancy = lifeExpectancy;
        this.fertileCycleLength = fertileCycleLength;
        this.spawnPos = spawnPos;
        this.idealTemperature = idealTemperature;
        setRegistryName(new MythriaResourceLocation(name));
    }

    public Genetic addGrantedAbility(Ability ability) {
        grantedAbilities.add(ability);
        return this;
    }

    public Genetic addImmunity(DamageSource... damageSources) {
        for (DamageSource damageSource : damageSources) {
            String damageType = damageSource.damageType;
            if (damageImmunity.contains(damageType)) continue;
            damageImmunity.add(damageType);
        }
        return this;
    }

    public Genetic addLockedEquipSlot(EquipmentSlotType type) {
        lockedEquipSlots.add(type);
        return this;
    }

    public boolean isLocked() {
        return locked;
    }

    public Genetic setLocked() {
        this.locked = true;
        return this;
    }

    public double getBaseStamina() {
        return baseStamina;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public double getBaseWeight() {
        return baseWeight;
    }

    public double getBaseHealth() {
        return baseHealth;
    }

    public double getBaseXP() {
        return baseXP;
    }

    public double getBaseMana() {
        return baseMana;
    }

    public double getBaseManaRegen() {
        return baseManaRegen;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public int getFertileCycleLength() {
        return fertileCycleLength;
    }

    public double getIdealTemperature() {
        return idealTemperature;
    }

    public SpawnPos getSpawnPos() {
        return spawnPos;
    }

    public RegistryKey<World> getSpawnDimension() {
        return spawnDimension;
    }

    public Genetic setSpawnDimension(RegistryKey<World> spawnDimension) {
        this.spawnDimension = spawnDimension;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SkinPart.Type getSpecialSkinPartType() {
        return specialSkinPartType;
    }

    public Genetic setSpecialSkinPartType(SkinPart.Type specialSkinPartType) {
        this.specialSkinPartType = specialSkinPartType;
        return this;
    }

    public boolean isSlotOpen(EquipmentSlotType slotType) {
        if (lockedEquipSlots.contains(slotType)) return false;
        return true;
    }

    public boolean isGlidingAllowed() {
        return canGlide;
    }

    public double getGenderBias() {
        return genderBias;
    }

    public Genetic setGenderBias(double genderBias) {
        this.genderBias = genderBias;
        return this;
    }

    public boolean isImmune(DamageSource source) {
        for (String immunity : damageImmunity) {
            if (immunity.equalsIgnoreCase(source.damageType)) return true;
        }
        return false;
    }

    public Genetic addMobTruce(EntityType... types) {
        for (EntityType type : types) {
            if (!mobTruces.contains(type)) mobTruces.add(type);
        }
        return this;
    }

    public boolean isInTruceWith(EntityType type) {
        return mobTruces.contains(type);
    }

    public boolean isWaterNeeded() {
        return waterNeeded;
    }

    public float getBonusUnarmedDamage() {
        return bonusUnarmedDamage;
    }

    public List<EntityType<? extends CreatureEntity>> getFleeingEntities() {
        return fleeingEntities;
    }
}
