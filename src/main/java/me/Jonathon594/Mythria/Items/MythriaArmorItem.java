package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.Interface.ISlotData;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class MythriaArmorItem extends ArmorItem implements IItemData, ISlotData {
    private final double weight;
    private final int backpackSlots;
    private final int hotbarSlots;

    public MythriaArmorItem(String name, IArmorMaterial material, EquipmentSlotType slotType, double weight, int backpackSlots, int hotbarSlots) {
        super(material, slotType, new Item.Properties().group(ItemGroup.COMBAT));
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
        this.backpackSlots = backpackSlots;
        this.hotbarSlots = hotbarSlots;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getAdditionalBackpackSlots() {
        return backpackSlots;
    }

    @Override
    public int getAdditionalHotbarSlots() {
        return hotbarSlots;
    }
}
