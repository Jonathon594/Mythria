package me.Jonathon594.Mythria.Util;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

public class CombatUtil {
    public static boolean canBlockDamage(LivingEntity livingEntity, DamageSource damageSource) {
        boolean blocking = livingEntity.isActiveItemStackBlocking();
        if (livingEntity instanceof PlayerEntity) {
            if(MythriaPlayerProvider.getMythriaPlayer(livingEntity).isParrying()) blocking = true;
        }
        if (!damageSource.isUnblockable() && blocking) {
            Vector3d Vector3d = damageSource.getDamageLocation();

            if (Vector3d != null) {
                Vector3d Vector3d1 = livingEntity.getLook(1.0F);
                Vector3d Vector3d2 = Vector3d.subtractReverse(new Vector3d(livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ())).normalize();
                Vector3d2 = new Vector3d(Vector3d2.x, 0.0D, Vector3d2.z);

                if (Vector3d2.dotProduct(Vector3d1) < 0.0D) {
                    return true;
                }
            }
        }

        return false;
    }
}
