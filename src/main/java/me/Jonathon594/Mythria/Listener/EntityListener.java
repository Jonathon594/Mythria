package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Entity.AI.AvoidGeneticGoal;
import me.Jonathon594.Mythria.Entity.MythriaEntityType;
import me.Jonathon594.Mythria.Entity.MythriaStriderEntity;
import me.Jonathon594.Mythria.Entity.NPCEntity;
import me.Jonathon594.Mythria.Entity.NetherChickenEntity;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityListener {
    @SubscribeEvent
    public static void onJump(final LivingEvent.LivingJumpEvent event) {
        if (!event.getEntity().world.isRemote) {
            if (event.getEntity() instanceof PlayerEntity) {
                final PlayerEntity player = (PlayerEntity) event.getEntity();
                BlessingManager.onJumpServer(player);
                final Profile p = ProfileProvider.getProfile(player);
                StatManager.onJump(player, p);
                if (p.getConsumables().get(Consumable.WEIGHT) > p.getStat(StatType.MAX_WEIGHT)) {
                    MythriaUtil.DropAllItems(player, false, true);
                    StatManager.updateCarryWeight(player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityConstructing(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity || entity instanceof NPCEntity) {
            LivingEntity player = (LivingEntity) entity;
            player.getDataManager().register(MythriaPlayer.PARRYING, false);
            player.getDataManager().register(MythriaPlayer.SKIN, SkinPartManager.getSkinPartsFor(SkinPart.Type.SKIN, 0, null).get(0).getResourceLocation().toString());
            player.getDataManager().register(MythriaPlayer.HAIR, SkinPartManager.getSkinPartsFor(SkinPart.Type.HAIR, 0, null).get(0).getResourceLocation().toString());
            player.getDataManager().register(MythriaPlayer.CLOTHES, SkinPartManager.getSkinPartsFor(SkinPart.Type.CLOTHING, 0, null).get(0).getResourceLocation().toString());
            player.getDataManager().register(MythriaPlayer.EYES, SkinPartManager.getSkinPartsFor(SkinPart.Type.EYES, 0, null).get(0).getResourceLocation().toString());
            player.getDataManager().register(MythriaPlayer.WINGS, "");
            player.getDataManager().register(MythriaPlayer.VINES, "");
            player.getDataManager().register(MythriaPlayer.SCALES, "");
        }
    }

    @SubscribeEvent
    public static void onEntityTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntity().world.isRemote) return;
        BlessingManager.onEntityTarget(event);

        LivingEntity entity = event.getEntityLiving();
        LivingEntity target = event.getTarget();
        LivingEntity revengeTarget = entity.getRevengeTarget();
        if (revengeTarget == null || !revengeTarget.equals(target)) {
            if (target instanceof PlayerEntity && entity instanceof MobEntity) {
                if (ProfileProvider.getProfile((PlayerEntity) target).getGenetic().isInTruceWith(entity.getType()))
                    ((MobEntity) entity).setAttackTarget(null);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        //Todo AbilityManager.handleCombatSkills(event);
        if (event.getEntity().getEntityWorld().isRemote)
            return;

        final Entity trueSource = event.getSource().getTrueSource();
        if (trueSource instanceof PlayerEntity && event.getSource().damageType.equalsIgnoreCase("player") ||
                event.getSource().damageType.equalsIgnoreCase("playeroffhand")) {
            final PlayerEntity source = (PlayerEntity) trueSource;
            final Profile sourceProfile = ProfileProvider.getProfile(source);
            //Todo AbilityManager.onLivingHurt(sourceProfile, event, source);
            BlessingManager.onPlayerAttackEntity(sourceProfile, event, source);

            event.getEntityLiving().hurtResistantTime = 0;
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event) {
        //If it was a Player attacked.
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            final Profile profile = ProfileProvider.getProfile(player);

            //Handle Blessings
            BlessingManager.onLivingHurt(player, profile, event);

            if (event.getEntityLiving() instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                if (ProfileProvider.getProfile(playerEntity).getGenetic().isImmune(event.getSource())) {
                    event.setCanceled(true);
                }
            }

            if (event.isCanceled()) return;

            //Trigger the players health data
            if (!player.world.isRemote) {
                profile.getHealthData().onDamaged(event, player, profile);
            }

            //Cancel Starvation damage because Mythria doesn't need it.
            if (event.getSource().equals(DamageSource.STARVE)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityConsumeItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            Profile profile = ProfileProvider.getProfile(player);
            profile.getHealthData().onConsumeItem(event, player, profile);
            NutritionManager.onConsume(event, player, profile);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        for (Genetic genetic : MythriaRegistries.GENETICS.getValues()) {
            if (genetic.getFleeingEntities().contains(entity.getType())) {
                CreatureEntity creatureEntity = (CreatureEntity) entity;
                creatureEntity.goalSelector.addGoal(-1, new AvoidGeneticGoal(creatureEntity, genetic));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityPickupItem(final EntityItemPickupEvent event) {
        if (event.getPlayer() != null) {
            LimitedInventoryManager.onPlayerPickupItem(event);
            FoodManager.onPlayerPickupItem(event);
            //PickupManager.onPlayerPickupItem(event);
        }
    }

}
