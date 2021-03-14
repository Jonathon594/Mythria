package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Genetic.Gene.*;
import me.Jonathon594.Mythria.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Managers.GeneSerializers;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinParts;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genetic {
    private GeneticType type;
    private DoubleStatGene healthGene;
    private DoubleStatGene staminaGene;
    private DoubleStatGene speedGene;
    private DoubleStatGene weightGene;
    private DoubleStatGene intelligenceGene;
    private DoubleStatGene manaGene;
    private DoubleStatGene manaRegenGene;
    private DoubleStatGene temperature;
    private DoubleStatGene genderBias;
    private IntStatGene lifespanGene;
    private NutritionGene nutrition;

    private SkinPartGene hair;
    private SkinPartGene eyes;
    private SkinPartGene skin;

    private List<Gene> extraGenes = new ArrayList<>();

    public Genetic(GeneticType type, double health, double stamina, double speed,
                   double weight, double intelligence, double mana, int lifespan, double temperature, double manaRegen,
                   Map<Consumable.Nutrition, Integer> requiredNutrition) {
        this.type = type;
        this.healthGene = new DoubleStatGene(Gene.GeneType.HEALTH, health);
        this.staminaGene = new DoubleStatGene(Gene.GeneType.STAMINA, stamina);
        this.speedGene = new DoubleStatGene(Gene.GeneType.SPEED, speed);
        this.weightGene = new DoubleStatGene(Gene.GeneType.WEIGHT, weight);
        this.intelligenceGene = new DoubleStatGene(Gene.GeneType.INTELLIGENCE, intelligence);
        this.manaGene = new DoubleStatGene(Gene.GeneType.MANA, mana);
        this.manaRegenGene = new DoubleStatGene(Gene.GeneType.MANA_REGEN, manaRegen);
        this.temperature = new DoubleStatGene(Gene.GeneType.TEMPERATURE, temperature);
        this.genderBias = new DoubleStatGene(Gene.GeneType.GENDER_BIAS, 0.5);
        this.lifespanGene = new IntStatGene(Gene.GeneType.LIFESPAN, lifespan);
        this.nutrition = new NutritionGene(new HashMap<>(requiredNutrition));

        hair = new SkinPartGene(SkinParts.HUMAN_HAIR_BLOND, Gene.GeneType.HAIR);
        eyes = new SkinPartGene(SkinParts.HUMAN_EYES_BLUE, Gene.GeneType.EYES);
        skin = new SkinPartGene(SkinParts.HUMAN_SKIN_WHITE, Gene.GeneType.SKIN);
    }

    public Genetic withExtraGene(Gene gene) {
        if (!gene.getType().canStack())
            extraGenes.removeIf((g) -> !g.getType().canStack() && g.getType().equals(gene.getType()));
        this.extraGenes.add(gene);
        return this;
    }

    public List<Gene> getExtraGenes() {
        return extraGenes;
    }

    public double getGenderBias() {
        return genderBias.getValue();
    }

    public int getLifeExpectancy() {
        return lifespanGene.getValue();
    }

    public double getBaseStamina() {
        return staminaGene.getValue();
    }

    public double getBaseSpeed() {
        return speedGene.getValue();
    }

    public double getBaseWeight() {
        return weightGene.getValue();
    }

    public double getBaseHealth() {
        return healthGene.getValue();
    }

    public double getBaseXP() {
        return intelligenceGene.getValue();
    }

    public double getBaseMana() {
        return manaGene.getValue();
    }

    public double getBaseManaRegen() {
        return manaRegenGene.getValue();
    }

    public double getIdealTemperature() {
        return temperature.getValue();
    }

    public Genetic setGenderBias(double value) {
        genderBias = new DoubleStatGene(Gene.GeneType.GENDER_BIAS, value);
        return this;
    }

    public List<Ability> getGrantedAbilities() {
        List<Ability> abilities = new ArrayList<>();
        getExtraGenes().forEach((gene -> {
            if (gene instanceof IAbilityGene) {
                Ability ability = ((IAbilityGene) gene).getAbility();
                if (!abilities.contains(ability)) abilities.add(ability);
            }
        }));
        return abilities;
    }

    public boolean isImmune(DamageSource damageSource) {
        for (Gene gene : getExtraGenes()) {
            if (!(gene instanceof ImmunityGene)) continue;
            for (String immunity : ((ImmunityGene) gene).getDamageSources()) {
                if (immunity.equals(damageSource.damageType)) return true;
            }
        }
        return false;
    }

    public List<EntityType> getEntityRelationships(EntityAttitudeGene.Attitude attitude) {
        List<EntityType> relations = new ArrayList<>();
        getExtraGenes().forEach(gene -> {
            if (gene instanceof EntityAttitudeGene) {
                EntityAttitudeGene entityAttitudeGene = (EntityAttitudeGene) gene;
                if (entityAttitudeGene.getAttitude().equals(attitude)) {
                    entityAttitudeGene.getEntityTypes().forEach(entityType -> {
                        relations.add(entityType);
                    });
                }
            }
        });
        return relations;
    }

    public List<SpecialAbility> getSpecialAbilities() {
        List<SpecialAbility> abilities = new ArrayList<>();
        getExtraGenes().forEach((gene -> {
            if (gene instanceof ISpecialAbilitiesGene) {
                ((ISpecialAbilitiesGene) gene).getSpecialAbilities().forEach((specialAbility ->
                {
                    if (!abilities.contains(specialAbility)) abilities.add(specialAbility);
                }));
            }
        }));
        return abilities;
    }

    public boolean isSlotLocked(EquipmentSlotType slotType) {
        for (Gene gene : extraGenes) {
            if (!(gene instanceof ISlotLockingGene)) continue;
            if (((ISlotLockingGene) gene).getLockedSlots().contains(slotType)) return true;
        }
        return false;
    }

    public CompoundNBT toNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Type", type.getRegistryName().toString());
        compoundNBT.put("Health", healthGene.toNBT(false));
        compoundNBT.put("Stamina", staminaGene.toNBT(false));
        compoundNBT.put("Speed", speedGene.toNBT(false));
        compoundNBT.put("Weight", weightGene.toNBT(false));
        compoundNBT.put("Intelligence", intelligenceGene.toNBT(false));
        compoundNBT.put("Mana", manaGene.toNBT(false));
        compoundNBT.put("ManaRegen", manaRegenGene.toNBT(false));
        compoundNBT.put("Temperature", temperature.toNBT(false));
        compoundNBT.put("GenderBias", genderBias.toNBT(false));
        compoundNBT.put("Lifespan", lifespanGene.toNBT(false));
        compoundNBT.put("Nutrition", nutrition.toNBT(false));

        compoundNBT.put("Hair", hair.toNBT(false));
        compoundNBT.put("Eyes", eyes.toNBT(false));
        compoundNBT.put("Skin", skin.toNBT(false));

        ListNBT list = new ListNBT();
        for (Gene gene : extraGenes) {
            list.add(gene.toNBT(true));
        }
        compoundNBT.put("ExtraGenes", list);
        return compoundNBT;
    }

    public void fromNBT(CompoundNBT compound) {
        type = MythriaRegistries.GENETICS.getValue(new ResourceLocation(compound.getString("Type")));
        healthGene = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("Health"));
        staminaGene = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("Stamina"));
        speedGene = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("Speed"));
        weightGene = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("Weight"));
        intelligenceGene = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("Intelligence"));
        manaGene = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("Mana"));
        manaRegenGene = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("ManaRegen"));
        temperature = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("Temperature"));
        genderBias = GeneSerializers.DOUBLE_STAT.deserialize(compound.getCompound("GenderBias"));
        lifespanGene = GeneSerializers.INT_STAT.deserialize(compound.getCompound("Lifespan"));
        nutrition = GeneSerializers.NUTRITION.deserialize(compound.getCompound("Nutrition"));

        hair = GeneSerializers.SKIN_PART.deserialize(compound.getCompound("Hair"));
        eyes = GeneSerializers.SKIN_PART.deserialize(compound.getCompound("Eyes"));
        skin = GeneSerializers.SKIN_PART.deserialize(compound.getCompound("Skin"));

        ListNBT list = compound.getList("ExtraGenes", 10);
        for (INBT inbt : list) {
            CompoundNBT compoundNBT = (CompoundNBT) inbt;
            GeneSerializer serializer = MythriaRegistries.GENE_SERIALIZERS
                    .getValue(new ResourceLocation(compoundNBT.getString("Serializer")));
            extraGenes.add(serializer.deserialize(compoundNBT));
        }
    }

    public void setHair(SkinPartGene hair) {
        this.hair = hair;
    }

    public SkinPartGene getEyes() {
        return eyes;
    }

    public Genetic setEyes(SkinPartGene eyes) {
        this.eyes = eyes;
        return this;
    }

    public SkinPartGene getSkin() {
        return skin;
    }

    public Genetic setSkin(SkinPartGene skin) {
        this.skin = skin;
        return this;
    }

    public SkinPartGene getHair() {
        return hair;
    }

    public GeneticType getType() {
        return type;
    }

    public NutritionGene getNutrition() {
        return nutrition;
    }
}
