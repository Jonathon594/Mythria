package me.Jonathon594.Mythria.Items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.Jonathon594.Mythria.Client.Renderer.Items.DaggerItemRenderer;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class MythriaDaggerItem extends MythriaSwordItem {
    public MythriaDaggerItem(String name, IItemTier tier, double weight, Supplier<Item> toolHead) {
        super(name, tier, weight, new Item.Properties().setISTER(() -> DaggerItemRenderer::new), toolHead, false);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        final Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getAttackDamage(), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    public MeleeCombatManager getCombatManager() {
        return MeleeCombatManager.DAGGER_MANAGER;
    }

    @Override
    public AttributeFlag getFlagForParrying() {
        return AttributeFlag.DAGGER_ABILITY_PARRY;
    }

    @Override
    public Skill getUsageSkill() {
        return Skill.DAGGERS;
    }
}
