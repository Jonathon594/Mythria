package me.Jonathon594.Mythria.Client.Model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class ModelSpearTip {
    public final float posX1;
    public final float posY1;
    public final float posZ1;
    public final float posX2;
    public final float posY2;
    public final float posZ2;
    private final TexturedQuad[] quads;

    public ModelSpearTip(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirorIn, float texWidth, float texHeight) {
        this.posX1 = x;
        this.posY1 = y;
        this.posZ1 = z;
        this.posX2 = x + width;
        this.posY2 = y + height;
        this.posZ2 = z + depth;
        this.quads = new TexturedQuad[5];
        float f = x + width;
        float f1 = y + height;
        float f2 = z + depth;
        x = x - deltaX;
        y = y - deltaY;
        z = z - deltaZ;
        f = f + deltaX;
        f1 = f1 + deltaY;
        f2 = f2 + deltaZ;
        if (mirorIn) {
            float f3 = f;
            f = x;
            x = f3;
        }

        float w = x + width / 2;
        float d = z + depth / 2;
        PositionTextureVertex modelrenderer$positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
        PositionTextureVertex modelrenderer$positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
        PositionTextureVertex modelrenderer$positiontexturevertex1 = new PositionTextureVertex(w, f1, d, 8.0F, 8.0F);//
        PositionTextureVertex modelrenderer$positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
        PositionTextureVertex modelrenderer$positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
        float f4 = (float) texOffX;
        float f5 = (float) texOffX + depth;
        float f6 = (float) texOffX + depth + width;
        float f8 = (float) texOffX + depth + width + depth;
        float f9 = (float) texOffX + depth + width + depth + width;
        float f10 = (float) texOffY;
        float f11 = (float) texOffY + depth;
        float f12 = (float) texOffY + depth + height;
        this.quads[2] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex}, f5, f10, f6, f11, texWidth, texHeight, mirorIn, Direction.DOWN);
        this.quads[1] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex1}, f4, f11, f5, f12, texWidth, texHeight, mirorIn, Direction.WEST);
        this.quads[3] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex, modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex1}, f5, f11, f6, f12, texWidth, texHeight, mirorIn, Direction.NORTH);
        this.quads[0] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex, modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex1}, f6, f11, f8, f12, texWidth, texHeight, mirorIn, Direction.EAST);
        this.quads[4] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex1}, f8, f11, f9, f12, texWidth, texHeight, mirorIn, Direction.SOUTH);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        Matrix3f matrix3f = matrixStackIn.getLast().getNormal();
        for (TexturedQuad modelrenderer$texturedquad : quads) {
            Vector3f vector3f = modelrenderer$texturedquad.normal.copy();
            vector3f.transform(matrix3f);
            float f = vector3f.getX();
            float f1 = vector3f.getY();
            float f2 = vector3f.getZ();

            for (int i = 0; i < 4; ++i) {
                PositionTextureVertex modelrenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
                float f3 = modelrenderer$positiontexturevertex.position.getX() / 16.0F;
                float f4 = modelrenderer$positiontexturevertex.position.getY() / 16.0F;
                float f5 = modelrenderer$positiontexturevertex.position.getZ() / 16.0F;
                Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
                vector4f.transform(matrix4f);
                bufferIn.addVertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, modelrenderer$positiontexturevertex.textureU, modelrenderer$positiontexturevertex.textureV, packedOverlayIn, packedLightIn, f, f1, f2);
            }
        }
    }


    static class TexturedQuad {
        public final PositionTextureVertex[] vertexPositions;
        public final Vector3f normal;

        public TexturedQuad(PositionTextureVertex[] positionsIn, float u1, float v1, float u2, float v2, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn) {
            this.vertexPositions = positionsIn;
            float f = 0.0F / texWidth;
            float f1 = 0.0F / texHeight;
            positionsIn[0] = positionsIn[0].setTextureUV(u2 / texWidth - f, v1 / texHeight + f1);
            positionsIn[1] = positionsIn[1].setTextureUV(u1 / texWidth + f, v1 / texHeight + f1);
            positionsIn[2] = positionsIn[2].setTextureUV(u1 / texWidth + f, v2 / texHeight - f1);
            positionsIn[3] = positionsIn[3].setTextureUV(u2 / texWidth - f, v2 / texHeight - f1);
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


    static class PositionTextureVertex {
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

        public ModelSpearTip.PositionTextureVertex setTextureUV(float texU, float texV) {
            return new ModelSpearTip.PositionTextureVertex(this.position, texU, texV);
        }
    }
}
