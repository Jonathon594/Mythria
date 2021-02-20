package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Enum.EnumGlassShape;
import me.Jonathon594.Mythria.Interface.IBlowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class GlassIngotItem extends MythriaItem implements IBlowable {
    private final EnumGlassShape shape;

    public GlassIngotItem(String name, double weight, EnumGlassShape shape) {
        super(name, weight, new Item.Properties().group(ItemGroup.MATERIALS));
        this.shape = shape;
    }

    @Override
    public EnumGlassShape getShape() {
        return shape;
    }
}
