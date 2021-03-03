package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.EnumStone;
import net.minecraft.state.EnumProperty;

import java.util.Collection;

public class StoneProperty extends EnumProperty<EnumStone> {
    public StoneProperty(String name, Collection<EnumStone> values) {
        super(name, EnumStone.class, values);
    }
}
