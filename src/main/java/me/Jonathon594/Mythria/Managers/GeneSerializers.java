package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Serializers.*;
import me.Jonathon594.Mythria.Mythria;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class GeneSerializers {
    public static final DoubleStatSerializer DOUBLE_STAT = null;
    public static final IntStatSerializer INT_STAT = null;
    public static final SkinPartSerializer SKIN_PART = null;
    public static final AbilitySerializer ABILITY = null;
    public static final SpecialAbilitySerializer SPECIAL_ABILITY = null;
    public static final FaeWingsSerializer FAE_WINGS = null;
    public static final SaerkiTailSerializer SAERKI_TAIL = null;
    public static final ImmunitySerializer IMMUNITY = null;
    public static final EntityRelationSerializer ENTITY_RELATION = null;
    public static final NutritionSerializer NUTRITION = null;
    public static final LifeStagesSerializer LIFESPAN = null;

    @SubscribeEvent
    public static void onRegisterSkinParts(RegistryEvent.Register<GeneSerializer<? extends Gene>> event) {
        event.getRegistry().registerAll(
                new DoubleStatSerializer("double_stat"),
                new IntStatSerializer("int_stat"),
                new SkinPartSerializer("skin_part"),
                new AbilitySerializer("ability"),
                new SpecialAbilitySerializer("special_ability"),
                new FaeWingsSerializer("fae_wings"),
                new SaerkiTailSerializer("saerki_tail"),
                new ImmunitySerializer("immunity"),
                new EntityRelationSerializer("entity_relation"),
                new NutritionSerializer("nutrition"),
                new LifeStagesSerializer("lifespan")
        );
    }
}
