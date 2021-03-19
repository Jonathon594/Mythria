package me.Jonathon594.Mythria.Client.Model;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

class TexturedQuad {
    public final PositionTextureVertex[] vertexPositions;
    public final Vector3f normal;

    public TexturedQuad(PositionTextureVertex[] positionsIn, float u1, float v1, float u2, float v2, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn) {
        this.vertexPositions = positionsIn;
        float f = 0.0F / texWidth;
        float f1 = 0.0F / texHeight;
        if (mirrorIn) {
            int i = positionsIn.length;

            for (int j = 0; j < i / 2; ++j) {
                PositionTextureVertex modelrenderer$positiontexturevertex = positionsIn[j];
                positionsIn[j] = positionsIn[i - 1 - j];
                positionsIn[i - 1 - j] = modelrenderer$positiontexturevertex;
            }
        }

        this.normal = directionIn.toVector3f();
        if (mirrorIn) {
            this.normal.mul(-1.0F, 1.0F, 1.0F);
        }
    }
}
