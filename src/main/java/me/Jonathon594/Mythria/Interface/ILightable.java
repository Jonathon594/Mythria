package me.Jonathon594.Mythria.Interface;

public interface ILightable {
    boolean canBeLit();

    boolean isLit();

    void light();

    boolean tryLight(double friction);
}
