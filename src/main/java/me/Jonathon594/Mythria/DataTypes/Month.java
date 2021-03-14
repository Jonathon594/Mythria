package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.BirthSign;

public class Month {
    private final String Name;
    private final int Length;
    private final BirthSign sign;

    public Month(final String name, final int length, BirthSign sign) {
        Name = name;
        Length = length;
        this.sign = sign;
    }

    public int getLength() {
        return Length;
    }

    public String getName() {
        return Name;
    }

    public BirthSign getSign() {
        return sign;
    }
}
