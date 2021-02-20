package me.Jonathon594.Mythria.Ability;

public abstract class PassiveTickAbility extends Ability {
    public PassiveTickAbility(String name) {
        super(name);
    }

    @Override
    public void tick(AbilityInstance abilityInstance) {
        onPassiveTick(abilityInstance);
    }

    protected abstract void onPassiveTick(AbilityInstance abilityInstance);
}
