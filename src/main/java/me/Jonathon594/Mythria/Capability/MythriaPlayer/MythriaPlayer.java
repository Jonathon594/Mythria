package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import me.Jonathon594.Mythria.DataTypes.SkinPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;

public class MythriaPlayer implements IMythriaPlayer {
    public static final DataParameter<Boolean> PARRYING = new DataParameter<>(254, DataSerializers.BOOLEAN);
    public static final DataParameter<String> HAIR = new DataParameter<>(253, DataSerializers.STRING);
    public static final DataParameter<String> SKIN = new DataParameter<>(252, DataSerializers.STRING);
    public static final DataParameter<String> CLOTHES = new DataParameter<>(251, DataSerializers.STRING);
    public static final DataParameter<String> EYES = new DataParameter<>(250, DataSerializers.STRING);
    public static final DataParameter<String> WINGS = new DataParameter<>(249, DataSerializers.STRING);
    public static final DataParameter<String> VINES = new DataParameter<>(248, DataSerializers.STRING);
    public static final DataParameter<String> SCALES = new DataParameter<>(247, DataSerializers.STRING);

    private LivingEntity entity;
    private int ticksParrying;
    private int wingFlightFlapAngle;

    public MythriaPlayer(LivingEntity entity) {
        this.entity = entity;
    }

    public MythriaPlayer() {
        this.entity = null;
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
    public int getTicksParrying() {
        return ticksParrying;
    }

    @Override
    public void setTicksParrying(int ticksParrying) {
        this.ticksParrying = ticksParrying;
    }

    @Override
    public void setSkinPart(SkinPart.Type skinPart, String part) {
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
            case SKAEREN_SCALES:
                entity.getDataManager().set(SCALES, part);
                break;
        }
    }

    @Override
    public String getSkinPart(SkinPart.Type skinPart) {
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
            case SKAEREN_SCALES:
                return entity.getDataManager().get(SCALES);
        }
        return null;
    }

    //todo remove
    public void setEntity(LivingEntity entity) {
        this.entity = entity;
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
