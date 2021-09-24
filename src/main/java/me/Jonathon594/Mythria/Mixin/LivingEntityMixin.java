package me.Jonathon594.Mythria.Mixin;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Redirect(method = "Lnet/minecraft/entity/LivingEntity;baseTick()V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;areEyesInFluid(Lnet/minecraft/tags/ITag;)Z", ordinal = 0))
    protected boolean areEyesInFluidProxy(LivingEntity livingEntity, ITag<Fluid> tagIn) {
        return livingEntity.areEyesInFluid(FluidTags.WATER) || livingEntity.areEyesInFluid(FluidTags.LAVA);
    }

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;updateElytra()V", at = @At("HEAD"), cancellable = true)
    protected void onUpdateElytra(CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if (!playerEntity.isOnGround() && playerEntity.isElytraFlying() && !playerEntity.isInWater() && !playerEntity.isPotionActive(Effects.LEVITATION)) {
                if (ProfileProvider.getProfile(playerEntity).getGenetic().getSpecialAbilities().contains(SpecialAbility.GLIDING))
                    ci.cancel();
            }
        }
    }

    @Inject(method = "isPotionActive", at = @At("HEAD"), cancellable = true)
    protected void onIsPotionActive(Effect potionIn, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if (potionIn == Effects.NIGHT_VISION &&ProfileProvider.getProfile(playerEntity).getGenetic().getSpecialAbilities().contains(SpecialAbility.FORCE_NIGHT_VISION)) {
                cir.setReturnValue(true);
            }
            if (potionIn == Effects.WATER_BREATHING &&ProfileProvider.getProfile(playerEntity).getGenetic().getSpecialAbilities().contains(SpecialAbility.WATER_BREATHING)) {
                cir.setReturnValue(true);
            }
        }
    }

    @ModifyArgs(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/vector/Vector3d;mul(DDD)Lnet/minecraft/util/math/vector/Vector3d;", ordinal = 0)
    )
    private void onTravelArg(Args args) {
        if((Object) this instanceof PlayerEntity) {
            args.set(1, args.get(0));
        }
    }

    @Inject(method = "handleFluidJump", at = @At("HEAD"), cancellable = true)
    protected void onHandleFluidJump(ITag<Fluid> fluidTag, CallbackInfo ci) {
        ascend(ci);
    }

    private void ascend(CallbackInfo ci) {
        if((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if(playerEntity.isSwimming()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "handleFluidSneak", at = @At("HEAD"), cancellable = true)
    protected void onHandleFluidSneak(CallbackInfo ci) {
        descend(ci);
    }

    private void descend(CallbackInfo ci) {
        if((Object) this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
            if(playerEntity.isSwimming()) {
                ci.cancel();
            }
        }
    }
}
