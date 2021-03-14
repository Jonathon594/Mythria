package me.Jonathon594.Mythria.Entity;

import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Skin.SkinParts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

import java.util.HashSet;

public class NPCEntity extends LivingEntity {
    public static final DataParameter<Boolean> PARRYING = EntityDataManager.createKey(NPCEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<String> HAIR = EntityDataManager.createKey(NPCEntity.class, DataSerializers.STRING);
    public static final DataParameter<String> SKIN = EntityDataManager.createKey(NPCEntity.class, DataSerializers.STRING);
    public static final DataParameter<String> CLOTHES = EntityDataManager.createKey(NPCEntity.class, DataSerializers.STRING);
    public static final DataParameter<String> EYES = EntityDataManager.createKey(NPCEntity.class, DataSerializers.STRING);
    public static final DataParameter<String> WINGS = EntityDataManager.createKey(NPCEntity.class, DataSerializers.STRING);

    public <T extends Entity> NPCEntity(World world) {
        this(MythriaEntityType.NPC, world);
    }

    public NPCEntity(EntityType<NPCEntity> entityType, World world) {
        super(entityType, world);
        getDataManager().register(PARRYING, false);
        getDataManager().register(SKIN, SkinParts.getSkinPartsFor(SkinPart.Type.SKIN).get(0).getTextureLocation(Gender.MALE).toString());
        getDataManager().register(HAIR, SkinParts.getSkinPartsFor(SkinPart.Type.HAIR).get(0).getTextureLocation(Gender.MALE).toString());
        getDataManager().register(CLOTHES, SkinParts.getSkinPartsFor(SkinPart.Type.CLOTHING).get(0).getTextureLocation(Gender.MALE).toString());
        getDataManager().register(EYES, SkinParts.getSkinPartsFor(SkinPart.Type.EYES).get(0).getTextureLocation(Gender.MALE).toString());
        getDataManager().register(WINGS, "");
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return new HashSet<>();
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }
}
