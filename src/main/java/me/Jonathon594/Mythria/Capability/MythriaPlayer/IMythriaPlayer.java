package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.nbt.CompoundNBT;

public interface IMythriaPlayer {
    void fromNBT(CompoundNBT comp);

    Gender getGender();

    void setGender(Gender gender);

    SkinPart getSkinPart(SkinPart.Type skinPart);

    int getTicksParrying();

    void setTicksParrying(int ticksParrying);

    boolean isParrying();

    void setParrying(boolean parrying);

    void setSkinPart(SkinPart.Type skinPart, SkinPart part);

    CompoundNBT toNBT();
}
