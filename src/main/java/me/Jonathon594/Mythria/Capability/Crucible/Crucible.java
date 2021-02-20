package me.Jonathon594.Mythria.Capability.Crucible;

import me.Jonathon594.Mythria.DataTypes.MetallurgyRecipe;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class Crucible implements ICrucible {
    private final ItemStackHandler oreInventory = new ItemStackHandler(10);
    private MythriaMaterial material = null;
    private int amount = 0;

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("inventory", oreInventory.serializeNBT());
        nbt.putString("material", material == null ? "" : material.toString());
        nbt.putInt("amount", amount);
        return nbt;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        oreInventory.deserializeNBT(nbt.getCompound("inventory"));
        String material = nbt.getString("material");
        if (!material.isEmpty()) this.material = MythriaMaterial.valueOf(material);
        amount = nbt.getInt("amount");
    }

    @Override
    public ItemStackHandler getOreInventory() {
        return oreInventory;
    }

    public void createMetal() {
        if (hasMeltingContents()) return;
        for (MetallurgyRecipe r : SmeltingManager.getMetalRecipes()) {
            if (r.matches(oreInventory)) {
                int ore = 0;
                for (int i = 0; i < oreInventory.getSlots(); i++) {
                    if (oreInventory.getStackInSlot(i).isEmpty()) continue;
                    ore++;
                }
                for (int i = 0; i < oreInventory.getSlots(); i++) {
                    oreInventory.setStackInSlot(i, ItemStack.EMPTY);
                }
                material = r.getMaterial();

                amount = ore * 100;
            }
        }
    }

    public MythriaMaterial getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean hasMeltingContents() {
        return material != null;
    }
}
