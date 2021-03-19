package me.Jonathon594.Mythria.Client.Renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Client.Model.CharacterModel;
import me.Jonathon594.Mythria.Entity.NPCEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;

public class NPCRenderer extends CharacterRenderer {
    public NPCRenderer(EntityRendererManager renderManager) {
        this(renderManager, false);
    }

    public NPCRenderer(EntityRendererManager renderManager, boolean useSmallArms) {
        super(renderManager, useSmallArms);
    }

    public void render(LivingEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public void renderArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, NPCEntity npcIn, HandSide side) {
        this.setModelVisibilities(npcIn);
        super.renderArm(matrixStackIn, bufferIn, combinedLightIn, npcIn, side);
    }

    private void setModelVisibilities(LivingEntity npc) {
        CharacterModel<LivingEntity> playermodel = this.getEntityModel();
        if (npc.isSpectator()) {
            playermodel.setVisible(false);
            playermodel.bipedHead.showModel = true;
            playermodel.bipedHeadwear.showModel = true;
        } else {
            ItemStack itemstack = npc.getHeldItemMainhand();
            ItemStack itemstack1 = npc.getHeldItemOffhand();
            playermodel.setVisible(true);
            playermodel.isSneak = npc.isCrouching();
            BipedModel.ArmPose bipedmodel$armpose = this.getArmPose(npc, itemstack, itemstack1, Hand.MAIN_HAND);
            BipedModel.ArmPose bipedmodel$armpose1 = this.getArmPose(npc, itemstack, itemstack1, Hand.OFF_HAND);
            if (npc.getPrimaryHand() == HandSide.RIGHT) {
                playermodel.rightArmPose = bipedmodel$armpose;
                playermodel.leftArmPose = bipedmodel$armpose1;
            } else {
                playermodel.rightArmPose = bipedmodel$armpose1;
                playermodel.leftArmPose = bipedmodel$armpose;
            }
        }
    }
}
