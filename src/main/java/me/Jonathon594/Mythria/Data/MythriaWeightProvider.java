package me.Jonathon594.Mythria.Data;

import me.Jonathon594.Mythria.Data.Provider.WeightProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class MythriaWeightProvider extends WeightProvider {
    public MythriaWeightProvider(DataGenerator generator, String modid) {
        super(generator, modid);
    }

    @Override
    protected void addWeights() {
        for (Item item : ForgeRegistries.ITEMS) {
            add(item, 1.0f);
        }
    }
}
