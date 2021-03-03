package me.Jonathon594.Mythria.Capability.MythriaPlayer;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MythriaPlayerProvider implements ICapabilityProvider {
    @CapabilityInject(IMythriaPlayer.class)
    public static final Capability<IMythriaPlayer> PLAYER_CAP = null;
    private final IMythriaPlayer instance = PLAYER_CAP.getDefaultInstance();

    public static MythriaPlayer getMythriaPlayer(LivingEntity entity) {
        MythriaPlayer mythriaPlayer = (MythriaPlayer) entity.getCapability(PLAYER_CAP, null).orElse(new MythriaPlayer(entity));
        mythriaPlayer.setEntity(entity);
        return mythriaPlayer;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        return PLAYER_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }
}
