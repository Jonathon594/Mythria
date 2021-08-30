package me.Jonathon594.Mythria.Entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MythriaArrowEntity extends AbstractArrowEntity {
    public static final DataParameter<ItemStack> ARROW_STACK = EntityDataManager.createKey(MythriaArrowEntity.class, DataSerializers.ITEMSTACK);

    public MythriaArrowEntity(EntityType<? extends MythriaArrowEntity> type, World worldIn) {
        super(type, worldIn);
        dataManager.register(ARROW_STACK, ItemStack.EMPTY);
    }

    public MythriaArrowEntity(World worldIn, double x, double y, double z) {
        super(MythriaEntityType.ARROW, x, y, z, worldIn);
        dataManager.register(ARROW_STACK, ItemStack.EMPTY);
    }

    public MythriaArrowEntity(World worldIn, LivingEntity shooter) {
        super(MythriaEntityType.ARROW, shooter, worldIn);
        dataManager.register(ARROW_STACK, ItemStack.EMPTY);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("stack", getArrowStack().serializeNBT());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setArrowStack(ItemStack.read(compound.getCompound("stack")));
    }

    @Override
    public ItemStack getArrowStack() {
        return dataManager.get(ARROW_STACK);
    }

    public void setArrowStack(ItemStack arrowStack) {
        dataManager.set(ARROW_STACK, arrowStack);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
