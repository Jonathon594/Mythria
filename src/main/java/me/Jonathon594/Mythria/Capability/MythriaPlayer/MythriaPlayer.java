package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import me.Jonathon594.Mythria.Enum.CombatMode;
import me.Jonathon594.Mythria.Enum.ControlMode;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Enum.InputIntent;
import me.Jonathon594.Mythria.Network.MythriaSerializers;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.Hand;

public class MythriaPlayer implements IMythriaPlayer {
    public static final DataParameter<Boolean> PARRYING = new DataParameter<>(254, DataSerializers.BOOLEAN);
    public static final DataParameter<SkinPart> HAIR = new DataParameter<>(253, MythriaSerializers.SKIN);
    public static final DataParameter<SkinPart> SKIN = new DataParameter<>(252, MythriaSerializers.SKIN);
    public static final DataParameter<SkinPart> CLOTHES = new DataParameter<>(251, MythriaSerializers.SKIN);
    public static final DataParameter<SkinPart> EYES = new DataParameter<>(250, MythriaSerializers.SKIN);
    public static final DataParameter<SkinPart> WINGS = new DataParameter<>(249, MythriaSerializers.SKIN);
    public static final DataParameter<SkinPart> VINES = new DataParameter<>(248, MythriaSerializers.SKIN);
    public static final DataParameter<SkinPart> SCALES = new DataParameter<>(247, MythriaSerializers.SKIN);
    public static final DataParameter<Gender> GENDER = new DataParameter<>(246, MythriaSerializers.GENDER);
    public static final DataParameter<ControlMode> CONTROL_MODE = new DataParameter<>(245, MythriaSerializers.CONTROL_MODE);

    private final LivingEntity entity;
    private int ticksParrying;
    private int wingFlightFlapAngle;
    private CombatMode combatMode = CombatMode.NORMAL;

    private InputIntent mainhandIntent = InputIntent.NONE, offhandIntent = InputIntent.NONE;
    private int attackingMainhand;
    private int attackingOffhand;

    private boolean abilityBookOpen = false;

    public MythriaPlayer(LivingEntity entity) {
        this.entity = entity;
    }

    public MythriaPlayer() {
        this.entity = null;
    }

    public int getAttackingMainhand() {
        return attackingMainhand;
    }

    public boolean isAbilityBookOpen() {
        return abilityBookOpen;
    }

    public MythriaPlayer setAbilityBookOpen(boolean open) {
        this.abilityBookOpen = open;
        return this;
    }

    public MythriaPlayer setAttackingMainhand(int attackingMainhand) {
        this.attackingMainhand = attackingMainhand;
        return this;
    }

    public int getAttackingOffhand() {
        return attackingOffhand;
    }

    public MythriaPlayer setAttackingOffhand(int attackingOffhand) {
        this.attackingOffhand = attackingOffhand;
        return this;
    }

    public CombatMode getCombatMode() {
        return combatMode;
    }

    public void setCombatMode(CombatMode combatMode) {
        this.combatMode = combatMode;
    }

    public ControlMode getControlMode() {
        return entity.getDataManager().get(CONTROL_MODE);
    }

    public void setControlMode(ControlMode controlMode) {
        entity.getDataManager().set(CONTROL_MODE, controlMode);
    }

    @Override
    public void fromNBT(CompoundNBT comp) {
        abilityBookOpen = comp.getBoolean("AbilityBookOpen");
    }

    @Override
    public Gender getGender() {
        return entity.getDataManager().get(GENDER);
    }

    @Override
    public void setGender(Gender gender) {
        entity.getDataManager().set(GENDER, gender);
    }

    @Override
    public SkinPart getSkinPart(SkinPart.Type skinPart) {
        switch (skinPart) {
            case SKIN:
                return entity.getDataManager().get(SKIN);
            case EYES:
                return entity.getDataManager().get(EYES);
            case HAIR:
                return entity.getDataManager().get(HAIR);
            case CLOTHING:
                return entity.getDataManager().get(CLOTHES);
            case WINGS:
                return entity.getDataManager().get(WINGS);
            case DRYAD_VINES:
                return entity.getDataManager().get(VINES);
        }
        return null;
    }

    @Override
    public int getTicksParrying() {
        return ticksParrying;
    }

    @Override
    public void setTicksParrying(int ticksParrying) {
        this.ticksParrying = ticksParrying;
    }

    @Override
    public boolean isParrying() {
        return entity.getDataManager().get(PARRYING);
    }

    @Override
    public void setParrying(boolean parrying) {
        entity.getDataManager().set(PARRYING, parrying);
    }

    //todo skinpart layer numbers
    @Override
    public void setSkinPart(SkinPart.Type skinPart, SkinPart part) {
        switch (skinPart) {
            case SKIN:
                entity.getDataManager().set(SKIN, part);
                break;
            case EYES:
                entity.getDataManager().set(EYES, part);
                break;
            case HAIR:
                entity.getDataManager().set(HAIR, part);
                break;
            case CLOTHING:
                entity.getDataManager().set(CLOTHES, part);
                break;
            case WINGS:
                entity.getDataManager().set(WINGS, part);
                break;
            case DRYAD_VINES:
                entity.getDataManager().set(VINES, part);
                break;
        }
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("AbilityBookOpen", abilityBookOpen);
        return compoundNBT;
    }

    public InputIntent getInputIntent(Hand hand) {
        return hand == Hand.MAIN_HAND ? mainhandIntent : offhandIntent;
    }

    public int getWingFlightFlapAngle() {
        return wingFlightFlapAngle;
    }

    public void setWingFlightFlapAngle(int wingFlightFlapAngle) {
        this.wingFlightFlapAngle = wingFlightFlapAngle;
    }

    public void onTick() {
    }

    public MythriaPlayer setInputIntent(Hand hand, InputIntent inputIntent) {
        if (hand == Hand.MAIN_HAND) {
            mainhandIntent = inputIntent;
        } else offhandIntent = inputIntent;
        return this;
    }
}
