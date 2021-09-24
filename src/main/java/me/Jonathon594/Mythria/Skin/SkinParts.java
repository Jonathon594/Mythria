package me.Jonathon594.Mythria.Skin;

import com.google.common.collect.ImmutableSet;
import me.Jonathon594.Mythria.DataTypes.GenderedSkinPart;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class SkinParts {
    public static final SkinPart CLOTHES_PRIMITIVE = null;
    public static final SkinPart SKAEREN_CLOTHES_PRIMITIVE = null;
    public static final SkinPart CLOTHES_NUDE = null;
    public static final SkinPart CLOTHES_SEASHELL_BRA = null;
    public static final SkinPart CLOTHES_SAERKI_SHIRTLESS = null;
    public static final SkinPart HUMAN_EYES_BROWN = null;
    public static final SkinPart HUMAN_EYES_BLUE = null;
    public static final SkinPart HUMAN_EYES_GREEN = null;
    public static final SkinPart HUMAN_HAIR_BLOND = null;
    public static final SkinPart HUMAN_HAIR_GINGER = null;
    public static final SkinPart HUMAN_HAIR_BROWN = null;
    public static final SkinPart HUMAN_HAIR_BLACK = null;
    public static final SkinPart FAE_HAIR_PINK = null;
    public static final SkinPart HUMAN_SKIN_WHITE = null;
    public static final SkinPart HUMAN_SKIN_TAN = null;
    public static final SkinPart HUMAN_SKIN_MEDIUM = null;
    public static final SkinPart HUMAN_SKIN_DARK = null;
    public static final SkinPart HUMAN_SKIN_BLACK = null;
    public static final SkinPart ORC_SKIN_LIGHT = null;
    public static final SkinPart ORC_SKIN_TAN = null;
    public static final SkinPart ORC_SKIN_MEDIUM = null;
    public static final SkinPart ORC_SKIN_DARK = null;
    public static final SkinPart FAE_WINGS_BLUE = null;
    public static final SkinPart FAE_WINGS_RED = null;
    public static final SkinPart FAE_WINGS_GREEN = null;
    public static final SkinPart FAE_WINGS_PINK = null;
    public static final SkinPart DRYAD_VINES_OAK = null;
    public static final SkinPart SAERKI_TAIL_BLUE = null;
    private static final ArrayList<SkinPart> skinParts = new ArrayList<>();

    public static List<SkinPart> getSkinPartsFor(SkinPart.Type type) {
        List<SkinPart> parts = new ArrayList<>();
        MythriaRegistries.SKIN_PARTS.getValues().forEach((skinPart -> {
            if (skinPart.getType().equals(type)) parts.add(skinPart);
        }));
        return parts;
    }

    @SubscribeEvent
    public static void onRegisterSkinParts(RegistryEvent.Register<SkinPart> event) {
        event.getRegistry().registerAll(
                //Clothes
                new SkinPart("Primitive", "clothes_primitive", SkinPart.Type.CLOTHING),
//                new SkinPart("Simple Chain", "human_clothes_unis_0", SkinPart.Type.CLOTHING, true, true),
//                new SkinPart("Peasant Dress", "human_clothes_female_0", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Blacksmith Robes", "human_clothes_unis_1", SkinPart.Type.CLOTHING, true, true),
//                new SkinPart("Black Cloak", "human_clothes_unis_2", SkinPart.Type.CLOTHING, true, true),

                new GenderedSkinPart("Primitive", "skaeren_clothes_primitive", SkinPart.Type.CLOTHING)
                        .setMakesPiglinsNeutral(true),

                new SkinPart("Nude", "clothes_nude", SkinPart.Type.CLOTHING),

                new SkinPart("Blue Seashell Bra", "clothes_seashell_bra", SkinPart.Type.CLOTHING)
                        .setAllowedGenders(ImmutableSet.of(Gender.FEMALE)),
                new SkinPart("Shirtless", "clothes_saerki_shirtless", SkinPart.Type.CLOTHING)
                        .withCustomTextureName("clothes_nude")
                        .setAllowedGenders(ImmutableSet.of(Gender.MALE)),

//                new SkinPart("Felixia", "deity_clothes_felixia", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Melinias", "deity_clothes_melinias", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Asana", "deity_clothes_asana", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Kasai", "deity_clothes_kasai", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Raika", "deity_clothes_raika", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Selina", "deity_clothes_selina", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Lilasia", "deity_clothes_lilasia", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Eliana", "deity_clothes_eliana", SkinPart.Type.CLOTHING, false, true),
//                new SkinPart("Mal", "deity_clothes_mal", SkinPart.Type.CLOTHING, false, true),

                new GenderedSkinPart("Brown", "human_eyes_brown", SkinPart.Type.EYES),
                new GenderedSkinPart("Blue", "human_eyes_blue", SkinPart.Type.EYES),
                new GenderedSkinPart("Green", "human_eyes_green", SkinPart.Type.EYES),

//                new SkinPart("Felixia", "deity_eyes_felixia", SkinPart.Type.EYES, false, true),
//                new SkinPart("Melinias", "deity_eyes_melinias", SkinPart.Type.EYES, false, true),
//                new SkinPart("Asana", "deity_eyes_asana", SkinPart.Type.EYES, false, true),
//                new SkinPart("Kasai", "deity_eyes_kasai", SkinPart.Type.EYES, false, true),
//                new SkinPart("Raika", "deity_eyes_raika", SkinPart.Type.EYES, false, true),
//                new SkinPart("Selina", "deity_eyes_selina", SkinPart.Type.EYES, false, true),
//                new SkinPart("Lilasia", "deity_eyes_lilasia", SkinPart.Type.EYES, false, true),
//                new SkinPart("Eliana", "deity_eyes_eliana", SkinPart.Type.EYES, false, true),
//                new SkinPart("Mal", "deity_eyes_mal", SkinPart.Type.EYES, false, true),

                //Hair
                new GenderedSkinPart("Blond", "human_hair_blond", SkinPart.Type.HAIR),
                new GenderedSkinPart("Ginger", "human_hair_ginger", SkinPart.Type.HAIR),
                new GenderedSkinPart("Brown", "human_hair_brown", SkinPart.Type.HAIR),
                new GenderedSkinPart("Black", "human_hair_black", SkinPart.Type.HAIR),
                new GenderedSkinPart("Pink", "fae_hair_pink", SkinPart.Type.HAIR)
                        .setAllowedGenders(ImmutableSet.of(Gender.FEMALE)),
                new GenderedSkinPart("Green", "fae_hair_green", SkinPart.Type.HAIR)
                        .setAllowedGenders(ImmutableSet.of(Gender.FEMALE)),
                new GenderedSkinPart("Purple", "fae_hair_purple", SkinPart.Type.HAIR)
                        .setAllowedGenders(ImmutableSet.of(Gender.FEMALE)),

//                new SkinPart("Felixia", "deity_hair_felixia", SkinPart.Type.HAIR, false, true),
//                new SkinPart("Melinias", "deity_hair_melinias", SkinPart.Type.HAIR, false, true),
//                new SkinPart("Asana", "deity_hair_asana", SkinPart.Type.HAIR, false, true),
//                new SkinPart("Kasai", "deity_hair_kasai", SkinPart.Type.HAIR, false, true),
//                new SkinPart("Raika", "deity_hair_raika", SkinPart.Type.HAIR, false, true),
//                new SkinPart("Selina", "deity_hair_selina", SkinPart.Type.HAIR, false, true),
//                new SkinPart("Lilasia", "deity_hair_lilasia", SkinPart.Type.HAIR, false, true),
//                new SkinPart("Mal", "deity_hair_mal", SkinPart.Type.HAIR, false, true),

                //Skin
                new SkinPart("White", "human_skin_white", SkinPart.Type.SKIN),
                new SkinPart("Tan", "human_skin_tan", SkinPart.Type.SKIN),
                new SkinPart("Medium", "human_skin_medium", SkinPart.Type.SKIN),
                new SkinPart("Dark", "human_skin_dark", SkinPart.Type.SKIN),
                new SkinPart("Black", "human_skin_black", SkinPart.Type.SKIN),

                new SkinPart("Light", "orc_skin_light", SkinPart.Type.SKIN),
                new SkinPart("Mocha", "orc_skin_tan", SkinPart.Type.SKIN),
                new SkinPart("Medium", "orc_skin_medium", SkinPart.Type.SKIN),
                new SkinPart("Dark", "orc_skin_dark", SkinPart.Type.SKIN),

                new SkinPart("Blue Wings", "fae_wings_blue", SkinPart.Type.WINGS),
                new SkinPart("Red Wings", "fae_wings_red", SkinPart.Type.WINGS),
                new SkinPart("Green Wings", "fae_wings_green", SkinPart.Type.WINGS),
//                new SkinPart("Pink Wings", "fae_wings_pink", SkinPart.Type.WINGS)
//                        .setAllowedGenders(ImmutableSet.of(Gender.FEMALE)),

                new SkinPart("Vines", "dryad_vines_oak", SkinPart.Type.DRYAD_VINES),

                new SkinPart("Blue Scales", "saerki_tail_blue", SkinPart.Type.SAERKI_TAIL)
        );
    }
}
