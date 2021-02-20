package me.Jonathon594.Mythria.Client.Renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Client.Model.CharacterModel;
import me.Jonathon594.Mythria.Client.Renderer.Layer.CharacterLayerRenderer;
import me.Jonathon594.Mythria.Client.Renderer.Layer.FaeWingLayer;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

public class CharacterRenderer extends LivingRenderer<LivingEntity, CharacterModel<LivingEntity>> {
    public CharacterRenderer(EntityRendererManager renderManager) {
        this(renderManager, false);
    }

    public CharacterRenderer(EntityRendererManager renderManager, boolean useSmallArms) {
        super(renderManager, new CharacterModel<>(0.0F, useSmallArms), 0.5F);
        this.addLayer(new CharacterLayerRenderer(this, SkinPart.Type.EYES));
        this.addLayer(new CharacterLayerRenderer(this, SkinPart.Type.SKAEREN_SCALES));
        this.addLayer(new CharacterLayerRenderer(this, SkinPart.Type.DRYAD_VINES));
        this.addLayer(new CharacterLayerRenderer(this, SkinPart.Type.CLOTHING));
        this.addLayer(new CharacterLayerRenderer(this, SkinPart.Type.HAIR));
        this.addLayer(new FaeWingLayer(this));
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new ArrowLayer<>(this));
        //this.addLayer(new Deadmau5HeadLayer(this));
        //this.addLayer(new CapeLayer(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new SpinAttackEffectLayer(this));
        //todo
    }

    public static ResourceLocation getCharacterLayeredTexture(LivingEntity entity, SkinPart.Type skin) {
        String name = MythriaPlayerProvider.getMythriaPlayer(entity).getSkinPart(skin);
        return name.isEmpty() ? null : new ResourceLocation(name);
    }

    @Override
    public ResourceLocation getEntityTexture(LivingEntity entity) {
        return getCharacterLayeredTexture(entity, SkinPart.Type.SKIN);
    }

    @Override
    protected void renderName(LivingEntity entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

    }

    protected void applyRotations(LivingEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        float f = entityLiving.getSwimAnimation(partialTicks);
        if (entityLiving.isElytraFlying()) {
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
            float f1 = (float) entityLiving.getTicksElytraFlying() + partialTicks;
            float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
            if (!entityLiving.isSpinAttacking()) {
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f2 * (-90.0F - entityLiving.rotationPitch)));
            }

            Vector3d vec3d = entityLiving.getLook(partialTicks);
            Vector3d vec3d1 = entityLiving.getMotion();
            double d0 = Entity.horizontalMag(vec3d1);
            double d1 = Entity.horizontalMag(vec3d);
            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (vec3d1.x * vec3d.x + vec3d1.z * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
                double d3 = vec3d1.x * vec3d.z - vec3d1.z * vec3d.x;
                matrixStackIn.rotate(Vector3f.YP.rotation((float) (Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
            float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.rotationPitch : -90.0F;
            float f4 = MathHelper.lerp(f, 0.0F, f3);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f4));
            if (entityLiving.isActualySwimming()) {
                matrixStackIn.translate(0.0D, -1.0D, 0.3F);
            }
        } else {
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        }
    }

    protected BipedModel.ArmPose getArmPose(LivingEntity entityIn, ItemStack itemStackMain, ItemStack itemStackOff, Hand handIn) {
        BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
        ItemStack itemstack = handIn == Hand.MAIN_HAND ? itemStackMain : itemStackOff;
        if (!itemstack.isEmpty()) {
            bipedmodel$armpose = BipedModel.ArmPose.ITEM;
            if (entityIn.getItemInUseCount() > 0) {
                UseAction useaction = itemstack.getUseAction();
                if (useaction == UseAction.BLOCK) {
                    bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
                } else if (useaction == UseAction.BOW) {
                    bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
                } else if (useaction == UseAction.SPEAR) {
                    bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
                } else if (useaction == UseAction.CROSSBOW && handIn == entityIn.getActiveHand()) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else {
                boolean flag3 = itemStackMain.getItem() == Items.CROSSBOW;
                boolean flag = CrossbowItem.isCharged(itemStackMain);
                boolean flag1 = itemStackOff.getItem() == Items.CROSSBOW;
                boolean flag2 = CrossbowItem.isCharged(itemStackOff);
                if (flag3 && flag) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
                }

                if (flag1 && flag2 && itemStackMain.getItem().getUseAction(itemStackMain) == UseAction.NONE) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
                }
            }
        }

        return bipedmodel$armpose;
    }

    public void renderArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, LivingEntity entityIn, HandSide side) {
        CharacterModel<LivingEntity> model = this.getEntityModel();
        model.swingProgress = 0.0F;
        model.isSneak = false;
        model.swimAnimation = 0.0F;
        model.setRotationAngles(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        ModelRenderer rendererArmIn = side == HandSide.RIGHT ? model.bipedRightArm : model.bipedLeftArm;
        ModelRenderer rendererArmwearIn = side == HandSide.RIGHT ? model.bipedRightArmwear : model.bipedLeftArmwear;
        rendererArmIn.rotateAngleX = 0.0F;
        rendererArmIn.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(getEntityTexture(entityIn))), combinedLightIn, OverlayTexture.NO_OVERLAY);
        rendererArmwearIn.rotateAngleX = 0.0F;
        rendererArmwearIn.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityTranslucent(getEntityTexture(entityIn))), combinedLightIn, OverlayTexture.NO_OVERLAY);
    }
}
