package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import me.Jonathon594.Mythria.DataTypes.SkinPart;

public interface IMythriaPlayer {
    boolean isParrying();

    void setParrying(boolean parrying);

    int getTicksParrying();

    void setTicksParrying(int ticksParrying);

    void setSkinPart(SkinPart.Type skinPart, String part);

    String getSkinPart(SkinPart.Type skinPart);
}
