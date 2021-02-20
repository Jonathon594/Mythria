package me.Jonathon594.Mythria.Data;

import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Iterator;

public class MythriaLangGenerator extends LanguageProvider {
    public MythriaLangGenerator(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        Iterator<Item> itemIterator = ForgeRegistries.ITEMS.iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.getRegistryName().getNamespace().equals(Mythria.MODID)) {
                add(item, MythriaUtil.capitalizeWords(item.getRegistryName().getPath().replace("_", " ")));
            }
        }
    }
}
