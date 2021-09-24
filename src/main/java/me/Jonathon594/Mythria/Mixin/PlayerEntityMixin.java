package me.Jonathon594.Mythria.Mixin;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract void travel(Vector3d travelVector);

    @Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;tryToStartFallFlying()Z", at = @At("HEAD"), cancellable = true)
    protected void onTryToStartFallFlying(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if (ProfileProvider.getProfile(playerEntity).getGenetic().getSpecialAbilities().contains(SpecialAbility.GLIDING)) {
                if (!playerEntity.isOnGround() && !playerEntity.isElytraFlying() && !playerEntity.isInWater() && !playerEntity.isPotionActive(Effects.LEVITATION)) {
                    playerEntity.startFallFlying();
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "getSize", at = @At("HEAD"), cancellable = true)
    protected void onGetSize(Pose poseIn, CallbackInfoReturnable<EntitySize> cir) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(playerEntity);
            if (mythriaPlayer.hasCustomSize(poseIn)) {
                cir.setReturnValue(mythriaPlayer.getCustomSize(poseIn));
            }
        }
    }

    @Inject(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateSwimming()V"), cancellable = true)
    protected void onUpdateSwimming(CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if (!playerEntity.isElytraFlying() && ProfileProvider.getProfile(playerEntity).getGenetic().getSpecialAbilities().contains(SpecialAbility.FORCE_SWIMMING)) {
                playerEntity.setSwimming(true);
                ci.cancel();
            }
        }
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSwimming()Z"))
    protected boolean onIsSwimmingTravelProxy(PlayerEntity playerEntity) {
        if(ProfileProvider.getProfile(playerEntity).getGenetic().getSpecialAbilities().contains(SpecialAbility.FORCE_SWIMMING)) return false;
        return playerEntity.isSwimming();
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/vector/Vector3d;)V"), index = 0)
    private Vector3d onTravelArg(Vector3d travelVector) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if (playerEntity.isSwimming()) {
                return new Vector3d(travelVector.x * 0.5, playerEntity.isJumping ? 0.5 : 0 - (playerEntity.isSneaking() ? 0.5 : 0), MathHelper.clamp(travelVector.z, -0.5, 1)).rotatePitch((float) Math.toRadians(-playerEntity.rotationPitch));
            }
        }
        return travelVector;
    }
}
