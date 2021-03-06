package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Managers.SkinPartManager;
import me.Jonathon594.Mythria.Mythria;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Mythria.MODID)
public class Genetics {
    public static final Genetic HUMAN = null;
    public static final Genetic ELF = null;
    public static final Genetic ORC = null;
    public static final Genetic FAE = null;
    public static final Genetic DRYAD = null;
    public static final Genetic SKAEREN = null;
    public static final Genetic KATANA = null;

    @SubscribeEvent
    public static void onRegisterGenetics(RegistryEvent.Register<Genetic> event) {
        SpawnPos zero = SpawnPos.ZERO;
        event.getRegistry().registerAll(
                new Genetic("human", "Human", zero)
                        .withGene(new HairGene(SkinPartManager.HUMAN_CLOTHES_UNIS_PRIMITIVE, Gender.MALE))
                        .withGene(new EyesGene(SkinPartManager.HUMAN_EYES_MALE_0, Gender.MALE))
                        .withGene(new SkinGene(SkinPartManager.HUMAN_SKIN_UNIS_0, Gender.MALE))
                        .withGene(new DoubleStatGene(Gene.GeneType.HEALTH, 4))
                        .withGene(new DoubleStatGene(Gene.GeneType.STAMINA, 100))
                        .withGene(new DoubleStatGene(Gene.GeneType.SPEED, 0.0))
                        .withGene(new DoubleStatGene(Gene.GeneType.WEIGHT, 50))
                        .withGene(new DoubleStatGene(Gene.GeneType.INTELLIGENCE, 1.5))
                        .withGene(new DoubleStatGene(Gene.GeneType.MANA, 10))
                        .withGene(new DoubleStatGene(Gene.GeneType.MANA_REGEN, 0))
                        .withGene(new IntStatGene(Gene.GeneType.LIFESPAN, 74))
                        .withGene(new DoubleStatGene(Gene.GeneType.TEMPERATURE, 14))
                        .withGene(new DoubleStatGene(Gene.GeneType.GENDER_BIAS, 0.5))


//                new Genetic("elf", "Elf", 12,
//                        80, 0.01, 45, -2, 1.0, 50,
//                        0.5, 0, zero)
//                        .addGrantedAbility(Abilities.ELF_HEALING),
//
//                new Genetic("orc", "Orc", 16,
//                        140, -0.01, 120, 20, 0.8, 5,
//                        0, 48, zero),
//
//                new Genetic("fae", "Fae", 11,
//                        80, 0.02, 40, -10, 1.0, 80,
//                        1, 284, zero).setSpecialSkinPartType(SkinPart.Type.WINGS)
//                        .addGrantedAbility(Abilities.FAE_FLIGHT)
//                        .addLockedEquipSlot(EquipmentSlotType.CHEST)
//                        .addLockedEquipSlot(EquipmentSlotType.LEGS)
//                        .setCanGlide(true),
//
//                new Genetic("dryad", "Dryad", 14,
//                        120, 0.01, 80, 8, 1.0, 200,
//                        -0.1, 649, zero).setSpecialSkinPartType(SkinPart.Type.DRYAD_VINES)
//                        .setGenderBias(1.0).addGrantedAbility(Abilities.DRYAD_GROWTH)
//                        .addMobTruce(EntityType.WOLF, EntityType.POLAR_BEAR)
//                        .addImmunity(DamageSource.CACTUS, DamageSource.SWEET_BERRY_BUSH),
//
//                new Genetic("skaeren", "Skaeren", 20,
//                        100, 0.01, 60, 0, 1.0, 40,
//                        1, 210, zero).setSpawnDimension(World.THE_NETHER)
//                        .addImmunity(DamageSource.IN_FIRE, DamageSource.ON_FIRE, DamageSource.HOT_FLOOR, DamageSource.LAVA)
//                        .addMobTruce(EntityType.GHAST, EntityType.BLAZE),
//
//                new Genetic("katana", "Ka'Tana", 14,
//                        300, 0.02, 60, -4, 0.85, 20,
//                        1, 34, zero)
//                        .addFleeingEntity(EntityType.CREEPER)
        );
    }
}
