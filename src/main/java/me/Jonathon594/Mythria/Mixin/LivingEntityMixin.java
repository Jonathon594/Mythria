package me.Jonathon594.Mythria.Mixin;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract void livingTick();

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;updateElytra()V", at = @At("HEAD"), cancellable = true)
    protected void onUpdateElytra(CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if (!playerEntity.isOnGround() && playerEntity.isElytraFlying() && !playerEntity.isInWater() && !playerEntity.isPotionActive(Effects.LEVITATION)) {
                //if (ProfileProvider.getProfile(playerEntity).getGenetic().isGlidingAllowed())
                //    ci.cancel(); todo
            }
        }
    }

    @Redirect(method = "Lnet/minecraft/entity/LivingEntity;baseTick()V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;areEyesInFluid(Lnet/minecraft/tags/ITag;)Z", ordinal = 0))
    protected boolean areEyesInFluidProxy(LivingEntity livingEntity, ITag<Fluid> tagIn) {
        return livingEntity.areEyesInFluid(FluidTags.WATER) || livingEntity.areEyesInFluid(FluidTags.LAVA);
    }
}
