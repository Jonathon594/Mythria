package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Managers.SkinPartManager;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Arrays;

public class SkinPart extends ForgeRegistryEntry<SkinPart> {
    private final ArrayList<Genetic> allowedRaces = new ArrayList<>();
    private final Type type;
    private String displayName = "";
    private boolean masculine = false;
    private boolean feminine = false;

    public SkinPart(String displayName, String name, Type type, boolean masculine, boolean feminine) {
        setRegistryName(new MythriaResourceLocation(name));
        this.type = type;
        this.displayName = displayName;
        this.masculine = masculine;
        this.feminine = feminine;

        SkinPartManager.addSkinPart(this);
    }

    public ArrayList<Genetic> getAllowedRaces() {
        return allowedRaces;
    }

    public SkinPart addAllowedRace(Genetic... race) {
        allowedRaces.addAll(Arrays.asList(race));
        return this;
    }

    public Type getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isMasculine() {
        return masculine;
    }

    public boolean isFeminine() {
        return feminine;
    }

    public ResourceLocation getResourceLocation() {
        return new MythriaResourceLocation("textures/entity/player/" + getRegistryName().getPath() + ".png");
    }

    public enum Type {
        SKIN, EYES, HAIR, CLOTHING, WINGS, DRYAD_VINES, SKAEREN_SCALES
    }
}
