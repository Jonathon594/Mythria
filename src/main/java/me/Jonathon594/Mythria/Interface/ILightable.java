package me.Jonathon594.Mythria.Interface;

public interface ILightable {
    void light();

    boolean tryLight(double friction);

    boolean canBeLit();

    boolean isLit();
}
