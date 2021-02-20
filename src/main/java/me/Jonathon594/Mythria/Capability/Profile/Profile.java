package me.Jonathon594.Mythria.Capability.Profile;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.HealthData;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Genetic.Genetics;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Packet.*;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

import java.util.*;

public class Profile implements IProfile {
    private final List<Perk> perks = new ArrayList<>();
    private final List<PerkType> unlockedPerkTypes = new ArrayList<>();
    private final HashMap<MythicSkills, Double> skillLevels = new HashMap<>();
    private final HashMap<Consumable, Double> consumables = new HashMap<>();
    private final HashMap<Consumable.Nutrition, Double> nutrition = new HashMap<>();
    private final HashMap<Consumable.Nutrition, Double> undigested_nutrition = new HashMap<>();
    private final HealthData healthData = new HealthData();
    private final HashMap<Attribute, Integer> attributeValues = new HashMap<>();
    private final HashMap<Deity, Integer> favorLevels = new HashMap<>();
    private final HashMap<StatType, Double> statModifiers = new HashMap<>();
    private final HashMap<SkinPart.Type, SkinPart> skinData = new HashMap<SkinPart.Type, SkinPart>();
    private final Random random;
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private Date birthDay = new Date();
    private int gender = 0;
    private boolean created = false;
    private PlayerEntity player;
    private UUID profileUUID;
    private String ownerName;
    private String dataVersion;
    private boolean pregnant = false;
    private CompoundNBT pregMotherProfileData = new CompoundNBT();
    private CompoundNBT pregFatherProfileData = new CompoundNBT();
    private int pregBabyCount = 0;
    private int pregConceptionData = 0;
    private Long lastDisconnect = 0L;
    private Genetic genetic = Genetics.HUMAN;
    private double bleeding = 0.0;
    private double playerLevelProgressBuffer;
    private ArrayList<Ability> abilities = new ArrayList<>();
    private AbilityHandler abilityHandler = new AbilityHandler();

    public Profile() {
        fillHashMaps();
        random = new Random();
    }

    public void fillHashMaps() {
        for (final MythicSkills sk : MythicSkills.values())
            skillLevels.put(sk, (double) 0);

        for (final Attribute a : Attribute.values())
            attributeValues.put(a, 0);
        for (final Consumable s : Consumable.values())
            consumables.put(s, 0.0);
        for (final Consumable.Nutrition s : Consumable.Nutrition.values())
            nutrition.put(s, 0.0);
        for (final Consumable.Nutrition s : Consumable.Nutrition.values())
            undigested_nutrition.put(s, 0.0);
        for (Deity d : Deity.values()) {
            favorLevels.put(d, 0);
        }
        for (StatType type : StatType.values()) {
            statModifiers.put(type, 1.0);
        }

        for (SkinPart.Type type : SkinPart.Type.values()) {
            List<SkinPart> parts = SkinPartManager.getSkinPartsFor(type, gender, Genetics.HUMAN);
            skinData.put(type, parts.size() == 0 ? null : parts.get(0));
        }
    }

    public void addAbility(Ability ability) {
        if (!abilities.contains(ability)) {
            abilities.add(ability);
            abilityHandler.addAbilityInstance(ability, player);
        }
    }

    public void unlockPerkType(PerkType type) {
        if (!unlockedPerkTypes.contains(type)) unlockedPerkTypes.add(type);
    }

    public boolean isPerkTypeUnlocked(PerkType type) {
        return unlockedPerkTypes.contains(type);
    }

    public void setSkinData(SkinPart.Type type, SkinPart part) {
        skinData.put(type, part);
    }

    public SkinPart getSkinData(SkinPart.Type type) {
        return skinData.get(type);
    }

    public void applyRandomStatModifiers() {
        for (StatType type : StatType.values()) {
            double value = random.nextDouble() + 0.5;
            statModifiers.put(type, value);
        }
    }

    public void clear() {
        perks.clear();
        unlockedPerkTypes.clear();
        fillHashMaps();
        firstName = "";
        middleName = "";
        lastName = "";
        birthDay = new Date();
        gender = 0;
        created = false;
        player = null;
        ownerName = null;
    }

