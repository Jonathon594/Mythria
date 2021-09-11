package me.Jonathon594.Mythria.DataTypes.Genetic;

import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.java.games.util.plugins.PluginLoader;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.server.ServerLifecycleEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.versions.forge.ForgeVersion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        return SkinParts.getSkinPartsFor(SkinPart.Type.HAIR);
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
