package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;

public class NetherChickenRenderer extends ChickenRenderer {
    private static final MythriaResourceLocation CHICKEN_TEXTURES = new MythriaResourceLocation("textures/entity/nether_chicken.png");

    public NetherChickenRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ChickenEntity entity) {
        return CHICKEN_TEXTURES;
    }
}
