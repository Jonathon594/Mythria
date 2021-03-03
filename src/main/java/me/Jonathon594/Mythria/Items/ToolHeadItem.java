package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.DataTypes.CastingRecipe;
import me.Jonathon594.Mythria.Enum.EnumMetalShape;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.function.Supplier;

public class ToolHeadItem extends MythriaItem {
    private final Supplier<Item> result;

    public ToolHeadItem(String name, Supplier<Item> result, Properties properties) {
        super(name, properties.group(ItemGroup.TOOLS));
        this.result = result;
    }

    public Item getResult() {
        return result.get();
    }

    public ToolHeadItem createCastingRecipe(EnumMetalShape shape, MythriaMaterial material) {
        new CastingRecipe(shape, material, this);
        return this;
    }
}
