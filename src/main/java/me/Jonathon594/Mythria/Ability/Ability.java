package me.Jonathon594.Mythria.Ability;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public abstract class Ability extends ForgeRegistryEntry<Ability> {
    protected final Random random;

    public Ability(String name) {
        setRegistryName(new MythriaResourceLocation(name));
        random = new Random();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientManager.addTextureToStitch(new MythriaResourceLocation("magic/" + getTextureName())));
    }

    public boolean canBeBound() {
        return false;
    }

    public ResourceLocation getAbilityTexturePath() {
        return new MythriaResourceLocation("textures/magic/" + getTextureName() + ".png");
    }

    public List<ITextComponent> getHoveredToolTip() {
        return ImmutableList.of(
                getDisplayName()
        );
    }

    @NotNull
    public TranslationTextComponent getDisplayName() {
        return new TranslationTextComponent("abilities." + getRegistryName().getPath() + ".name");
    }

    public boolean isHidden() {
        return false;
    }

    public abstract void tick(AbilityInstance abilityInstance);

    protected String getTextureName() {
        return "plant";
    }
}
