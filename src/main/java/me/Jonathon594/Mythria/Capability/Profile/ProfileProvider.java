package me.Jonathon594.Mythria.Capability.Profile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProfileProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IProfile.class)
    public static final Capability<IProfile> PROFILE_CAP = null;
    private final Profile instance = new Profile();
    private final PlayerEntity player;

    public ProfileProvider(PlayerEntity player) {
        this.player = player;
        instance.setPlayer(player);
    }

    public static Profile getProfile(PlayerEntity playerEntity) {
        Profile profile = (Profile) playerEntity.getCapability(PROFILE_CAP, null).orElse(new Profile());
        return profile;
    }

    @Override
    public INBT serializeNBT() {
        return PROFILE_CAP.getStorage().writeNBT(PROFILE_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        PROFILE_CAP.getStorage().readNBT(PROFILE_CAP, instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PROFILE_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }
}
