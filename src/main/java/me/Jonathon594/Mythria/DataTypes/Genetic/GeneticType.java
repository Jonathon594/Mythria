package me.Jonathon594.Mythria.DataTypes.Genetic;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class GeneticType extends ForgeRegistryEntry<GeneticType> {
    private final String displayName;
    private final Supplier<Genetic> factory;
    private final Genetic instance;
    private SkinPart.Type specialSkinPartType = null;

    public GeneticType(String name, String displayName, Supplier<Genetic> factory) {
        setRegistryName(new MythriaResourceLocation(name));
        this.displayName = displayName;
        this.factory = factory;
        this.instance = factory.get();
    }

    public Genetic createGenetic() {
        return factory.get();
    }

    public List<SkinPart> getAllowedClothes() {
        return SkinParts.getSkinPartsFor(SkinPart.Type.CLOTHING);
    }

    public List<SkinPart> getAllowedEyes() {
        return SkinParts.getSkinPartsFor(SkinPart.Type.EYES);
    }

    public List<SkinPart> getAllowedHairs() {
        return ImmutableList.of(SkinParts.HUMAN_HAIR_BLACK, SkinParts.HUMAN_HAIR_BROWN, SkinParts.HUMAN_HAIR_BLOND, SkinParts.HUMAN_HAIR_GINGER);
    }

    public Collection<Origin> getAllowedOrigins() {
        return MythriaRegistries.ORIGINS.getValues();
    }

    public List<SkinPart> getAllowedSkins() {
        return SkinParts.getSkinPartsFor(SkinPart.Type.SKIN);
    }

    public Genetic getDefaultInstance() {
        return instance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SkinPart.Type getSpecialSkinPartType() {
        return specialSkinPartType;
    }

    public GeneticType setSpecialSkinPartType(SkinPart.Type specialSkinPartType) {
        this.specialSkinPartType = specialSkinPartType;
        return this;
    }
}
