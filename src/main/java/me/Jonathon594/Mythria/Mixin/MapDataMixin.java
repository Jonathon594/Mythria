package me.Jonathon594.Mythria.Mixin;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapData.class)
public class MapDataMixin {
    @Inject(method = "Lnet/minecraft/world/storage/MapData;updateDecorations(Lnet/minecraft/world/storage/MapDecoration$Type;Lnet/minecraft/world/IWorld;Ljava/lang/String;DDDLnet/minecraft/util/text/ITextComponent;)V",
    at = @At(value = "HEAD"), cancellable = true)
    public void onUpdateDecoration(MapDecoration.Type type, IWorld worldIn, String decorationName, double worldX, double worldZ, double rotationIn, ITextComponent name, CallbackInfo ci) {
        if(type.equals(MapDecoration.Type.PLAYER)) ci.cancel();
    }
}
