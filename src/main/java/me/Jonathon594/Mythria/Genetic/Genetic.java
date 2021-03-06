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

    public Genetic(final String name, String displayName,
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

    protected double getDoubleStatGeneValue(Gene.GeneType type) {
        DoubleStatGene gene = (DoubleStatGene) getEssentialGene(type);
        return gene.getValue();
    }

    protected int getIntGeneValue(Gene.GeneType type) {
        IntStatGene gene = (IntStatGene) getEssentialGene(type);
        return gene.getValue();
    }

    public double getGenderBias() {
        return getDoubleStatGeneValue(Gene.GeneType.GENDER_BIAS);
    }

    public int getLifeExpectancy() {
        return getIntGeneValue(Gene.GeneType.LIFESPAN);
    }

    public double getBaseStamina() {
        return getDoubleStatGeneValue(Gene.GeneType.STAMINA);
    }

    public double getBaseSpeed() {
        return getDoubleStatGeneValue(Gene.GeneType.SPEED);
    }

    public double getBaseWeight() {
        return getDoubleStatGeneValue(Gene.GeneType.WEIGHT);
    }

    public double getBaseHealth() {
        return getDoubleStatGeneValue(Gene.GeneType.HEALTH);
    }

    public double getBaseXP() {
        return getDoubleStatGeneValue(Gene.GeneType.INTELLIGENCE);
    }

    public double getBaseMana() {
        return getDoubleStatGeneValue(Gene.GeneType.MANA);
    }

    public double getBaseManaRegen() {
        return getDoubleStatGeneValue(Gene.GeneType.MANA_REGEN);
    }

    public double getIdealTemperature() {
        return getDoubleStatGeneValue(Gene.GeneType.TEMPERATURE);
    }
}
