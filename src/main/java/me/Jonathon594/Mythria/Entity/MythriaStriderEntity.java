package me.Jonathon594.Mythria.Entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MythriaStriderEntity extends StriderEntity {
    public MythriaStriderEntity(EntityType<? extends StriderEntity> p_i231562_1_, World p_i231562_2_) {
        super(p_i231562_1_, p_i231562_2_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof PlayerEntity;
    }

    @Override
    public void travel(Vector3d travelVector) {
        this.stepHeight = 1F;
        this.setAIMoveSpeed(this.func_234316_eJ_());
        if (this.isAlive()) {
            if (this.isBeingRidden() && this.canBeSteered()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.renderYawOffset;
                float f = livingentity.moveStrafing * 0.5F;
                float f1 = livingentity.moveForward;

                if (this.canPassengerSteer()) {
                    this.setAIMoveSpeed(getMountedSpeed());
                    this.travelTowards(new Vector3d(f, travelVector.y, f1));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setMotion(Vector3d.ZERO);
                }
            } else {
                this.travelTowards(travelVector);
            }
        }
    }

    public float getMountedSpeed() {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) *
                (this.func_234315_eI_() ? 0.23F : (this.isHorseSaddled() ? 0.55F : 0.33F));
    }

    public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        boolean flag = this.isBreedingItem(p_230254_1_.getHeldItem(p_230254_2_));
        if (!flag && !this.isBeingRidden() && !p_230254_1_.isSecondaryUseActive()) {
            if (!this.world.isRemote) {
                p_230254_1_.startRiding(this);
            }

            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            ActionResultType actionresulttype = super.func_230254_b_(p_230254_1_, p_230254_2_);
            if (!actionresulttype.isSuccessOrConsume()) {
                ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
                return itemstack.getItem() == Items.SADDLE ? itemstack.interactWithEntity(p_230254_1_, this, p_230254_2_) : ActionResultType.PASS;
            } else {
                if (flag && !this.isSilent()) {
                    this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_STRIDER_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                }

                return actionresulttype;
            }
        }
    }

    public StriderEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return MythriaEntityType.STRIDER.create(p_241840_1_);
    }
}
