package me.Jonathon594.Mythria.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class WeightManager {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final HashMap<IItemProvider, Float> weightMap = new HashMap<>();

    public static double getWeight(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return weightMap.containsKey(item) ? weightMap.get(item) : 0.0f;
    }

    public static void init() {
        String loc = "weight.json";
        InputStream in = Mythria.class.getClassLoader().getResourceAsStream(loc);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        JsonElement je = GSON.fromJson(reader, JsonElement.class);
        JsonObject json = je.getAsJsonObject();

        for (Item item : ForgeRegistries.ITEMS) {
            String memberName = item.getRegistryName().toString();
            if (json.has(memberName))
                weightMap.put(item, json.get(memberName).getAsFloat());
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
