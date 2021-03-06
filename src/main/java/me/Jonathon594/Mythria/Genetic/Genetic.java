package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class Genetic extends ForgeRegistryEntry<Genetic> {
    private final String displayName;
    private final SpawnPos spawnPos;
    private RegistryKey<World> spawnDimension = World.OVERWORLD;
    private boolean locked = false;
    private SkinPart.Type specialSkinPartType = null;

    private List<Gene> genes = new ArrayList<>();

    public Genetic(final String name, String displayName, double idealTemperature, double baseStamina, double baseSpeed, double baseWeight, double baseHealth, double baseXP, double baseMana, double baseManaRegen, int lifeExpectancy,
                   SpawnPos spawnPos) {
        this.displayName = displayName;
        this.spawnPos = spawnPos;
        setRegistryName(new MythriaResourceLocation(name));
    }

    public Genetic withGene(Gene gene) {
        if (!gene.getType().canStack())
            genes.removeIf((g) -> !g.getType().canStack() && g.getType().equals(gene.getType()));
        this.genes.add(gene);
        return this;
    }

    public List<Gene> getGenesOfType(Gene.GeneType geneType) {
        List<Gene> genes = new ArrayList<>();
        this.genes.forEach((gene) -> {
            if (gene.getType().equals(geneType)) genes.add(gene);
        });
        return genes;
    }

    public Gene getEssentialGene(Gene.GeneType type) {
        if (!type.isEssential()) throw new IllegalArgumentException("Passed Gene.Type is not essential.");
        List<Gene> genes = getGenesOfType(type);
        if (genes.size() == 0) throw new IllegalStateException("Genetic was missing essential gene type " + type);
        Gene gene = genes.get(0);
        return gene;
    }

    public SpawnPos getSpawnPos() {
        return spawnPos;
    }

    public RegistryKey<World> getSpawnDimension() {
        return spawnDimension;
    }

    public Genetic setSpawnDimension(RegistryKey<World> spawnDimension) {
        this.spawnDimension = spawnDimension;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SkinPart.Type getSpecialSkinPartType() {
        return specialSkinPartType;
    }

    public Genetic setSpecialSkinPartType(SkinPart.Type specialSkinPartType) {
        this.specialSkinPartType = specialSkinPartType;
        return this;
    }
}
