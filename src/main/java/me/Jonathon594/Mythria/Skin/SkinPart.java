package me.Jonathon594.Mythria.Skin;

import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SkinPart extends ForgeRegistryEntry<SkinPart> {
    private final Type type;
    protected Set<Gender> allowedGenders = new HashSet<>();
    private String displayName = "";
    private String customTextureName;

    private boolean makesPiglinsNeutral = false;

    public SkinPart(String displayName, String name, Type type) {
        setRegistryName(new MythriaResourceLocation(name));
        this.type = type;
        this.displayName = displayName;
        allowedGenders.addAll(Arrays.asList(Gender.values()));
    }

    public Set<Gender> getAllowedGenders() {
        return allowedGenders;
    }

    public SkinPart setAllowedGenders(Set<Gender> allowedGenders) {
        this.allowedGenders = allowedGenders;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ResourceLocation getTextureLocation(Gender gender) {
        String path = customTextureName == null ? getRegistryName().getPath() : customTextureName;
        return new MythriaResourceLocation("textures/entity/player/" + path + ".png");
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

    public SkinPart withCustomTextureName(String textureName) {
        customTextureName = textureName;
        return this;
    }

    public enum Type {
        SKIN(true),
        EYES(true),
        DRYAD_VINES(true),
        CLOTHING(true),
        HAIR(true),
        WINGS(false),
        SAERKI_TAIL(false);

        private final boolean rendersOnHands;

        Type(boolean rendersOnHands) {
            this.rendersOnHands = rendersOnHands;
        }

        public boolean renderOnHands() {
            return this.rendersOnHands;
        }
    }
}
