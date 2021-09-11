package me.Jonathon594.Mythria.Managers;

import com.google.gson.JsonObject;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

public class WeightManager {
    private static final HashMap<IItemProvider, Float> weightMap = new HashMap<>();

    public static float getWeight(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return weightMap.containsKey(item) ? weightMap.get(item) : 0.0f;
    }

    public static void init() {
        JsonObject json = MythriaUtil.loadJson("weight.json");

        for (Item item : ForgeRegistries.ITEMS) {
            String memberName = item.getRegistryName().toString();
            if (json.has(memberName))
                weightMap.put(item, json.get(memberName).getAsFloat());
        }
    }

}
