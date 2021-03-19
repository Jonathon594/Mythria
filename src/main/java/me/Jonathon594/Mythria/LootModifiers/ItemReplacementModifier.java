package me.Jonathon594.Mythria.LootModifiers;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemReplacementModifier extends LootModifier {
    private final Item replace;
    private final Item with;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     * @param replace      the Item that needs replacing.
     * @param with         the Item to replace it with.
     */
    protected ItemReplacementModifier(ILootCondition[] conditionsIn, Item replace, Item with) {
        super(conditionsIn);
        this.replace = replace;
        this.with = with;
    }

    public static class Serializer extends GlobalLootModifierSerializer<ItemReplacementModifier> {
        @Override
        public ItemReplacementModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            Item replace = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(object, "replace"))));
            Item with = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "with")));
            return new ItemReplacementModifier(conditionsIn == null ? new ILootCondition[0] : conditionsIn, replace, with);
        }

        @Override
        public JsonObject write(ItemReplacementModifier instance) {
            return makeConditions(instance.conditions);
        }
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        List<ItemStack> toAdd = new ArrayList<>();
        for (ItemStack is : generatedLoot) {
            if (is.getItem() == replace)
                toAdd.add(new ItemStack(with, is.getCount()));
        }
        generatedLoot.removeIf(x -> x.getItem() == replace);
        generatedLoot.addAll(toAdd);
        return generatedLoot;
    }
}
