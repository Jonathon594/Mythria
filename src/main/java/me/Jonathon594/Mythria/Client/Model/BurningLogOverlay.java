package me.Jonathon594.Mythria.Client.Model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.Direction;

public class BurningLogOverlay extends Model {
    private OptionalFaceCubeRenderer overlay;

    public BurningLogOverlay(boolean upperLog) {
        super(RenderType::getEntityCutout);

        if (upperLog) {
            UVFace sideFace = new UVFace(0, 16, 0, 4);
            UVFace bottomFace = new UVFace(0, 16, 4, 8);
            overlay = new OptionalFaceCubeRenderer(0, 0, 0, 4, 4,
                    16, new UVFace[]{sideFace, sideFace, bottomFace},
                    new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN});
        } else {
            UVFace sideFace = new UVFace(0, 16, 1, 5);
            overlay = new OptionalFaceCubeRenderer(0, 0, 0, 0, 4,
                    16, new UVFace[]{sideFace}, new Direction[]{Direction.EAST});
        }

    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
                       float red, float green, float blue, float alpha) {
        overlay.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