    public void setPregMotherProfileData(CompoundNBT pregMotherProfileData) {
        this.pregMotherProfileData = pregMotherProfileData;
    }

    public void newDay(LivingEntity LivingEntity) {
        handleDeathChance(LivingEntity);
    }

    public void setPregFatherProfileData(CompoundNBT pregFatherProfileData) {
        this.pregFatherProfileData = pregFatherProfileData;
    }

    private void handleDeathChance(LivingEntity LivingEntity) {
        if (!getCreated()) return;
        Genetic primaryGenetic = getGenetic();
        if (primaryGenetic.getLifeExpectancy() == 0) return;
        if (getBirthDay().getYearsFromCurrent() < primaryGenetic.getLifeExpectancy()) return;
        if (hasFlag(AttributeFlag.IMMORTALITY)) return;
        int ageMGD = TimeManager.getCurrentDate().getMGD() - getBirthDay().getMGD();
        final double dpll = ageMGD - (primaryGenetic.getLifeExpectancy() * TimeManager.getDaysPerYear());
        final double deathChance = dpll / TimeManager.getDaysPerYear() / 300D;
        if (random.nextDouble() < deathChance) {
            LivingEntity.setHealth(0);
        }
    }

    public void setPregConceptionData(int pregConceptionData) {
        this.pregConceptionData = pregConceptionData;
    }

