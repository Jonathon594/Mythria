package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.EntityAttitudeGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.Entity.AI.AvoidGeneticGoal;
import me.Jonathon594.Mythria.Entity.NPCEntity;
import me.Jonathon594.Mythria.Enum.ControlMode;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityListener {
    @SubscribeEvent
    public static void onEntityConstructing(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity || entity instanceof NPCEntity) { //todo
            LivingEntity player = (LivingEntity) entity;
            player.getDataManager().register(MythriaPlayer.PARRYING, false);
            player.getDataManager().register(MythriaPlayer.SKIN, SkinParts.getSkinPartsFor(SkinPart.Type.SKIN).get(0));
            player.getDataManager().register(MythriaPlayer.HAIR, SkinParts.getSkinPartsFor(SkinPart.Type.HAIR).get(0));
            player.getDataManager().register(MythriaPlayer.CLOTHES, SkinParts.getSkinPartsFor(SkinPart.Type.CLOTHING).get(0));
            player.getDataManager().register(MythriaPlayer.EYES, SkinParts.getSkinPartsFor(SkinPart.Type.EYES).get(0));
            player.getDataManager().register(MythriaPlayer.WINGS, null);
            player.getDataManager().register(MythriaPlayer.VINES, null);
            player.getDataManager().register(MythriaPlayer.SCALES, null);
            player.getDataManager().register(MythriaPlayer.GENDER, Gender.MALE);
            player.getDataManager().register(MythriaPlayer.CONTROL_MODE, ControlMode.NORMAL);
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
        if (entity instanceof CreatureEntity) {
            CreatureEntity creatureEntity = (CreatureEntity) entity;
            creatureEntity.goalSelector.addGoal(-1, new AvoidGeneticGoal(creatureEntity));
        }
    }

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (MythriaConst.LILASIA_ENTITIES.contains(livingEntity.getType())) {
            World entityWorld = livingEntity.getEntityWorld();
            if (entityWorld.getDimensionType().getMoonPhase(
                    entityWorld.getWorldInfo().getDayTime()) != 0) {
                event.setResult(Event.Result.DENY);
            }
        }

        if(!(event.getEntityLiving() instanceof PlayerEntity)) {
            event.setResult(Event.Result.DENY);
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

    @SubscribeEvent
    public static void onEntityTarget(LivingSetAttackTargetEvent event) {
        if(event.getTarget() == null) return; //todo Fix TRUCE
        if (event.getEntity().world.isRemote) return;
        BlessingManager.onEntityTarget(event);

        LivingEntity entity = event.getEntityLiving();
        LivingEntity target = event.getTarget();
        LivingEntity revengeTarget = entity.getRevengeTarget();
        if (revengeTarget == null || !revengeTarget.equals(target)) {
            if (target instanceof PlayerEntity && entity instanceof MobEntity) {
                for (Gene gene : ProfileProvider.getProfile((PlayerEntity) target).getGenetic().getExtraGenes()) {
                    if (gene instanceof EntityAttitudeGene) {
                        EntityAttitudeGene entityAttitudeGene = (EntityAttitudeGene) gene;
                        if (entityAttitudeGene.getEntityTypes().contains(entity.getType()) &&
                                !entityAttitudeGene.getAttitude().equals(EntityAttitudeGene.Attitude.TRUCE)) {
                            ((MobEntity) entity).setAttackTarget(null);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onJump(final LivingEvent.LivingJumpEvent event) {
        if (!event.getEntity().world.isRemote) {
            if (event.getEntity() instanceof PlayerEntity) {
                final PlayerEntity player = (PlayerEntity) event.getEntity();
                BlessingManager.onJumpServer(player);
                final Profile p = ProfileProvider.getProfile(player);
                StatManager.onJump(player, p);
            }
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

}
