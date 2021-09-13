package me.Jonathon594.Mythria.DataTypes;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GenderedSkinPart extends SkinPart {

    public GenderedSkinPart(String displayName, String name, Type type) {
        super(displayName, name, type);
    }

    @Override
    public ResourceLocation getTextureLocation(Gender gender) {
        return new MythriaResourceLocation("textures/entity/player/" +
                getRegistryName().getPath() + "_" + gender.name().toLowerCase() + ".png");
    }

}
