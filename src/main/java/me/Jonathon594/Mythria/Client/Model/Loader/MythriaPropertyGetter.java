package me.Jonathon594.Mythria.Client.Model.Loader;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ResourceLocation;

public abstract class MythriaPropertyGetter implements IItemPropertyGetter {
    private final ResourceLocation resourceLocation;

    protected MythriaPropertyGetter(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public ResourceLocation getName() {
        return resourceLocation;
    }
}
