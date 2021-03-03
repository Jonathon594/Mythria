package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Random;

public abstract class Ability extends ForgeRegistryEntry<Ability> {
    protected final Random random;

    public Ability(String name) {
        setRegistryName(new MythriaResourceLocation(name));
        random = new Random();
    }

    public abstract void tick(AbilityInstance abilityInstance);
}
