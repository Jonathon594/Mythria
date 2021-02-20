package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Enum.EnumMetalShape;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;

public interface IWorkable {
    MythriaMaterial getMetalType();

    EnumMetalShape getMetalShape();
}
