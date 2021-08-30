package me.Jonathon594.Mythria.Mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Client.PlayerRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FirstPersonRenderer.class)
public abstract class FirstPersonRendererMixin {
    private final Minecraft mc = Minecraft.getInstance();

    @Shadow
    protected abstract void renderArmFirstPerson(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, HandSide side);

    @Inject(method = "renderItemInFirstPerson(FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer$Impl;Lnet/minecraft/client/entity/player/ClientPlayerEntity;I)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/matrix/MatrixStack;rotate(Lnet/minecraft/util/math/vector/Quaternion;)V", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    protected void onRenderItemInFirstPerson(float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer.Impl bufferIn,
                                             ClientPlayerEntity playerEntityIn, int combinedLightIn, CallbackInfo ci, float f,
                                             Hand hand, float f1, boolean flag, boolean flag1, float f3, float f4) {
        flag1 = true;
    }

    @Inject(method = "renderItemInFirstPerson(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    protected void onRenderItemInFirstPerson(AbstractClientPlayerEntity player, float partialTicks, float pitch, Hand handIn,
                                             float swingProgress, ItemStack stack, float equippedProgress, MatrixStack matrixStackIn,
                                             IRenderTypeBuffer bufferIn, int combinedLightIn,
                                             CallbackInfo ci, boolean flag, HandSide handside) {
        if (!flag && stack.isEmpty() && !player.isInvisible()) {
            renderArmFirstPerson(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, handside);
            matrixStackIn.pop();
            ci.cancel();
        }
    }

    @Inject(method = "renderArmFirstPerson", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/PlayerRenderer;renderRightArm(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/client/entity/player/AbstractClientPlayerEntity;)V", ordinal = 0), cancellable = true)
    protected void onRenderArmFirstPersonRightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, HandSide side, CallbackInfo ci) {
        PlayerRenderManager.RENDER_MYTHRIA_PLAYER.renderArm(matrixStackIn, bufferIn, combinedLightIn, mc.player, HandSide.RIGHT);
        ci.cancel();
    }

    @Inject(method = "renderArmFirstPerson", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/PlayerRenderer;renderLeftArm(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/client/entity/player/AbstractClientPlayerEntity;)V", ordinal = 0), cancellable = true)
    protected void onRenderArmFirstPersonLeftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, HandSide side, CallbackInfo ci) {
        PlayerRenderManager.RENDER_MYTHRIA_PLAYER.renderArm(matrixStackIn, bufferIn, combinedLightIn, mc.player, HandSide.LEFT);
        ci.cancel();
    }
}
