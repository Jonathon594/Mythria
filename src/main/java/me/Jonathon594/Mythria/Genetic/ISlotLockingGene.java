package me.Jonathon594.Mythria.Genetic;

import net.minecraft.inventory.EquipmentSlotType;

import java.util.List;

public interface ISlotLockingGene {
    List<EquipmentSlotType> getLockedSlots();
}
