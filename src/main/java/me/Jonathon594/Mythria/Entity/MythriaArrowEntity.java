package me.Jonathon594.Mythria.Entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
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
        super(MythriaEntities.ARROW.get(), x, y, z, worldIn);
        dataManager.register(ARROW_STACK, ItemStack.EMPTY);
    }

    public MythriaArrowEntity(World worldIn, LivingEntity shooter) {
        super(MythriaEntities.ARROW.get(), shooter, worldIn);
        dataManager.register(ARROW_STACK, ItemStack.EMPTY);
    }

    public void setArrowStack(ItemStack arrowStack) {
        dataManager.set(ARROW_STACK, arrowStack);
    }

    @Override
    public ItemStack getArrowStack() {
        return dataManager.get(ARROW_STACK);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
