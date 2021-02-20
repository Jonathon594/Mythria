package me.Jonathon594.Mythria.Util;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.util.ResourceLocation;

public class MythriaResourceLocation extends ResourceLocation {
    public MythriaResourceLocation(String resourceName) {
        super(Mythria.MODID, resourceName);
    }
}
