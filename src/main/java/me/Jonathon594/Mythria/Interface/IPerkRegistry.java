package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.PerkType;

import java.util.List;

public interface IPerkRegistry {
    List<Perk> getPerks(PerkType type);
}
