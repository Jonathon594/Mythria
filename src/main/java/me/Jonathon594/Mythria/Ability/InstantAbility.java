package me.Jonathon594.Mythria.Ability;

public abstract class InstantAbility extends Ability {
    public InstantAbility(String name) {
        super(name);
    }

    @Override
    public boolean canBeBound() {
        return true;
    }

    @Override
    public void tick(AbilityInstance abilityInstance) {

    }

    public abstract void onInstantActivate(AbilityInstance abilityInstance);
}
