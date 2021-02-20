package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Entity.MythriaArrowEntity;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MythriaArrorRenderer<T extends MythriaArrowEntity> extends ArrowRenderer<T> {
    public MythriaArrorRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        ItemStack stack = entity.getArrowStack();
        if (stack.isEmpty()) return null;
        return new MythriaResourceLocation("textures/entity/arrows/" + stack.getItem().getRegistryName().getPath() + ".png");
    }
}
