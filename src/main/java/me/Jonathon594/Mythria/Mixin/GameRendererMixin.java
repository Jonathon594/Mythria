package me.Jonathon594.Mythria.Mixin;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getNightVisionBrightness", at = @At("HEAD"), cancellable = true)
    private static void onGetNightVisionBrightness(LivingEntity livingEntityIn, float entitylivingbaseIn, CallbackInfoReturnable<Float> cir) {
        if (livingEntityIn instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntityIn;
            if (ProfileProvider.getProfile(playerEntity).getGenetic().getSpecialAbilities().contains(SpecialAbility.FORCE_NIGHT_VISION)) {
                cir.setReturnValue(1.0F);
            }
        }
    }
}
