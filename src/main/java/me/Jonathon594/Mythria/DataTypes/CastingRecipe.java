package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.EnumMetalShape;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;
import me.Jonathon594.Mythria.Managers.CastingManager;
import net.minecraft.item.Item;

public class CastingRecipe {
    private final EnumMetalShape shape;
    private final MythriaMaterial material;
    private final Item result;

    public CastingRecipe(EnumMetalShape shape, MythriaMaterial material, Item result) {
        this.shape = shape;
        this.material = material;
        this.result = result;
        CastingManager.addRecipe(this);
    }

    public MythriaMaterial getMaterial() {
        return material;
    }

    public Item getResult() {
        return result;
    }

    public EnumMetalShape getShape() {
        return shape;
    }
}
