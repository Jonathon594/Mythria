package me.Jonathon594.Mythria.Client.Model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.Direction;

public class PitFurnaceTopOverlay extends Model {
    private OptionalFaceCubeRenderer overlay;

    public PitFurnaceTopOverlay(float vOffset, boolean vFlip) {
        super(RenderType::getEntityCutout);
        UVFace face = vFlip ? new UVFace(16, 0, 4 + vOffset, vOffset)
                : new UVFace(0, 16, vOffset, 4 + vOffset);
        overlay = new OptionalFaceCubeRenderer(0, 0, 0, 4, 4,
                16, new UVFace[]{face},
                new Direction[]{Direction.UP});
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
                       float red, float green, float blue, float alpha) {
        overlay.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
