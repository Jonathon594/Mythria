package me.Jonathon594.Mythria.Genetic;

public class DoubleStatGene extends Gene {
    private final double value;

    public DoubleStatGene(GeneType type, double value) {
        super(type);
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
