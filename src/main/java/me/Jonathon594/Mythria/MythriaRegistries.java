package me.Jonathon594.Mythria;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGift;
import me.Jonathon594.Mythria.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.Serializers.GeneSerializer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class MythriaRegistries {
    public static final IForgeRegistry<Ability> ABILITIES = RegistryManager.ACTIVE.getRegistry(Ability.class);
    public static final IForgeRegistry<GeneticType> GENETICS = RegistryManager.ACTIVE.getRegistry(GeneticType.class);
    public static final IForgeRegistry<Perk> PERKS = RegistryManager.ACTIVE.getRegistry(Perk.class);
    public static final IForgeRegistry<SkinPart> SKIN_PARTS = RegistryManager.ACTIVE.getRegistry(SkinPart.class);
    public static final IForgeRegistry<GeneSerializer<? extends Gene>> GENE_SERIALIZERS =
            RegistryManager.ACTIVE.getRegistry(GeneSerializer.class);
    public static final IForgeRegistry<SpawnGift> SPAWN_GIFTS = RegistryManager.ACTIVE.getRegistry(SpawnGift.class);
}
