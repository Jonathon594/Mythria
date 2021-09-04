package me.Jonathon594.Mythria.Client.Enum;

import com.google.common.collect.Lists;
import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Ability.CastAbility;
import me.Jonathon594.Mythria.Ability.InstantAbility;
import me.Jonathon594.Mythria.Ability.PassiveAbility;
import me.Jonathon594.Mythria.MythriaRegistries;

import java.util.List;
import java.util.function.Predicate;

public enum AbilityBookCategories {
    SEARCH(ability -> true),
    INSTANT(ability -> ability instanceof InstantAbility),
    CASTED(ability -> ability instanceof CastAbility),
    PASSIVE(ability -> ability instanceof PassiveAbility);

    private Predicate<? super Ability> predicate;

    AbilityBookCategories(Predicate<? super Ability> predicate) {
        this.predicate = predicate;
    }

    public List<Ability> getAbilities() {
        List<Ability> abilities = Lists.newArrayList(MythriaRegistries.ABILITIES.getValues());
        abilities.removeIf(predicate.negate());
        return abilities;
    }
}
