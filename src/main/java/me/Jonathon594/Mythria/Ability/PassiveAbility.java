package me.Jonathon594.Mythria.Ability;

public abstract class PassiveAbility extends Ability {
    public PassiveAbility(String name) {
        super(name);
    }

    @Override
    public void tick(AbilityInstance abilityInstance) {
        onPassiveTick(abilityInstance);
    }

    protected abstract void onPassiveTick(AbilityInstance abilityInstance);
}
