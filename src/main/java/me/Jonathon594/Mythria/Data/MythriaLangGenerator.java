package me.Jonathon594.Mythria.Data;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class MythriaLangGenerator extends LanguageProvider {
    public MythriaLangGenerator(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    public void add(Ability ability) {
        add("abilities." + ability.getRegistryName().getPath() + ".name",
                MythriaUtil.capitalizeWords(ability.getRegistryName().getPath().replace("_", " ")));
    }

    @Override
    protected void addTranslations() {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item.getRegistryName().getNamespace().equals(Mythria.MODID)) {
                add(item, MythriaUtil.capitalizeWords(item.getRegistryName().getPath().replace("_", " ")));
            }
        }

        for (Ability ability : MythriaRegistries.ABILITIES) {
            add(ability);
        }
    }
}
