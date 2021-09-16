package me.Jonathon594.Mythria.Mixin;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow
    private boolean clientSneakState;

    @Inject(method = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;livingTick()V", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/client/entity/player/ClientPlayerEntity;getItemStackFromSlot(Lnet/minecraft/inventory/EquipmentSlotType;)Lnet/minecraft/item/ItemStack;"))
    protected void onLivingTick(CallbackInfo ci) {
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) (Object) this;
        if (!clientPlayerEntity.getItemStackFromSlot(EquipmentSlotType.CHEST).canElytraFly(clientPlayerEntity) && !clientPlayerEntity.isElytraFlying()) {
            clientPlayerEntity.connection.sendPacket(new CEntityActionPacket(clientPlayerEntity, CEntityActionPacket.Action.START_FALL_FLYING));
        }
    }

    //Used so that sprinting is possible even when out of food
    @Redirect(method = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;livingTick()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/FoodStats;getFoodLevel()I"))
    protected int onGetFoodStatsProxy(FoodStats foodStats) {
        return 20;
    }
}
