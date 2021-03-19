package me.Jonathon594.Mythria.Client.Model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.Direction;

public class PitFurnaceFillingModel extends Model {
    private OptionalFaceCubeRenderer thatch;

    public PitFurnaceFillingModel(float vOffset) {
        super(RenderType::getEntityCutout);

        UVFace flatFace = new UVFace(0, 16, 0, 16);
        UVFace sideFace = new UVFace(0, 16, 14 - vOffset, 16 - vOffset);
        thatch = new OptionalFaceCubeRenderer(0, 0, 0, 16, 2,
                16, new UVFace[]{flatFace, flatFace, sideFace, sideFace, sideFace, sideFace}, Direction.values());
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
                       float red, float green, float blue, float alpha) {
        thatch.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
