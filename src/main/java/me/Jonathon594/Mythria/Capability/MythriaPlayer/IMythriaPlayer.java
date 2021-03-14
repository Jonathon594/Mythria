package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Enum.Gender;

public interface IMythriaPlayer {
    boolean isParrying();

    void setParrying(boolean parrying);

    int getTicksParrying();

    void setTicksParrying(int ticksParrying);

    void setSkinPart(SkinPart.Type skinPart, SkinPart part);

    SkinPart getSkinPart(SkinPart.Type skinPart);

    void setGender(Gender gender);

    Gender getGender();
}
