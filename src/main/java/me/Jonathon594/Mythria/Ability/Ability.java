package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Random;

public abstract class Ability extends ForgeRegistryEntry<Ability> {
    protected final Random random;

    public Ability(String name) {
        setRegistryName(new MythriaResourceLocation(name));
        random = new Random();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientManager.addTextureToStitch(new MythriaResourceLocation("magic/" + getTextureName())));
    }

    public abstract void tick(AbilityInstance abilityInstance);

    public boolean canBeBound() {
        return false;
    }

    public ResourceLocation getAbilityTexturePath() {
        return new MythriaResourceLocation("textures/magic/" + getTextureName() + ".png");
    }

    protected String getTextureName() {
        return "plant";
    }
}
