package me.Jonathon594.Mythria;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Genetic.Genetic;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class MythriaRegistries {
    public static final IForgeRegistry<Ability> ABILITIES = RegistryManager.ACTIVE.getRegistry(Ability.class);
    public static final IForgeRegistry<Genetic> GENETICS = RegistryManager.ACTIVE.getRegistry(Genetic.class);
    public static final IForgeRegistry<Perk> PERKS = RegistryManager.ACTIVE.getRegistry(Perk.class);
    public static final IForgeRegistry<SkinPart> SKIN_PARTS = RegistryManager.ACTIVE.getRegistry(SkinPart.class);
}
