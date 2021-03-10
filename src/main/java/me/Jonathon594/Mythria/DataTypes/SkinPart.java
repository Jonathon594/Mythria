package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SkinPart extends ForgeRegistryEntry<SkinPart> {
    private final Type type;
    private String displayName = "";

    public SkinPart(String displayName, String name, Type type) {
        setRegistryName(new MythriaResourceLocation(name));
        this.type = type;
        this.displayName = displayName;
        System.out.println(name.toUpperCase());
    }

    public Type getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ResourceLocation getTextureLocation(Gender gender) {
        return new MythriaResourceLocation("textures/entity/player/" + getRegistryName().getPath() + ".png");
    }

    public enum Type {
        SKIN, EYES, HAIR, CLOTHING, WINGS, DRYAD_VINES
    }
}
