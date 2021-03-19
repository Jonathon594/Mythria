package me.Jonathon594.Mythria.Client.Model;

import net.minecraft.util.math.vector.Vector3f;

class PositionTextureVertex {
    public final Vector3f position;
    public final float textureU;
    public final float textureV;

    public PositionTextureVertex(float x, float y, float z, float texU, float texV) {
        this(new Vector3f(x, y, z), texU, texV);
    }

    public PositionTextureVertex(Vector3f posIn, float texU, float texV) {
        this.position = posIn;
        this.textureU = texU;
        this.textureV = texV;
    }

    public PositionTextureVertex setTextureUV(float texU, float texV) {
        return new PositionTextureVertex(this.position, texU, texV);
    }
}
