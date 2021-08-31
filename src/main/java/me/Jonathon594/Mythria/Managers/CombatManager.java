package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

public class CombatManager {
    public static void attackEntity(PlayerEntity player, Entity targetEntity, Hand hand, EnumAttackType type,
                                    AttackClass attackClass, boolean ignoreRangeChecks, boolean ignoreCooldownChecks) {
        if (targetEntity == null) return;
        if (!ForgeHooks.onPlayerAttackTarget(player, targetEntity)) return;
        if (targetEntity.canBeAttackedWithItem()) {
            if (!targetEntity.hitByEntity(player)) {
                float damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                float f1;

                //Mythria
                final ItemStack itemStack = hand == Hand.MAIN_HAND ? player.getHeldItemMainhand()
                        : player.getHeldItemOffhand();
                final EntityDamageSource ds = hand.equals(Hand.MAIN_HAND)
                        ? new EntityDamageSource("player", player)
                        : new EntityDamageSource("playeroffhand", player);
                final ItemStack itemStackOther = hand == Hand.MAIN_HAND ? player.getHeldItemOffhand()
                        : player.getHeldItemMainhand();

                if (player.fallDistance > 0.0F && !player.isOnGround() && !player.isOnLadder()
                        && !player.isInWater() && !player.isPotionActive(Effects.BLINDNESS) && player.getRidingEntity() == null
                        && targetEntity instanceof LivingEntity) {
                    type = EnumAttackType.AIR;
                } else if (player.isSprinting()) {
                    type = EnumAttackType.SPRINT;
                } else if (player.isSneaking()) {
                    type = EnumAttackType.SNEAK;
                }

                MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);
                double attackRange = getAttackRangeByWeapon(itemStack);
                CombatEvent.Pre pre = new CombatEvent.Pre(damage, ds, type, targetEntity, player, ProfileProvider.getProfile(player), itemStack, itemStackOther, hand, attackRange, attackClass);
                MinecraftForge.EVENT_BUS.post(pre);

                if (pre.isFail()) {
                    type = EnumAttackType.BASIC;
                }

                attackRange = Math.pow(pre.getAttackRange(), 2);
                if (!player.canEntityBeSeen(targetEntity)) attackRange /= 4;

                if (!ignoreRangeChecks && player.getDistanceSq(targetEntity) >= attackRange) {
                    return;
                }

                boolean airSmash = false;
                if (!targetEntity.isOnGround() && !targetEntity.isInWater() && targetEntity.getRidingEntity() == null
                        && targetEntity instanceof LivingEntity && pre.isAirSmash()) {
                    damage *= 2.5;
                    airSmash = true;
                }

                if (targetEntity instanceof LivingEntity) {
                    f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((LivingEntity) targetEntity).getCreatureAttribute());
                } else {
                    f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
                }

                float f2 = player.getCooledAttackStrength(0.5F);
                damage = damage * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;
                player.getAttributeManager().reapplyModifiers(itemStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
                player.resetCooldown();
                if (damage > 0.0F || f1 > 0.0F) {
                    boolean flag = f2 > 0.9F;
                    boolean flag1 = false;

                    int knockBack = 0;
                    knockBack = knockBack + EnchantmentHelper.getKnockbackModifier(player);

//                    if (player.isSprinting() && flag) {
//                        player.world.playSound((PlayerEntity) null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, player.getSoundCategory(), 1.0F, 1.0F);
//                        ++i;
//                        flag1 = true;
//                    }

                    //Mythria
                    boolean isHeavy = attackClass.equals(AttackClass.HEAVY);
                    boolean isCrit = pre.isForceCrit() || isHeavy;
                    float critModifier = isCrit ? (isHeavy ? 1.75F : 1.5F) : 1.0F;
                    final CriticalHitEvent hitResult = ForgeHooks
                            .getCriticalHit(player, targetEntity, isCrit, critModifier);
                    isCrit = hitResult != null;
                    if (isCrit)
                        damage *= hitResult.getDamageModifier();

//                    boolean flag2 = flag && player.fallDistance > 0.0F && !player.isOnGround() && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Effects.BLINDNESS) && !player.isPassenger() && targetEntity instanceof LivingEntity;
//                    flag2 = flag2 && !player.isSprinting();
//                    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
//                    flag2 = hitResult != null;
//                    if (flag2) {
//                        damage *= hitResult.getDamageModifier();
//                    }

                    damage = damage + f1;
                    boolean shouldSweep = false;

//                    double d0 = (double) (player.distanceWalkedModified - player.prevDistanceWalkedModified);
//                    if (flag && !flag2 && !flag1 && player.isOnGround() && d0 < (double) player.getAIMoveSpeed()) {
//                        ItemStack itemstack = player.getHeldItem(Hand.MAIN_HAND);
//                        if (itemstack.getItem() instanceof SwordItem) {
//                            shouldSweep = true;
//                        }
//                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(player);
                    if (targetEntity instanceof LivingEntity) {
                        f4 = ((LivingEntity) targetEntity).getHealth();
                        if (j > 0 && !targetEntity.isBurning()) {
                            flag4 = true;
                            targetEntity.setFire(1);
                        }
                    }

                    Vector3d vector3d = targetEntity.getMotion();
                    boolean shouldAttack = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                    if (shouldAttack) {
                        CombatEvent.Post post = new CombatEvent.Post(damage, ds, type, targetEntity, player, ProfileProvider.getProfile(player), itemStack, itemStackOther, knockBack, hand, attackClass);

//                        if (targetEntity instanceof EntityPlayer) {
//                            AbilityManager.applyTorpidity(ProfileProvider.getProfile((EntityPlayer) targetEntity), itemStack.getItem(), hitInfo.getTorpidity() * post.getTorpidityMultiplier());
//                        } todo

                        if (!MinecraftForge.EVENT_BUS.post(post)) {
                            knockBack = post.getKnockBack();
                            shouldSweep = post.isShouldSweep();
                        }

                        if (knockBack > 0) {
                            if (targetEntity instanceof LivingEntity) {
                                ((LivingEntity) targetEntity).applyKnockback((float) knockBack * 0.5F, MathHelper.sin(player.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(player.rotationYaw * ((float) Math.PI / 180F)));
                            } else {
                                targetEntity.addVelocity(-MathHelper.sin(player.rotationYaw * ((float) Math.PI / 180F)) * (float) knockBack * 0.5F, 0.1D, MathHelper.cos(player.rotationYaw * ((float) Math.PI / 180F)) * (float) knockBack * 0.5F);
                            }

                            player.setMotion(player.getMotion().mul(0.6D, 1.0D, 0.6D));
                            player.setSprinting(false);
                        }

                        int knockup = post.getKnockUp();
                        if (knockup > 0) {
                            targetEntity.addVelocity(0, 0.1 * knockup, 0);
                        }

                        if (shouldSweep) {
                            triggerSweep(player, targetEntity, ds, damage);
                        }

                        if (targetEntity instanceof ServerPlayerEntity && targetEntity.velocityChanged) {
                            ((ServerPlayerEntity) targetEntity).connection.sendPacket(new SEntityVelocityPacket(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.setMotion(vector3d);
                        }

                        if (isCrit) {
                            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
                            player.onCriticalHit(targetEntity);
                        }

                        if (!isCrit && !shouldSweep) {
                            if (flag) {
                                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, player.getSoundCategory(), 1.0F, 1.0F);
                            } else {
                                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, player.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }

                        if (f1 > 0.0F) {
                            player.onEnchantmentCritical(targetEntity);
                        }

                        player.setLastAttackedEntity(targetEntity);
                        if (targetEntity instanceof LivingEntity) {
                            EnchantmentHelper.applyThornEnchantments((LivingEntity) targetEntity, player);
                        }

                        EnchantmentHelper.applyArthropodEnchantments(player, targetEntity);
                        ItemStack itemstack1 = player.getHeldItemMainhand();
                        Entity entity = targetEntity;
                        if (targetEntity instanceof PartEntity) {
                            entity = ((PartEntity<?>) targetEntity).getParent();
                        }

                        if (!player.world.isRemote && !itemstack1.isEmpty() && entity instanceof LivingEntity) {
                            ItemStack copy = itemstack1.copy();
                            itemstack1.hitEntity((LivingEntity) entity, player);
                            if (itemstack1.isEmpty()) {
                                ForgeEventFactory.onPlayerDestroyItem(player, copy, Hand.MAIN_HAND);
                                player.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }

                        if (targetEntity instanceof LivingEntity) {
                            float f5 = f4 - ((LivingEntity) targetEntity).getHealth();
                            player.addStat(Stats.DAMAGE_DEALT, Math.round(f5 * 10.0F));
                            if (j > 0) {
                                targetEntity.setFire(j * 4);
                            }

                            if (player.world instanceof ServerWorld && f5 > 2.0F) {
                                int k = (int) ((double) f5 * 0.5D);
                                ((ServerWorld) player.world).spawnParticle(ParticleTypes.DAMAGE_INDICATOR, targetEntity.getPosX(), targetEntity.getPosYHeight(0.5D), targetEntity.getPosZ(), k, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }

                        //player.addExhaustion(0.1F);
                    } else {
                        player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory(), 1.0F, 1.0F);
                        if (flag4) {
                            targetEntity.extinguish();
                        }
                    }

                    targetEntity.hurtResistantTime = 0;
                }
            }
        }
    }

    public static void triggerSweep(PlayerEntity player, Entity targetEntity, DamageSource source, float damage) {
        float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * damage;

        for (LivingEntity livingentity : player.world.getEntitiesWithinAABB(LivingEntity.class, targetEntity.getBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
            if (livingentity != player && livingentity != targetEntity && !player.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).hasMarker()) && player.getDistanceSq(livingentity) < 9.0D) {
                livingentity.applyKnockback(0.4F, MathHelper.sin(player.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(player.rotationYaw * ((float) Math.PI / 180F)));
                livingentity.attackEntityFrom(source, f3);
            }
        }

        player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
        player.spawnSweepParticles();
    }

    private static double getAttackRangeByWeapon(ItemStack itemStack) {
        double attackRange = 3.0;
        Item item = itemStack.getItem();
        if (item instanceof ToolItem) attackRange = 3.3;
        if (MythriaUtil.isAxe(item)) attackRange = 3.5;
        if (MythriaUtil.isDagger(item)) attackRange = 3.1;
        if (MythriaUtil.isSword(item)) attackRange = 3.9;
        if (MythriaUtil.isHammer(item)) attackRange = 3.3;
        return attackRange;
    }
}
