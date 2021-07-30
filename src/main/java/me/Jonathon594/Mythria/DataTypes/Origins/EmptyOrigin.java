package me.Jonathon594.Mythria.DataTypes.Origins;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.LifeSpanGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;

public class EmptyOrigin extends Origin {
    public EmptyOrigin(String name) {
        super(name);
    }

    @Override
    public int getStartingAge(Genetic genetic) {
        return genetic.getLifeSpanGene().getStage(LifeSpanGene.LifeStage.ADULT);
    }
}
