package me.Jonathon594.Mythria.Enum;

import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

public enum MythriaArmorMaterial implements IArmorMaterial {
    PRIMITIVE(Mythria.MODID + ":primitive", 3, new int[]{0, 0, 0, 0}, 2, SoundEvents.BLOCK_WOOL_PLACE, 0.0f, 0.0f, () -> {
        return Ingredient.fromItems(Items.LEATHER);
    }),

    TIN(Mythria.MODID + ":tin", 8, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, () -> {
        return Ingredient.fromItems(MythriaItems.TIN_INGOT);
    }),

    COPPER(Mythria.MODID + ":copper", 10, new int[]{1, 3, 4, 1}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, () -> {
        return Ingredient.fromItems(MythriaItems.COPPER_INGOT);
    }),

    BRONZE(Mythria.MODID + ":bronze", 12, new int[]{1, 4, 5, 1}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, () -> {
        return Ingredient.fromItems(MythriaItems.BRONZE_INGOT);
    }),

    STEEL(Mythria.MODID + ":steel", 18, new int[]{2, 5, 6, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, () -> {
        return Ingredient.fromItems(MythriaItems.STEEL_INGOT);
    }),

    TITANIUM(Mythria.MODID + ":titanium", 24, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
        return Ingredient.fromItems(MythriaItems.TITANIUM_INGOT);
    }),

    TUNGSTEN(Mythria.MODID + ":tungsten", 30, new int[]{3, 6, 7, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0f, 0.0f, () -> {
        return Ingredient.fromItems(MythriaItems.TUNGSTEN_INGOT);
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    MythriaArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }


    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
