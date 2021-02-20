package me.Jonathon594.Mythria.Genetic;

import me.Jonathon594.Mythria.Ability.Abilities;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
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
                new Genetic("human", "Human", 14,
                        100, 0.0, 50, 4, 1.5, 10,
                        0, 74, 28, zero),

                new Genetic("elf", "Elf", 12,
                        80, 0.01, 45, -2, 1.0, 50,
                        0.5, 0, 28, zero)
                        .addGrantedAbility(Abilities.ELF_HEALING),

                new Genetic("orc", "Orc", 16,
                        140, -0.01, 120, 20, 0.8, 5,
                        0, 48, 36, zero),

                new Genetic("fae", "Fae", 11,
                        80, 0.02, 40, -10, 1.0, 80,
                        1, 284, 36, zero).setSpecialSkinPartType(SkinPart.Type.WINGS)
                        .addGrantedAbility(Abilities.FAE_FLIGHT)
                        .addLockedEquipSlot(EquipmentSlotType.CHEST)
                        .addLockedEquipSlot(EquipmentSlotType.LEGS)
                        .setCanGlide(true),

                new Genetic("dryad", "Dryad", 14,
                        120, 0.01, 80, 8, 1.0, 200,
                        -0.1, 649, Integer.MAX_VALUE, zero).setSpecialSkinPartType(SkinPart.Type.DRYAD_VINES)
                        .setGenderBias(1.0).addGrantedAbility(Abilities.DRYAD_GROWTH)
                        .addMobTruce(EntityType.WOLF, EntityType.POLAR_BEAR)
                        .addImmunity(DamageSource.CACTUS, DamageSource.SWEET_BERRY_BUSH),

                new Genetic("skaeren", "Skaeren", 20,
                        100, 0.01, 60, 0, 1.0, 40,
                        1, 210, 44, zero).setSpawnDimension(World.THE_NETHER)
                        .addImmunity(DamageSource.IN_FIRE, DamageSource.ON_FIRE, DamageSource.HOT_FLOOR, DamageSource.LAVA)
                        .addMobTruce(EntityType.GHAST, EntityType.BLAZE)
                        .setSpecialSkinPartType(SkinPart.Type.SKAEREN_SCALES)
                        .setWaterNeeded(false),

                new Genetic("katana", "Ka'Tana", 14,
                        300, 0.02, 60, -4, 0.85, 20,
                        1, 34, 16, zero)
                        .addFleeingEntity(EntityType.CREEPER)
        );
    }
}
