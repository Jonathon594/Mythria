package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Entity.MythriaArrowEntity;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MythriaArrowItem extends ArrowItem implements IItemData {
    private final double weight;

    public MythriaArrowItem(String name, double weight) {
        super(new Item.Properties().group(ItemGroup.COMBAT));
        setRegistryName(Mythria.MODID, name);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        MythriaArrowEntity arrowentity = new MythriaArrowEntity(worldIn, shooter);
        ItemStack copy = stack.copy();
        copy.setCount(1);
        arrowentity.setArrowStack(copy);
        return arrowentity;
    }
}
