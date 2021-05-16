package me.Jonathon594.Mythria.Client.Model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.Direction;

public class LogModel extends Model {
    private final OptionalFaceCubeRenderer log;

    public LogModel() {
        super(RenderType::getEntityCutout);
        UVFace side = new UVFace(0, 16, 0, 4);
        UVFace end = new UVFace(0, 4, 4, 8);
        log = new OptionalFaceCubeRenderer(0, 0, 0, 4, 4, 16,
                new UVFace[]{side, side, end, end, side, side}, Direction.values());
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        log.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
