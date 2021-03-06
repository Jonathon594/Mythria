package me.Jonathon594.Mythria.Genetic;

public class IntStatGene extends Gene {
    private final int value;

    public IntStatGene(GeneType type, int value) {
        super(type);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
