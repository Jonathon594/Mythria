package me.Jonathon594.Mythria.Client.Model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BackpackModel extends AgeableModel {
    private ModelRenderer backpack = new ModelRenderer(32, 32, 0, 0);

    public BackpackModel() {
        backpack.addBox(-4.0F, 0.0F, 2.0F, 8, 9, 4);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(backpack);
    }

    @Override
    public void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.backpack.setRotationPoint(0.0F, 4.5F, 2.0F);
        if (entityIn.isSneaking()) {
            this.backpack.rotateAngleX = 0.5F;
        } else {
            this.backpack.rotateAngleX = 0.0f;
        }
    }
}