    public Genetic getGenetic() {
        return genetic;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(final Date birthDay) {
        this.birthDay = birthDay;
    }

    public boolean hasFlag(final AttributeFlag flag) {
        if (player == null) return false;
        for (final Perk pa : perks) {
            if (pa == null)
                continue;
            if (pa.getAttributeFlags().contains(flag))
                return true;
        }
        return false;
    }

    public void addSkillExperience(final MythicSkills type, double value, final ServerPlayerEntity p, int effectiveLevel) {
        if (type == null)
            return;

        addSingleSkillExperience(type, value, p, effectiveLevel);
    }

    public void addSingleSkillExperience(MythicSkills skill, double xp, ServerPlayerEntity player, int effectiveLevel) {
        if (skill == null)
            return;

        final int level = getSkillLevel(skill);

        xp *= (1.0 + (Math.pow(effectiveLevel, 2) / 1000.0));

        double xpRate = 1.0;

        boolean isAtheist = hasFlag(AttributeFlag.ATHEISM);
        if (skill.isMental()) {
            xpRate += getStat(StatType.LEARN_RATE);
        }
        if (isAtheist) {
            xpRate += 0.25;
        }
        xp *= Math.max(xpRate, 0);
        if (xp <= 0) return;
        int plo = getPlayerLevel();
        skillLevels.put(skill, skillLevels.get(skill) + xp);
        calculateProgressTowardPlayerLevel();
        int pln = getPlayerLevel();
        MythriaPacketHandler.sendTo(new SPacketUpdateExperience(skill, skillLevels.get(skill)), player);
        final int lb = getSkillLevel(skill);
        if (lb > level)
            if (this.player != null) {
                this.player.sendMessage(new StringTextComponent(
                        String.format(MythriaConst.LEVE_UP_SKILL, MythriaUtil.capitalize(skill.name()), "(" + level + "->" + lb + ")")), Util.DUMMY_UUID);
            }

        if (pln > plo) {
            if (this.player != null) {
                this.player.sendMessage(new StringTextComponent(
                        String.format(MythriaConst.PLAYER_LEVEL_UP, pln)), Util.DUMMY_UUID);
            }
        }
    }

    public double getStat(StatType statType) {
        double modifier = 0;
        if (healthData.getStatModifiers().containsKey(statType)) {
            modifier = healthData.getStatModifiers().get(statType);
        }

        for (Consumable.Nutrition nutrition : Consumable.Nutrition.values()) {
            double value = getNutrition(nutrition);
            double prop = (value - 10) / 10.0;
            switch (nutrition) {
                case STARCH:
                    if (statType.equals(StatType.MAX_STAMINA)) modifier += prop * 25;
                    break;
                case FRUIT:
                    if (statType.equals(StatType.MAX_SPEED)) modifier += prop * 0.003;
                    break;
                case VEGETABLE:
                    if (statType.equals(StatType.MAX_WEIGHT)) modifier += prop * 25;
                    break;
                case MEAT:
                    if (statType.equals(StatType.MAX_HEALTH)) modifier += prop * 4;
                    break;
                case DAIRY:
                    if (statType.equals(StatType.MAX_MANA)) modifier += prop * 50;
                    break;
            }
        }

        if (player == null) return 1;

        Profile profile = ProfileProvider.getProfile(player);
        if (!profile.getCreated()) return 1;
        Genetic g = profile.getGenetic();
        switch (statType) {
            case MAX_STAMINA:
                return modifier + g.getBaseStamina() + getAttributeLevel(Attribute.ENDURANCE) * 50 * getStatModifier(statType);
            case MAX_SPEED:
                return modifier + g.getBaseSpeed() + getAttributeLevel(Attribute.AGILITY) * 0.001 * getStatModifier(statType);
            case MAX_WEIGHT:
                return modifier + g.getBaseWeight() + getAttributeLevel(Attribute.STRENGTH) * 12.5 * getStatModifier(statType);
            case MAX_HEALTH:
                return modifier + g.getBaseHealth() + getAttributeLevel(Attribute.VIGOR) * 2.5 * getStatModifier(statType);
            case LEARN_RATE:
                return modifier + (g.getBaseXP() - 1) + (getAttributeLevel(Attribute.INTELLIGENCE) * 0.05) * getStatModifier(statType);
            case HEAT_TOLLERANCE:
                return modifier + 0.25 * getStatModifier(statType) *
                        getAttributeLevel(Attribute.VITALITY) + (player.isPotionActive(Effects.FIRE_RESISTANCE) ? 2.5 : 0);
            case COLD_TOLLERANCE:
                return modifier + 0.125 * getStatModifier(statType) *
                        getAttributeLevel(Attribute.VITALITY) + (player.isPotionActive(Effects.FIRE_RESISTANCE) ? -5 : 0);
            case MAX_MANA:
                return Math.max(modifier + g.getBaseMana() + 50 * getStatModifier(statType)
                        * getAttributeLevel(Attribute.WILLPOWER), 0);
            case MANA_REGEN:
                return modifier + g.getBaseManaRegen() + 0.3 * getStatModifier(statType)
                        * getAttributeLevel(Attribute.WILLPOWER);
        }
        return 1;
    }

    public int getSkillLevel(final MythicSkills type) {
        return MythriaUtil.getLevelForXP(skillLevels.get(type));
    }

    public void addAttribute(final Perk perk) {
        if (perk == null)
            return;
        if (player != null) MythriaUtil.addRecipesFromPerk(player, perk);
        if (perks.contains(perk))
            return;

        perks.add(perk);
        for (PerkType type : perk.getPerkTypeUnlocks()) {
            unlockPerkType(type);
        }
        if (player == null)
            return;
        if (player.world.isRemote)
            return;

        sendDataPacket();
    }

    public void sendDataPacket() {
        if (player != null && !player.world.isRemote) {
            MythriaPacketHandler.sendTo(new SPacketProfileCache(toNBT()), (ServerPlayerEntity) player);
        }
    }

    public void copySkinToMythriaPlayer(MythriaPlayer mythriaPlayer) {
        for (SkinPart.Type type : SkinPart.Type.values()) {
            SkinPart skinData = getSkinData(type);
            mythriaPlayer.setSkinPart(type, skinData == null ? "" : skinData.getResourceLocation().toString());
        }
    }

    public int getPlayerLevel() {
        double totalExperience = getTotalExperience();
        int playerLevel = MythriaUtil.getPlayerLevelForXP(totalExperience);
        return playerLevel;
    }

    public CompoundNBT toNBT() {
        final CompoundNBT comp = new CompoundNBT();
        comp.putString("FirstName", getFirstName());
        comp.putString("MiddleName", getMiddleName());
        comp.putString("LastName", getLastName());
        if (getOwnerName() != null) comp.putString("OwnerName", getOwnerName());
        comp.putInt("Birthday", getBirthDay().getMGD());
        comp.putInt("Gender", getGender());
        comp.putBoolean("Created", getCreated());
        comp.putString("DataVersion", getDataVersion() == null ? "" : getDataVersion());
        comp.putString("Genetic", genetic.getRegistryName().toString());

        CompoundNBT skin = new CompoundNBT();
        for (Map.Entry<SkinPart.Type, SkinPart> entry : skinData.entrySet()) {
            skin.putString(entry.getKey().name(), entry.getValue() == null ? "" : entry.getValue().getRegistryName().toString());
        }

        comp.put("Skin", skin);

        comp.putBoolean("Pregnant", getPregnant());
        comp.put("PregFatherProfile", getPregFatherProfile());
        comp.put("PregMotherProfile", getPregMotherProfile());
        comp.putInt("PregBabyCount", getPregBabyCount());
        comp.putInt("PregConceptionDate", getPregConceptionDate());
        comp.putLong("LastDisconnect", getLastDisconnect());
        comp.put("HealthData", healthData.toNBT());

        if (getProfileUUID() == null)
            setProfileUUID(UUID.randomUUID());
        comp.putString("UUID", getProfileUUID().toString());

        ListNBT perkList = new ListNBT();
        for (final Perk pa : getPlayerSkills()) {
            perkList.add(StringNBT.valueOf(pa.getRegistryName().toString()));
        }
        comp.put("Perks", perkList);

        ListNBT abilityList = new ListNBT();
        for (Ability ability : abilities) {
            abilityList.add(StringNBT.valueOf(ability.getRegistryName().getPath()));
        }
        comp.put("Abilities", abilityList);

        ListNBT perkTypeList = new ListNBT();
        for (final PerkType type : unlockedPerkTypes) {
            perkTypeList.add(StringNBT.valueOf(type.name()));
        }
        comp.put("UnlockedPerkTypes", perkTypeList);

        CompoundNBT skills = new CompoundNBT();
        for (final MythicSkills cs : MythicSkills.values())
            skills.putDouble(cs.name(), getSkillLevels().get(cs));
        comp.put("Skills", skills);

        CompoundNBT attributes = new CompoundNBT();
        for (final Attribute s : Attribute.values()) {
            attributes.putInt(s.name(), attributeValues.get(s));
        }
        comp.put("Attributes", attributes);

        CompoundNBT consumables = new CompoundNBT();
        for (final Consumable s : Consumable.values())
            consumables.putDouble(s.name(), getConsumables().get(s));
        comp.put("Consumables", consumables);

        CompoundNBT nutritionTag = new CompoundNBT();
        CompoundNBT nutritionDigested = new CompoundNBT();
        CompoundNBT nutritionUndigested = new CompoundNBT();
        for (final Consumable.Nutrition s : Consumable.Nutrition.values()) {
            nutritionDigested.putDouble(s.name(), nutrition.get(s));
            nutritionUndigested.putDouble(s.name(), undigested_nutrition.get(s));
        }
        nutritionTag.put("Digested", nutritionDigested);
        nutritionTag.put("Undigested", nutritionUndigested);
        comp.put("Nutrition", nutritionTag);

        CompoundNBT statModifiers = new CompoundNBT();
        for (final StatType s : StatType.values())
            statModifiers.putDouble(s.name(), getStatModifier(s));
        comp.put("StatModifiers", statModifiers);

        CompoundNBT favor = new CompoundNBT();
        for (Map.Entry<Deity, Integer> e : getFavorLevels().entrySet()) {
            favor.putInt(e.getKey().name(), e.getValue());
        }
        comp.put("Favor", favor);
        return comp;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(final PlayerEntity player) {
        this.player = player;
        this.ownerName = player.getUniqueID().toString();

        for (Perk perk : perks) {
            MythriaUtil.addRecipesFromPerk(player, perk);
        }
    }

    public void fromNBT(final CompoundNBT comp) {
        setFirstName(comp.getString("FirstName"));
        setMiddleName(comp.getString("MiddleName"));
        setLastName(comp.getString("LastName"));
        setOwnerName(comp.getString("OwnerName"));
        setBirthDay(new Date(comp.getInt("Birthday")));
        setGender(comp.getInt("Gender"));
        setCreated(comp.getBoolean("Created"));
        setDataVersion(comp.getString("DataVersion"));
        genetic = MythriaRegistries.GENETICS.getValue(new ResourceLocation(comp.getString("Genetic")));

        CompoundNBT skin = comp.getCompound("Skin");
        for (SkinPart.Type type : SkinPart.Type.values()) {
            String partName = skin.getString(type.name());
            if (partName.isEmpty()) continue;
            SkinPart part = SkinPartManager.getSkinPart(new ResourceLocation(partName));
            if (part == null) continue;
            skinData.put(type, part);
        }

        setPregnant(comp.getBoolean("Pregnant"));
        setPregFatherProfileData(comp.getCompound("PregFatherProfile"));
        setPregMotherProfileData(comp.getCompound("PregMotherProfile"));
        setPregBabyCount(comp.getInt("PregBabyCount"));
        setPregConceptionData(comp.getInt("PregConceptionDate"));
        setLastDisconnect(comp.getLong("LastDisconnect"));
        bleeding = comp.getDouble("bleeding");

        healthData.fromNBT(comp.getCompound("HealthData"));

        final String uuids = comp.getString("UUID");
        if (uuids == null || uuids.isEmpty())
            setProfileUUID(UUID.randomUUID());
        else {
            final UUID uuid = UUID.fromString(uuids);
            setProfileUUID(uuid);
        }

        final ListNBT perkList = comp.getList("Perks", 8);
        for (INBT nbt : perkList) {
            String s = nbt.getString();
            final Perk pa = MythriaRegistries.PERKS.getValue(new ResourceLocation(s));
            if (pa != null)
                perks.add(pa);
        }

        final ListNBT abilityList = comp.getList("Abilities", 8);
        for (INBT nbt : abilityList) {
            String s = nbt.getString();
            Ability ability = MythriaRegistries.ABILITIES.getValue(new MythriaResourceLocation(s));
            if (ability != null)
                addAbility(ability);
        }

        final ListNBT perkTypeList = comp.getList("UnlockedPerkTypes", 8);
        for (INBT nbt : perkTypeList) {
            String s = nbt.getString();
            PerkType perkType = PerkType.valueOf(s);
            if (perkType != null)
                unlockPerkType(perkType);
        }

        CompoundNBT skills = comp.getCompound("Skills");
        for (final MythicSkills cs : MythicSkills.values()) {
            final Double value = skills.getDouble(cs.name());
            getSkillLevels().put(cs, value);
            MythriaUtil.getLevelForXP(value);
        }
        calculateProgressTowardPlayerLevel();

        CompoundNBT attributes = comp.getCompound("Attributes");
        for (final Attribute s : Attribute.values()) {
            final int v = attributes.getInt(s.name());
            attributeValues.put(s, v);
        }
        CompoundNBT consumables = comp.getCompound("Consumables");
        for (final Consumable s : Consumable.values()) {
            final double v = consumables.getDouble(s.name());
            getConsumables().put(s, v);
        }
        CompoundNBT nutritionTag = comp.getCompound("Nutrition");
        CompoundNBT digestedNutrition = nutritionTag.getCompound("Digested");
        for (final Consumable.Nutrition n : Consumable.Nutrition.values()) {
            final double v = digestedNutrition.getDouble(n.name());
            nutrition.put(n, v);
        }

        CompoundNBT undigestedNutrition = nutritionTag.getCompound("Undigested");
        for (final Consumable.Nutrition n : Consumable.Nutrition.values()) {
            final double v = undigestedNutrition.getDouble(n.name());
            undigested_nutrition.put(n, v);
        }

        CompoundNBT statModifiers = comp.getCompound("StatModifiers");
        for (final StatType s : StatType.values()) {
            final double v = statModifiers.getDouble(s.name());
            setStatModifier(s, v);

        }
        CompoundNBT favor = comp.getCompound("Favor");
        Set<String> tagsFavor = favor.keySet();
        for (String s : tagsFavor) {
            try {
                Deity d = Deity.valueOf(s);
                int v = favor.getInt(s);
                getFavorLevels().put(d, v);
            } catch (IllegalArgumentException e) {
                System.out.println("Error loading deity for favor.");
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setConsumable(final Consumable t, double v) {
        if (!created)
            return;
        switch (t) {
            case STAMINA:
                v = MathHelper.clamp(v, 0, getStat(StatType.MAX_STAMINA) * (1 - getConsumable(Consumable.FATIGUE)));
                consumables.put(t, v);
                break;
            case TEMPERATURE:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case THIRST:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case WEIGHT:
                v = MathHelper.clamp(v, 0, Double.MAX_VALUE);
                consumables.put(t, v);
                StatManager.UpdateSpeed(this, player);
                break;
            case FATIGUE:
                v = MathHelper.clamp(v, 0, 0.9);
                consumables.put(t, v);
                StatManager.UpdateSpeed(this, player);
                break;
            case PAIN:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case TORPOR:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case MANA:
                v = MathHelper.clamp(v, 0, getStat(StatType.MAX_MANA));
                consumables.put(t, v);
                break;
            case PLEASURE:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case BLOOD:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                if (v == 0) {
                    player.setHealth(0);
                }
                break;
        }
        if (player == null)
            return;
        if (!player.world.isRemote)
            MythriaPacketHandler.sendTo(new SPacketUpdateConsumables(t, v), (ServerPlayerEntity) player);
    }

    public void addFavor(Deity deity, int add, int max) {
        favorLevels.put(deity, Math.min(getFavor(deity) + add, max));
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public int getFavor(Deity d) {
        return favorLevels.containsKey(d) ? favorLevels.get(d) : 0;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setFavor(Deity d, int favor) {
        favorLevels.put(d, favor);
    }

    public void removeAttribute(Perk perk) {
        perks.remove(perk);

        if (player == null)
            return;
        if (player.world.isRemote)
            return;

        sendDataPacket();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getFullName() {
        return getFirstName() + " " + getMiddleName() + " " + getLastName();
    }

    public int getGender() {
        return gender;
    }

    public void setGender(final int gender) {
        this.gender = gender;
    }

    public void inseminate(Profile maleProfile, Profile femaleProfile) {
        if (gender == 0) return;
        if (!created) return;
        if (pregnant) return;

        Genetic primary = getGenetic();
        int cycleLength = primary.getFertileCycleLength();
        int cycleDay = MythriaUtil.wrapInt(TimeManager.getCurrentDate().getMGD(), 1, cycleLength);
        int padding = 0;
        if (cycleDay + padding > cycleLength / 4 && cycleDay - padding < cycleLength / 2) {
            pregnant = true;
            pregMotherProfileData = femaleProfile.toNBT();
            pregFatherProfileData = maleProfile.toNBT();
            pregBabyCount = (int) Math.floor(-((Math.log(random.nextDouble()) / -Math.log(89)) / Math.log(89)));
            pregConceptionData = TimeManager.getCurrentDate().getMGD();
        }
    }

    public HealthData getHealthData() {
        return healthData;
    }

    public boolean getCreated() {
        return created;
    }

    public void setCreated(final boolean created) {
        this.created = created;
    }

    public BirthSign getZodiac() {
        return TimeManager.getMonths().get(birthDay.getMonth()).getSign();
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public boolean getPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public boolean hasPerk(Perk perk) {
        for (Perk perk1 : perks) {
            if (perk1.equals(perk)) return true;
        }
        return false;
    }

    public double getBleeding() {
        return bleeding;
    }

    public void setBleeding(double bleeding) {
        this.bleeding = Math.max(bleeding, 0);
    }

    public CompoundNBT getPregFatherProfile() {
        return pregFatherProfileData;
    }

    public CompoundNBT getPregMotherProfile() {
        return pregMotherProfileData;
    }

    public int getPregBabyCount() {
        return pregBabyCount;
    }

    public void setPregBabyCount(int pregBabyCount) {
        this.pregBabyCount = pregBabyCount;
    }

    public int getSpendableAttributePoints() {
        int totalAttributes = 0;
        for (int value : attributeValues.values()) {
            totalAttributes += value;
        }
        int playerLevel = getPlayerLevel();
        int spendablePoints = playerLevel + 5 - totalAttributes;
        return spendablePoints;
    }

    public void setAttributeLevel(Attribute attribute, int level) {
        attributeValues.put(attribute, level);
    }

    public int getPregConceptionDate() {
        return pregConceptionData;
    }

    public Long getLastDisconnect() {
        return lastDisconnect;
    }

    public void setLastDisconnect(Long lastDisconnect) {
        this.lastDisconnect = lastDisconnect;
    }

    public double getPlayerLevelProgressBuffer() {
        return playerLevelProgressBuffer;
    }

    public UUID getProfileUUID() {
        return profileUUID;
    }

    public void setProfileUUID(final UUID profileUUID) {
        this.profileUUID = profileUUID;
    }

    public double getConsumable(Consumable consumable) {
        return consumables.get(consumable);
    }

    public List<Perk> getPlayerSkills() {
        return perks;
    }

    public HashMap<MythicSkills, Double> getSkillLevels() {
        return skillLevels;
    }

    public HashMap<Consumable, Double> getConsumables() {
        return consumables;
    }

    public double getStatModifier(StatType s) {
        return statModifiers.get(s);
    }

    public HashMap<Deity, Integer> getFavorLevels() {
        return favorLevels;
    }

    public int getAttributeLevel(Attribute attribute) {
        return attributeValues.get(attribute);
    }

    public void setStatModifier(StatType s, double v) {
        statModifiers.put(s, v);
    }

    private double getTotalExperience() {
        double totalExperience = 0;
        for (double value : skillLevels.values()) {
            totalExperience += value;
        }
        return totalExperience;
    }

    public void calculateProgressTowardPlayerLevel() {
        Double totalExperience = getTotalExperience();
        int playerLevel = MythriaUtil.getPlayerLevelForXP(totalExperience);
        double xpForNextLevel = MythriaUtil.getExperienceForPlayerLevel(playerLevel + 1);
        double xpForCurrentLevel = MythriaUtil.getExperienceForPlayerLevel(playerLevel);
        double xpProgress = totalExperience - xpForCurrentLevel;
        double xpDifference = xpForNextLevel - xpForCurrentLevel;
        double progress = xpProgress / xpDifference;
        playerLevelProgressBuffer = MathHelper.clamp(progress, 0, 1);
    }

    public void setGenetic(Genetic genetic) {
        this.genetic = genetic;
        addGrantedAbilities();
    }

    private void addGrantedAbilities() {
        for (Ability ability : genetic.getGrantedAbilities()) {
            if (!abilities.contains(ability)) addAbility(ability);
        }
    }

    public double getNutrition(Consumable.Nutrition nutrition) {
        return this.nutrition.get(nutrition);
    }

    public void setNutrition(Consumable.Nutrition nutrition, double value) {
        this.nutrition.put(nutrition, MathHelper.clamp(value, 0, 20));

        if (player == null)
            return;
        if (!player.world.isRemote)
            MythriaPacketHandler.sendTo(new SPacketUpdateNutrition(nutrition, value), (ServerPlayerEntity) player);
    }

    public void setUndigestedNutrition(Consumable.Nutrition nutrition, double value) {
        this.undigested_nutrition.put(nutrition, MathHelper.clamp(value, 0, 20));
    }

    public double getUndigestedNutrition(Consumable.Nutrition nutrition) {
        return this.undigested_nutrition.get(nutrition);
    }

    public double getAverageNutrition() {
        double avg = 0;
        for (Consumable.Nutrition nutrition : Consumable.Nutrition.values()) {
            avg += this.nutrition.get(nutrition);
        }
        avg /= this.nutrition.size();

        return avg;
    }

    public AbilityHandler getAbilityHandler() {
        return abilityHandler;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }
}
