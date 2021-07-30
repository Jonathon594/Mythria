package me.Jonathon594.Mythria.DataTypes.Genetic.Gene;

import net.minecraft.inventory.EquipmentSlotType;

import java.util.List;

public interface ISlotLockingGene {
    List<EquipmentSlotType> getLockedSlots();
}
