package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Genetic.GeneticType;
import me.Jonathon594.Mythria.Genetic.Serializers.GeneSerializer;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.SpawnGifts.SpawnGift;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryListener {
    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        new RegistryBuilder<Ability>().setName(new MythriaResourceLocation("abilities"))
                .setType(Ability.class).setMaxID(Integer.MAX_VALUE - 1).create();

        new RegistryBuilder<GeneticType>().setName(new MythriaResourceLocation("genetic_types"))
                .setType(GeneticType.class).setMaxID(Integer.MAX_VALUE - 1).setDefaultKey(new MythriaResourceLocation("human")).create();

        new RegistryBuilder<Perk>().setName(new MythriaResourceLocation("perks"))
                .setType(Perk.class).setMaxID(Integer.MAX_VALUE - 1).create();

        new RegistryBuilder<SkinPart>().setName(new MythriaResourceLocation("skin_parts"))
                .setType(SkinPart.class).setMaxID(Integer.MAX_VALUE - 1).create();

        new RegistryBuilder<GeneSerializer<?>>().setName(new MythriaResourceLocation("gene_serializers"))
                .setType(passTypedClass(GeneSerializer.class)).setMaxID(Integer.MAX_VALUE - 1).create();

        new RegistryBuilder<SpawnGift>().setName(new MythriaResourceLocation("spawn_gifts"))
                .setType(SpawnGift.class).setMaxID(Integer.MAX_VALUE - 1).create();
    }

    private static <T> Class<T> passTypedClass(Class<?> clazz) {
        return (Class<T>) clazz;
    }
}
