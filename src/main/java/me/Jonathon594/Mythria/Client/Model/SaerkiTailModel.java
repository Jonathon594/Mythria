package me.Jonathon594.Mythria.Client.Model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class SaerkiTailModel<E extends LivingEntity> extends SegmentedModel<E> {
    private ModelRenderer tail_upper;
    private ModelRenderer tail_middle;
    private ModelRenderer tail_lower;
    private ModelRenderer tail_end;
    private ModelRenderer flipper;

    public SaerkiTailModel() {
        super(RenderType::getEntityCutout);
        this.textureWidth = 32;
        this.textureHeight = 32;

        tail_upper = new ModelRenderer(this);
        tail_upper.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F, 0.0F, false);

        tail_middle = new ModelRenderer(this);
        tail_middle.setRotationPoint(0, 6, 0);
        tail_middle.setTextureOffset(0, 10)
                .addBox(-3.0F, 0.0F, -1.5F, 6.0F, 6.0F, 3.0F, 0.0F, false);
        tail_upper.addChild(tail_middle);

        tail_lower = new ModelRenderer(this);
        tail_lower.setRotationPoint(0, 6, 0);
        tail_lower.setTextureOffset(0, 19)
                .addBox(-2.0F, 0, -1.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);
        tail_middle.addChild(tail_lower);

        tail_end = new ModelRenderer(this);
        tail_end.setRotationPoint(0, 3, 0);
        tail_end.setTextureOffset(12, 21)
                .addBox(-1.0F, 0, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        tail_lower.addChild(tail_end);

        flipper = new ModelRenderer(this);
        flipper.setRotationPoint(0, 1, 0);
        flipper.setTextureOffset(0, 24).addBox(-5.0F, 0, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        tail_end.addChild(flipper);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.tail_upper);
    }

    @Override
    public void setRotationAngles(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.getTicksElytraFlying() > 0) {
            float elytraSpeedFactor = (float) Math.pow(entityIn.getMotion().lengthSquared()  / 0.2, 3);
            limbSwingAmount = Math.min(limbSwingAmount / elytraSpeedFactor, 1);
        }

        this.tail_upper.rotateAngleX = -0.05F * MathHelper.cos(ageInTicks * 0.3F) * limbSwingAmount;
        this.tail_middle.rotateAngleX = -0.1F * MathHelper.cos(ageInTicks * 0.3F) * limbSwingAmount;
        this.tail_lower.rotateAngleX = -0.2F * MathHelper.cos(ageInTicks * 0.3F) * limbSwingAmount;
        this.tail_end.rotateAngleX = -0.4F * MathHelper.cos(ageInTicks * 0.3F) * limbSwingAmount;
    }
}
