package me.Jonathon594.Mythria.Client.Model;

public class UVFace {
    private float u1, u2, v1, v2;

    public UVFace(float u1, float u2, float v1, float v2) {
        this.u1 = u1;
        this.u2 = u2;
        this.v1 = v1;
        this.v2 = v2;
    }

    public float getU1() {
        return u1;
    }

    public float getU2() {
        return u2;
    }

    public float getV1() {
        return v1;
    }

    public float getV2() {
        return v2;
    }
}
