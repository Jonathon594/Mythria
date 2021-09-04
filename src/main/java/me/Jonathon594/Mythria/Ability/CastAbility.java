package me.Jonathon594.Mythria.Ability;

public class CastAbility extends Ability {
    public CastAbility(String name) {
        super(name);
    }

    @Override
    public boolean canBeBound() {
        return true;
    }

    @Override
    public void tick(AbilityInstance abilityInstance) {

    }
}
