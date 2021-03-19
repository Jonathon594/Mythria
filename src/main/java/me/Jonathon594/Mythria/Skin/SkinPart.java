package me.Jonathon594.Mythria.Skin;

import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SkinPart extends ForgeRegistryEntry<SkinPart> {
    private final Type type;
    private String displayName = "";

    private boolean makesPiglinsNeutral = false;

    public SkinPart(String displayName, String name, Type type) {
        setRegistryName(new MythriaResourceLocation(name));
        this.type = type;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ResourceLocation getTextureLocation(Gender gender) {
        return new MythriaResourceLocation("textures/entity/player/" + getRegistryName().getPath() + ".png");
    }

    public Type getType() {
        return type;
    }

    public boolean makesPiglinsNeutral() {
        return makesPiglinsNeutral;
    }

    public SkinPart setMakesPiglinsNeutral(boolean makesPiglinsNeutral) {
        this.makesPiglinsNeutral = makesPiglinsNeutral;
        return this;
    }

    public enum Type {
        SKIN, EYES, HAIR, CLOTHING, WINGS, DRYAD_VINES
    }
}
