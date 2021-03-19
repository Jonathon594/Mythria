package me.Jonathon594.Mythria.Client.Model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.*;

public class OptionalFaceCubeRenderer {
    private final UVQuad[] quads;

    public OptionalFaceCubeRenderer(float x, float y, float z, float x2, float y2, float z2, UVFace[] uvs, Direction[] face) {
        this.quads = new UVQuad[face.length];
        Vector3f vertex7 = new Vector3f(x, y, z);
        Vector3f vertex = new Vector3f(x2, y, z);
        Vector3f vertex1 = new Vector3f(x2, y2, z);
        Vector3f vertex2 = new Vector3f(x, y2, z);
        Vector3f vertex3 = new Vector3f(x, y, z2);
        Vector3f vertex4 = new Vector3f(x2, y, z2);
        Vector3f vertex5 = new Vector3f(x2, y2, z2);
        Vector3f vertex6 = new Vector3f(x, y2, z2);

        for (int i = 0; i < face.length; i++) {
            Vector2f uv1 = new Vector2f(uvs[i].getU1(), uvs[i].getV1());
            Vector2f uv2 = new Vector2f(uvs[i].getU2(), uvs[i].getV1());
            Vector2f uv3 = new Vector2f(uvs[i].getU1(), uvs[i].getV2());
            Vector2f uv4 = new Vector2f(uvs[i].getU2(), uvs[i].getV2());

            switch (face[i]) {
                case DOWN:
                    quads[i] = new UVQuad(new Vector3f[]{vertex4, vertex3, vertex7, vertex},
                            new Vector2f[]{uv4, uv2, uv1, uv3}, face[i]);
                    break;
                case UP:
                    quads[i] = new UVQuad(new Vector3f[]{vertex1, vertex2, vertex6, vertex5},
                            new Vector2f[]{uv3, uv1, uv2, uv4}, face[i]);
                    break;
                case NORTH:
                    quads[i] = new UVQuad(new Vector3f[]{vertex, vertex7, vertex2, vertex1},
                            new Vector2f[]{uv4, uv3, uv1, uv2}, face[i]);
                    break;
                case SOUTH:
                    quads[i] = new UVQuad(new Vector3f[]{vertex3, vertex4, vertex5, vertex6},
                            new Vector2f[]{uv3, uv4, uv2, uv1}, face[i]);
                    break;
                case WEST:
                    quads[i] = new UVQuad(new Vector3f[]{vertex7, vertex3, vertex6, vertex2},
                            new Vector2f[]{uv4, uv3, uv1, uv2}, face[i]);
                    break;
                case EAST:
                    quads[i] = new UVQuad(new Vector3f[]{vertex4, vertex, vertex1, vertex5},
                            new Vector2f[]{uv3, uv4, uv2, uv1}, face[i]);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + face);
            }
        }
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        for (UVQuad quad : quads) {
            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            Matrix3f matrix3f = matrixStackIn.getLast().getNormal();
            Vector3f vector3f = quad.normal.copy();
            vector3f.transform(matrix3f);
            float f = vector3f.getX();
            float f1 = vector3f.getY();
            float f2 = vector3f.getZ();

            for (int i = 0; i < 4; ++i) {
                Vector3f vertex = quad.vertexPositions[i];
                float f3 = vertex.getX() / 16.0F;
                float f4 = vertex.getY() / 16.0F;
                float f5 = vertex.getZ() / 16.0F;
                Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
                vector4f.transform(matrix4f);

                Vector2f uv = quad.uvPositions[i];
                float f6 = uv.x / 16.0F;
                float f7 = uv.y / 16.0F;
                bufferIn.addVertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, f6, f7, packedOverlayIn, packedLightIn, f, f1, f2);
            }
        }
    }
}
