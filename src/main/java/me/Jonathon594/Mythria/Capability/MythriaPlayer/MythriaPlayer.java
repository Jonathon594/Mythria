package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Network.MythriaSerializers;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;

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

    private final LivingEntity entity;
    private int ticksParrying;
    private int wingFlightFlapAngle;

    public MythriaPlayer(LivingEntity entity) {
        this.entity = entity;
    }

    public MythriaPlayer() {
        this.entity = null;
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

    public int getWingFlightFlapAngle() {
        return wingFlightFlapAngle;
    }

    public void setWingFlightFlapAngle(int wingFlightFlapAngle) {
        this.wingFlightFlapAngle = wingFlightFlapAngle;
    }

    public void onTick() {

    }


}
