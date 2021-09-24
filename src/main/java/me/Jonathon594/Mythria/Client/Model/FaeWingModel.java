package me.Jonathon594.Mythria.Client.Model;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Client.Renderer.CharacterRenderer;
import me.Jonathon594.Mythria.Client.Renderer.QuaternionModelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Collections;

public class FaeWingModel extends AgeableModel<LivingEntity> {
    final QuaternionModelRenderer left_wing = new QuaternionModelRenderer(32, 32, 0, 0);
    final QuaternionModelRenderer right_wing = new QuaternionModelRenderer(32, 32, 0, 0);
    private final CharacterRenderer renderer;

    public FaeWingModel(CharacterRenderer renderer) {
        super(RenderType::getEntityTranslucent, false, 5.0f, 2.0f, 2.0f, 2.0f, 24.0f);
        left_wing.mirror = true;
        left_wing.addBox(0, -3, 0, 14, 20, 0);
        right_wing.addBox(-14, -3, 0, 14, 20, 0);
        this.renderer = renderer;
    }

    @Override
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        CharacterModel<LivingEntity> model = renderer.getEntityModel();
        left_wing.rotationPointX = 1;
        right_wing.rotationPointX = -1;

        boolean isElytraFlying = entityIn.getTicksElytraFlying() > 0;
        float elytraSpeedFactor = 1.0f;
        if (isElytraFlying) {
            elytraSpeedFactor = (float) Math.pow(entityIn.getMotion().lengthSquared() * 5, 3);
        }

        float idleRate = 0.1f;
        float minIdle = 0.2f;
        float minAngle = 30.0f / Math.max(elytraSpeedFactor, 1) + 5f;
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            if (playerEntity.abilities.isFlying) {
                idleRate = 0.5f;
                minIdle = 0.4f;
                minAngle = 28.0f;
            }
        }
        float idle = (float) (((Math.cos(ageInTicks * idleRate) / 2 + 0.5) * 45) * (Math.max(1 - limbSwingAmount, minIdle)));
        float limbSwingElytraFactor = Math.min(limbSwingAmount / Math.max(elytraSpeedFactor, 1), 1);
        float limbDeflection = 40 * limbSwingAmount;
        if (isElytraFlying) {
            MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(entityIn);
            int extraFlap = mythriaPlayer.getWingFlightFlapAngle();
            if (entityIn.isSprinting()) mythriaPlayer.setWingFlightFlapAngle(Math.min(extraFlap + 1, 20));
            else mythriaPlayer.setWingFlightFlapAngle(Math.max(extraFlap - 1, 0));
            float flap = 15 + (20 * (1 - limbSwingElytraFactor));
            idle = MathHelper.cos((limbSwing * 0.6662F + (float) Math.PI * 2) * 2) * (Math.max(flap * limbSwingElytraFactor, extraFlap));
            limbDeflection = 25 * limbSwingElytraFactor + 15 + extraFlap / 2f;
        }

        float wingAngle = minAngle + idle + limbDeflection;
        left_wing.quaternion = new Quaternion(Vector3f.YP, -wingAngle, true);

        right_wing.quaternion = new Quaternion(Vector3f.YP, wingAngle, true);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(left_wing, right_wing);
    }


}
