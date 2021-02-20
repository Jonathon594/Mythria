package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.BlessingType;
import me.Jonathon594.Mythria.Enum.Deity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BlessingManager {
    public static void onLivingHurt(PlayerEntity player, Profile profile, LivingAttackEvent event) {
        DamageSource ds = event.getSource();

        if (ds.equals(DamageSource.DROWN)) {
            if (player.isInWater()) {
                if (BlessingManager.hasBlessing(profile, BlessingType.MELINIAS_WATER_BREATHING, player)) {
                    event.setCanceled(true);
                }
            } else if (BlessingManager.hasBlessing(profile, BlessingType.ELIANA_BREATHING, player)) {
                event.setCanceled(true);
            }
        }
        if (ds.equals(DamageSource.WITHER)) {
            if (BlessingManager.hasBlessing(profile, BlessingType.FELIXIA_NO_WITHER, player)) {
                event.setCanceled(true);
            }
        }
        if (ds.equals(DamageSource.FALL)) {
            if (BlessingManager.hasBlessing(profile, BlessingType.ELIANA_NO_FALL, player)) {
                event.setCanceled(true);
            }
        }
        if (ds.equals(DamageSource.LIGHTNING_BOLT)) {
            if (BlessingManager.hasBlessing(profile, BlessingType.RAIKA_SMITE, player)) {
                event.setCanceled(true);
            }
        }
        if (ds.equals(DamageSource.LAVA) || ds.equals(DamageSource.IN_FIRE) || ds.equals(DamageSource.ON_FIRE) || ds.equals(DamageSource.HOT_FLOOR)) {
            if (BlessingManager.hasBlessing(profile, BlessingType.KASAI_NO_FIRE, player)) {
                event.setCanceled(true);
            }
        }
        if (ds.damageType.contains("explosion")) {
            if (BlessingManager.hasBlessing(profile, BlessingType.ASANA_NO_EXLODE, player)) {
                event.setCanceled(true);
            }
        }
    }

    public static boolean hasBlessing(Profile profile, BlessingType blessingType, PlayerEntity player) {
        if (profile == null) return false;
        if (!blessingType.equals(BlessingType.MAL_NULLIFY)) {
            for (PlayerEntity other : player.world.getEntitiesWithinAABB(PlayerEntity.class,
                    new AxisAlignedBB(
                            player.getPosition().add(-16, -16, -16),
                            player.getPosition().add(16, 16, 16)))) {
                if (other.getPositionVec().distanceTo(player.getPositionVec()) > 5) continue;
                if (other.equals(player)) continue;
                Profile otherProfile = ProfileProvider.getProfile(other);
                if (BlessingManager.hasBlessing(otherProfile, BlessingType.MAL_NULLIFY, other)) {
                    return false;
                }
            }
        }

        switch (blessingType) {
            case ELIANA_FLIGHT:
                return profile.getFavorLevels().get(Deity.ELIANA) >= 1000000;
            case FELIXIA_NO_WITHER:
                return profile.getFavorLevels().get(Deity.FELIXIA) >= 10000;
            case FELIXIA_HEALING:
                return profile.getFavorLevels().get(Deity.FELIXIA) >= 100000;
            case FELIXIA_INTIMIDATION:
                return profile.getFavorLevels().get(Deity.FELIXIA) >= 1000000;
            case FELIXIA_BURN:
                return profile.getFavorLevels().get(Deity.FELIXIA) <= -1000000;
            case ELIANA_NO_FALL:
                return profile.getFavorLevels().get(Deity.ELIANA) >= 100000;
            case ELIANA_BREATHING:
                return profile.getFavorLevels().get(Deity.ELIANA) >= 10000;
            case ELIANA_SUFFOCATE:
                return profile.getFavorLevels().get(Deity.ELIANA) <= -1000000;
            case SELINA_PROSPERITY:
                return profile.getFavorLevels().get(Deity.SELINA) >= 100000;
            case SELINA_IMMORTALITY:
                return profile.getFavorLevels().get(Deity.SELINA) >= 1000000;
            case SELINA_INFERTILITY:
                return profile.getFavorLevels().get(Deity.SELINA) <= -1000000;
            case RAIKA_SPEED:
                return profile.getFavorLevels().get(Deity.RAIKA) >= 10000;
            case RAIKA_ELECTROCUTE:
                return profile.getFavorLevels().get(Deity.RAIKA) >= 100000;
            case RAIKA_SMITE:
                return profile.getFavorLevels().get(Deity.RAIKA) >= 1000000;
            case RAIKA_WRATH:
                return profile.getFavorLevels().get(Deity.RAIKA) <= -1000000;
            case MELINIAS_NO_MOBS:
                return profile.getFavorLevels().get(Deity.MELINIAS) >= 10000;
            case MELINIAS_WATER_BREATHING:
                return profile.getFavorLevels().get(Deity.MELINIAS) >= 100000;
            case MELINIAS_WATER_JET:
                return profile.getFavorLevels().get(Deity.MELINIAS) >= 1000000;
            case MELINIAS_WATER_CURSE:
                return profile.getFavorLevels().get(Deity.MELINIAS) <= -1000000;
            case KASAI_NO_MOBS:
                return profile.getFavorLevels().get(Deity.KASAI) >= 10000;
            case KASAI_NO_FIRE:
                return profile.getFavorLevels().get(Deity.KASAI) >= 100000;
            case KASAI_LAVA_JET:
                return profile.getFavorLevels().get(Deity.KASAI) >= 1000000;
            case KASAI_NETHER_CURSE:
                return profile.getFavorLevels().get(Deity.KASAI) <= -1000000;
            case ASANA_NO_MOBS:
                return profile.getFavorLevels().get(Deity.ASANA) >= 10000;
            case ASANA_NO_EXLODE:
                return profile.getFavorLevels().get(Deity.ASANA) >= 100000;
            case ASANA_EARTH_CRUMPLE:
                return profile.getFavorLevels().get(Deity.ASANA) >= 1000000;
            case ASANA_EARTH_POISON:
                return profile.getFavorLevels().get(Deity.ASANA) <= -1000000;
            case LILASIA_NO_MOBS:
                return profile.getFavorLevels().get(Deity.LILASIA) >= 10000;
            case LILASIA_SHADOW_HEALING:
                return profile.getFavorLevels().get(Deity.LILASIA) >= 100000;
            case LILASIA_REINFORCEMENTS:
                return profile.getFavorLevels().get(Deity.LILASIA) >= 1000000;
            case LILASIA_SHADOW_CURSE:
                return profile.getFavorLevels().get(Deity.LILASIA) <= -1000000;
            case SELINA_NO_NATURE_MOBS:
                return profile.getFavorLevels().get(Deity.SELINA) >= 10000;
            case MAL_CREATION:
                return profile.getFavorLevels().get(Deity.MAL) >= 1000000;
            case MAL_NULLIFY:
                return profile.getFavorLevels().get(Deity.MAL) >= 100000;

        }
        return false;
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event, Profile profile) {
        PlayerEntity player = event.player;
        if (!player.isAlive()) return;
        long tick = player.world.getDayTime();

        handleWaterJet(player, profile);
        handleLavaJet(player, profile);

        if (System.currentTimeMillis() % 1000 < 50) { //Every Second
            handleElytra(player, profile);

            if (BlessingManager.hasBlessing(profile, BlessingType.FELIXIA_HEALING, player)) {
                if (player.world.getLight(player.getPosition()) > 13 && player.world.canBlockSeeSky(player.getPosition())) {
                    if (player.getHealth() < player.getMaxHealth()) {
                        player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth()));
                    }
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.FELIXIA_BURN, player)) {
                if (player.world.isDaytime() && player.world.canBlockSeeSky(player.getPosition())) {
                    player.setFire(5);
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.ELIANA_SUFFOCATE, player)) { //Broken
                if (player.getPosY() > 92) {
                    player.attackEntityFrom(new DamageSource("suffocation").setDamageBypassesArmor(), 1);
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.RAIKA_WRATH, player)) {
                if (player.world.canBlockSeeSky(player.getPosition())) {
                    spawnLightningBolt(player.getPositionVec(), player.world);
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.MELINIAS_WATER_CURSE, player)) {
                if (player.isInWater()) {
                    player.addVelocity(0, -1, 0);
                    player.velocityChanged = true;
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.KASAI_NETHER_CURSE, player)) {
                if (player.world.getDimensionKey().equals(World.THE_NETHER)) {
                    player.setHealth(player.getHealth() - 1);
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.ASANA_EARTH_POISON, player)) {
                if (isStone(player.world.getBlockState(player.getPosition().down()))) {
                    player.setHealth(player.getHealth() - 1);
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.LILASIA_SHADOW_HEALING, player)) {
                if (player.world.getLight(player.getPosition()) == 0 && !player.world.canBlockSeeSky(player.getPosition())) {
                    if (player.getHealth() < player.getMaxHealth()) {
                        player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth()));
                    }
                }
            }

            if (BlessingManager.hasBlessing(profile, BlessingType.LILASIA_SHADOW_CURSE, player)) {
                if (player.world.getLight(player.getPosition()) < 8) {
                    player.setHealth(player.getHealth() - 1);
                }
            }
        }
    }

    private static void spawnLightningBolt(Vector3d vector3d, World world) {
        LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(world);
        lightningBoltEntity.moveForced(vector3d);
        world.addEntity(lightningBoltEntity);
    }

    private static void handleWaterJet(PlayerEntity player, Profile profile) {
        if (BlessingManager.hasBlessing(profile, BlessingType.MELINIAS_WATER_JET, player)) {
            if (player.isInWater() && player.isSprinting()) {
                Vector3d vec = player.getLookVec().scale(0.2);
                player.addVelocity(vec.x, vec.y, vec.z);
                player.velocityChanged = true;
            }
        }
    }

    private static void handleLavaJet(PlayerEntity player, Profile profile) {
        if (BlessingManager.hasBlessing(profile, BlessingType.KASAI_LAVA_JET, player)) {
            if (player.isInLava() && player.isSprinting()) {
                Vector3d vec = player.getLookVec().scale(0.2);
                player.addVelocity(vec.x, vec.y, vec.z);
                player.velocityChanged = true;
            }
        }
    }

    private static void handleElytra(PlayerEntity player, Profile profile) {
        if (BlessingManager.hasBlessing(profile, BlessingType.ELIANA_FLIGHT, player)) {
            ItemStack elytra = getElianaElytra();
            ItemStack armorSlot = player.inventory.getStackInSlot(38);
            if (!armorSlot.isItemEqual(elytra)) {
                InventoryHelper.spawnItemStack(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), armorSlot.copy());
                player.inventory.setInventorySlotContents(38, elytra);
            }

            if (player.isElytraFlying()) {
                if (player.getFoodStats().getFoodLevel() > 6) {
                    if (player.getPosY() < 345) {
                        Vector3d vel = new Vector3d(player.getMotion().x, player.getMotion().y, player.getMotion().z);
                        if (!player.isSneaking()) {
                            Vector3d vec = new Vector3d(player.getLookVec().x, 1, player.getLookVec().z).scale(0.2);
                            player.addVelocity(vec.x, vec.y, vec.z);
                        } else if (vel.length() > 0.5) {
                            Vector3d neg = vel.scale(-0.5);
                            player.addVelocity(neg.x, neg.y, neg.z);
                        } else {
                            player.addVelocity(0, 0.4, 0);
                        }
                        player.velocityChanged = true;
                    }
                }
            }
        }
    }

    private static ItemStack getElianaElytra() {
        ItemStack elytra = new ItemStack(Items.ELYTRA, 1);
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("Unbreakable", 1);
        elytra.setTag(tag);
        elytra.addEnchantment(Enchantments.PROTECTION, 2);
        elytra.addEnchantment(Enchantments.BINDING_CURSE, 1);
        elytra.addEnchantment(Enchantments.VANISHING_CURSE, 1);
        elytra.setDisplayName(new StringTextComponent(TextFormatting.GRAY + "Blessed Wings"));
        return elytra.copy();
    }

    private static boolean isStone(BlockState blockState) {
        return blockState.getMaterial().equals(Material.ROCK);
    }

    public static void onEntityTarget(LivingSetAttackTargetEvent event) {
        LivingEntity e = event.getEntityLiving();
        LivingEntity t = event.getTarget();

        if (t instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) t;
            Profile profile = ProfileProvider.getProfile(player);

            //todo blessings
        }
    }

    public static void onPlayerAttackEntity(Profile profile, LivingHurtEvent event, PlayerEntity player) {
        LivingEntity target = event.getEntityLiving();
        if (BlessingManager.hasBlessing(profile, BlessingType.RAIKA_SMITE, player)) {
            spawnLightningBolt(target.getPositionVec(), target.world);
        }
    }

    public static void onPunchBlock(PlayerInteractEvent.LeftClickBlock event) {
        PlayerEntity player = event.getPlayer();
        if (!player.isSneaking()) return;
        if (CooldownManager.hasCooldown("earth_crumple", player)) return;
        Profile profile = ProfileProvider.getProfile(player);
        if (BlessingManager.hasBlessing(profile, BlessingType.ASANA_EARTH_CRUMPLE, player)) {
            BlockPos pos = event.getPos();
            BlockState state = player.world.getBlockState(pos);
            if (state.getMaterial().equals(Material.ROCK) || state.getBlock().equals(Blocks.GRASS_BLOCK) || state.getBlock().equals(Blocks.GRASS_PATH) ||
                    state.getMaterial().equals(Material.SAND)) {
                player.world.destroyBlock(pos, true);
                CooldownManager.addCooldown(player, "earth_crumple", 1000);
            }
        }
    }

    public static void onJumpServer(PlayerEntity player) {
        if (player == null) return;
        Profile profile = ProfileProvider.getProfile(player);
        if (profile == null) return;
        if (BlessingManager.hasBlessing(profile, BlessingType.ELIANA_FLIGHT, player)) {
            if (player.isSneaking()) {
                player.addVelocity(player.getLookVec().x, 1, player.getLookVec().z);
                player.velocityChanged = true;
            }
        }
    }
}
