package me.Jonathon594.Mythria.Enum;

import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum MythriaItemTier implements IItemTier {
    PRIMITIVE(0, 40, 2.0f, 0.0f, 0, () -> Ingredient.EMPTY),
    BONE(0, 24, 3.0f, 2.5f, 8, () -> Ingredient.EMPTY),
    TIN(0, 59, 2.0f, 0f, 15, () -> {
        return Ingredient.fromItems(MythriaItems.TIN_INGOT);
    }),
    COPPER(1, 131, 3.0f, 1.0f, 5, () -> {
        return Ingredient.fromItems(MythriaItems.COPPER_INGOT);
    }),
    BRONZE(2, 200, 5.0f, 1.5f, 10, () -> {
        return Ingredient.fromItems(MythriaItems.BRONZE_INGOT);
    }),
    STEEL(2, 480, 7.0f, 2.5f, 14, () -> {
        return Ingredient.fromItems(MythriaItems.STEEL_INGOT);
    }),
    TITANIUM(3, 1000, 7.5f, 2.5f, 12, () -> {
        return Ingredient.fromItems(MythriaItems.TITANIUM_INGOT);
    }),
    TUNGSTEN(4, 1275, 7.8f, 2.8f, 8, () -> {
        return Ingredient.fromItems(MythriaItems.TUNGSTEN_INGOT);
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    MythriaItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return maxUses;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public int getEnchantability() {
        return enchantability;
    }

    public Ingredient getRepairMaterial() {
        return repairMaterial.getValue();
    }
}
