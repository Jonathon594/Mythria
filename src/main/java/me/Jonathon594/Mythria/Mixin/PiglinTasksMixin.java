package me.Jonathon594.Mythria.Mixin;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.EntityAttitudeGene;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinTasks.class)
public class PiglinTasksMixin {
    @Inject(method = "Lnet/minecraft/entity/monster/piglin/PiglinTasks;func_234460_a_(Lnet/minecraft/entity/LivingEntity;)Z",
            at = @At("HEAD"), cancellable = true)
    private static void onFunc_234460_a_(LivingEntity p_234460_0_, CallbackInfoReturnable<Boolean> cir) {
        //Stop piglins attacking players with golden gilded clothing
        if (p_234460_0_ instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) p_234460_0_;
            Profile profile = ProfileProvider.getProfile(player);
            if (profile.getClothing().makesPiglinsNeutral()) cir.setReturnValue(true);

            profile.getGenetic().getExtraGenes().forEach(gene -> {
                if (gene instanceof EntityAttitudeGene) {
                    EntityAttitudeGene attitudeGene = (EntityAttitudeGene) gene;
                    if (!attitudeGene.getEntityTypes().contains(EntityType.PIGLIN)) return;
                    EntityAttitudeGene.Attitude attitude = attitudeGene.getAttitude();
                    if (!attitude.equals(EntityAttitudeGene.Attitude.HATE)) {
                        cir.setReturnValue(true);
                    }
                }
            });
        }
    }
}
