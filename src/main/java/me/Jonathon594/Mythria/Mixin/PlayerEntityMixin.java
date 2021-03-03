package me.Jonathon594.Mythria.Mixin;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;tryToStartFallFlying()Z", at = @At("HEAD"), cancellable = true)
    protected void onTryToStartFallFlying(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if (ProfileProvider.getProfile(playerEntity).getGenetic().isGlidingAllowed()) {
                if (!playerEntity.isOnGround() && !playerEntity.isElytraFlying() && !playerEntity.isInWater() && !playerEntity.isPotionActive(Effects.LEVITATION)) {
                    playerEntity.startFallFlying();
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
