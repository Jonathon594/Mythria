package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.Genetic.SpecialAbility;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Event.ChargeConsumableEvent;
import me.Jonathon594.Mythria.Interface.IHeatProvider;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.SPacketUpdateConsumables;
import me.Jonathon594.Mythria.Packet.SPacketUpdateNutrition;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import me.Jonathon594.Mythria.Worlds.MythriaWorlds;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.UUID;

public class StatManager {
    public static final UUID MAX_HEALTH_MODIFIER = UUID.randomUUID();
    public static final UUID SPEED_MODIFIER = UUID.randomUUID();
    public static final UUID SWIM_SPEED_MODIFIER = UUID.randomUUID();


    private HashMap<Consumable, Double> deltaStats;

    public static void UpdateMaxHealth(final Profile profile, final PlayerEntity p) {
        MythriaUtil.applyMythriaAttributeModifier(p, "Mythria.ProfileHealth", MAX_HEALTH_MODIFIER,
                profile.getStat(StatType.MAX_HEALTH), Attributes.MAX_HEALTH);
    }

    public static void UpdateSpeed(final Profile profile, final PlayerEntity p) {
        if (p == null) return;
        if (p.world.isRemote)
            return;

        double value = profile.getStat(StatType.MAX_SPEED);
        double maxWeight = profile.getStat(StatType.MAX_WEIGHT);
        Double currentWeight = profile.getConsumable(Consumable.WEIGHT);
        net.minecraft.entity.ai.attributes.Attribute attribute = Attributes.MOVEMENT_SPEED;
        double baseSpeed = p.getAttribute(attribute).getBaseValue();
        if (currentWeight >= maxWeight) {
            double encumberanceProp = Math.min((currentWeight - maxWeight) / (maxWeight * 4), 1);
            value += -(baseSpeed + profile.getStat(StatType.MAX_SPEED)) * encumberanceProp;
        }

        if (profile.getConsumable(Consumable.FATIGUE) >= 0.75)
            value = -0.05;
        if (profile.getConsumable(Consumable.FATIGUE) >= 0.85)
            value = -100;

        double armorWeight = getArmorWeight(p);
        double armorLoss = Math.pow(Math.max(armorWeight - 45 - getArmorMitigation(profile.getAttributeLevel(Attribute.VITALITY)), 0), 2) * 0.00002 - armorWeight > 45 ? 0.02 : 0;
        value -= armorLoss;

        MythriaUtil.applyMythriaAttributeModifier(p, "Mythria.Speed", SPEED_MODIFIER, value, attribute);

        if (profile.getGenetic().getSpecialAbilities().contains(SpecialAbility.FORCE_SWIMMING)) {
            MythriaUtil.applyMythriaAttributeModifier(p, "Mythria.SwimSpeed", SWIM_SPEED_MODIFIER, profile.getStat(StatType.SWIM_SPEED), ForgeMod.SWIM_SPEED.get());
        }
    }

    //Used to set a profiles base stats after creation
    public static void applyInitialStats(final Profile profile, ServerPlayerEntity serverPlayer) {
        for (final Consumable c : Consumable.values()) {
            double v = 0.0;
            switch (c) {
                case STAMINA:
                    v = profile.getStat(StatType.MAX_STAMINA);
                    break;
                case TEMPERATURE:
                    v = StatManager.getActualTemperature(serverPlayer);
                    break;
                case WEIGHT:
                    v = 0.0;
                    break;
                case THIRST:
                    v = 20.0;
                    break;
                case FATIGUE:
                    v = 0.0;
                    break;
                case TORPOR:
                    v = 0.0;
                    break;
                case MANA:
                    v = 0.0;
                case BLOOD:
                    v = 20.0;
            }
            profile.getConsumables().put(c, v);
        }
        for (Consumable.Nutrition nutrition : Consumable.Nutrition.values()) {
            profile.setNutrition(nutrition, 16);
        }
    }

    public static boolean chargeConsumable(final PlayerEntity player, double amount, final Consumable consumable) {
        if (amount <= 0) return false;
        final Profile p = ProfileProvider.getProfile(player);
        if (p.getConsumables().get(consumable) >= amount) {
            ChargeConsumableEvent event = new ChargeConsumableEvent(player, p, amount, consumable);
            MinecraftForge.EVENT_BUS.post(event);
            amount = event.getAmount();
            p.setConsumable(consumable, p.getConsumables().get(consumable) - amount);
            return true;
        }
        p.setConsumable(consumable, 0);
        return false;
    }

    public static double getActualTemperature(ServerPlayerEntity p) {
        double eTemp = p.getEntityWorld().getBiome(p.getPosition()).getTemperature(p.getPosition()) * 5 + 10;

        for (TileEntity te : p.world.loadedTileEntityList) {
            if (te instanceof IHeatProvider) {
                IHeatProvider provider = (IHeatProvider) te;

                double temp = provider.getTemperatureForHeating();
                double distance = p.getPositionVec().distanceTo(new Vector3d(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
                int maxDistance = 7;
                if (p.world.canBlockSeeSky(te.getPos().up())) maxDistance = 2;
                if (distance > maxDistance) continue;
                distance = Math.min(distance, maxDistance) / maxDistance;
                double tempFactor = 1 - distance;
                double scaling = Math.pow(tempFactor, 2) * 0.3;
                double effectiveTemp = temp * scaling;
                eTemp = Math.max(eTemp, effectiveTemp);
            }
        }

        eTemp += (p.getTotalArmorValue() / 8f);
        return eTemp;
    }

    public static double getTotalFatigueMitigation(final Profile p) {
        double fm = 0;
        for (final Perk pa : p.getPlayerSkills()) {
            if (pa == null)
                continue;
            fm += pa.getFatigueMitigation();
        }
        fm = MathHelper.clamp(fm, 0, 0.8);
        return fm;
    }

    //Called when the player jumps to consume stamina
    public static void onJump(final PlayerEntity player, final Profile p) {
        chargeConsumable(player, 5, Consumable.STAMINA); //todo cost const
    }

    public static void onTick(final TickEvent.ServerTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START)) return;
        final PlayerList players = ServerLifecycleHooks.getCurrentServer().getPlayerList();
        for (final ServerPlayerEntity player : players.getPlayers()) {
            int tickCounter = ServerLifecycleHooks.getCurrentServer().getTickCounter();
            updateStats(player);

            if (tickCounter % 2400 == 0) {
                consumeHunger(player);
            }
            if (tickCounter % 12000 == 0) {
                player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth())); //todo more elegance
            }

            updateCarryWeight(player);

            Profile profile = ProfileProvider.getProfile(player);
            if (tickCounter % 3 == 0) {
                MythriaPacketHandler.sendTo(new SPacketUpdateConsumables(profile), player);
                MythriaPacketHandler.sendTo(new SPacketUpdateNutrition(profile), player);
            }
        }
    }

    public static void updateCarryWeight(final PlayerEntity p) {
        double w = 0.0;
        for (int i = 0; i < p.inventory.getSizeInventory(); i++) {
            ItemStack is = p.inventory.getStackInSlot(i);
            double weight = WeightManager.getWeight(is);
            w += weight;
        }
        final Profile profile = ProfileProvider.getProfile(p);
        profile.setConsumable(Consumable.WEIGHT, w);
    }

    //Update the profile's stats every tick
    public static void updateStats(final ServerPlayerEntity p) {
        final Profile profile = ProfileProvider.getProfile(p);
        if (!profile.getCreated()) return;
        if (profile.getGenetic() == null) return;

        //Handle death by low thirst/nutrition
        if (profile.getAverageNutrition() <= 0
                || profile.getConsumable(Consumable.THIRST) <= 0) {
            p.setHealth(0.0f);
        }

        //Calculate thirst and stamina regeneration modifiers with respect to temperature
        double temperature = profile.getConsumable(Consumable.TEMPERATURE);
        double idealTemperature = profile.getGenetic().getIdealTemperature();
        double thirst = Math.min(1 + (Math.pow(Math.max(temperature - idealTemperature, 0), 2) * 0.0047), 4);
        double staminaRegen = Math.max(1 - (Math.pow(Math.abs(temperature - idealTemperature), 3)) / 1000.0, 0);

        //Torpor Decay
        profile.addConsumable(Consumable.TORPOR, -0.02);
        //Pain Decay
        profile.addConsumable(Consumable.PAIN, -0.005);

        //Move undigested nutrition into nutrition
        for (Consumable.Nutrition nutrition : Consumable.Nutrition.values()) {
            profile.addNutrition(nutrition, -0.000027);
            double undigested = profile.getUndigestedNutrition(nutrition) * 0.00025;
            profile.addNutrition(nutrition, undigested);
            profile.setUndigestedNutrition(nutrition, profile.getUndigestedNutrition(nutrition) - undigested);
        }

        //Handle Thirst and Fatigue Decay
        profile.addConsumable(Consumable.THIRST, -(0.0005 / 3.0) * thirst); //Thirst Decay
        double deltaFatigue = p.isSleeping() ? -0.0005 : -0.0001;
        profile.addConsumable(Consumable.FATIGUE, deltaFatigue); //FatigueDecay

        //Mana Regeneration
        profile.addConsumable(Consumable.MANA, profile.getStat(StatType.MANA_REGEN));

        //Adjust Temperature based on environment temperature
        double eTemp = getActualTemperature(p);
        double dTemp = (eTemp - temperature) * 0.001;
        profile.addConsumable(Consumable.TEMPERATURE, dTemp);

        //Refill Thirst
        if (p.isInWater()) {
            profile.addConsumable(Consumable.THIRST, 0.05);
        }

        //Bleeding
        double bleedOut = (profile.getBleeding() / 20.0) * MythriaUtil.scaleChance(100, 1.0, 0.5,
                profile.getAttributeLevel(Attribute.VIGOR));
        if (profile.hasFlag(AttributeFlag.ELF_HEALING)) {
            boolean canHeal = profile.getConsumable(Consumable.FATIGUE) < 0.8 && p.isAlive() && p.getHealth() > 0;
            if (p.getHealth() < p.getMaxHealth()) {
                if (canHeal) {
                    p.setHealth(p.getHealth() + 0.05f);
                    profile.addConsumable(Consumable.FATIGUE, 0.0005);
                }
            }
            Double blood = profile.getConsumable(Consumable.BLOOD);
            if (blood < 20 && canHeal) {
                profile.addConsumable(Consumable.BLOOD, blood + 0.05 - bleedOut);
                profile.addConsumable(Consumable.FATIGUE, 0.0005);
            }
            if (profile.getBleeding() > 0 && canHeal) {
                profile.setBleeding(profile.getBleeding() - 0.005);
                profile.addConsumable(Consumable.FATIGUE, 0.0005);
            }
        } else {
            profile.addConsumable(Consumable.BLOOD, 0.001 - bleedOut);
            profile.setBleeding(profile.getBleeding() - 0.0005);
        }

        //Stamina loss from sprinting
        if (p.isSprinting() && !p.isActualySwimming() && !p.isElytraFlying()) {
            profile.addConsumable(Consumable.STAMINA, -0.5);
            if (profile.getConsumable(Consumable.STAMINA) < 5) {
                profile.addConsumable(Consumable.TORPOR, 0.05);
            }
        }

        //Stamina loss from swimming
        if (p.isInWater() && !p.isOnGround()) {
            staminaRegen = 0;
            double amount = -0.2;
            if (p.isSwimming()) {
                amount = -0.4;
            }
            if (profile.getGenetic().getSpecialAbilities().contains(SpecialAbility.FORCE_SWIMMING)) amount = 0;
            profile.addConsumable(Consumable.STAMINA, amount);
        }

//        if (!SexManager.isSex(p))
//            profile.addConsumable(Consumable.PLEASURE, deltaStats.get(Consumable.PLEASURE) - 0.25);

        //Stamina regen
        double staminaRegenRate = profile.getStat(StatType.MAX_STAMINA) *
                0.0015 * staminaRegen;

        profile.addConsumable(Consumable.STAMINA, staminaRegenRate);

        //Update Others
        UpdateMaxHealth(profile, p);
        handleStatEffects(p, profile);
    }

    private static void consumeHunger(ServerPlayerEntity p) {
        if (!p.isAlive()) return; //If the player is dead cancel;
        if (p.world.getDimensionKey().equals(MythriaWorlds.SPAWN_KEY)) return;
        float saturationLevel = p.getFoodStats().getSaturationLevel();
        if (saturationLevel > 0) {
            ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, p.getFoodStats(), Math.max(saturationLevel - 1, 0), "field_75125_b");
            return;
        }
        Profile profile = ProfileProvider.getProfile(p);
        if (!profile.getCreated()) return;
        Genetic primaryGenetic = profile.getGenetic();
        if (primaryGenetic == null) {
            return;
        }

        FoodStats foodStats = p.getFoodStats(); //Get the food stats.
        int foodLevel = foodStats.getFoodLevel(); //Then the Food Level.
        if (foodLevel > 0) { //If it is larger than 0;
            foodStats.setFoodLevel(foodLevel - 1); //Subtract 1/2 food bar per minute.
        }
    }

    private static int getArmorMitigation(int strength) {
        return strength * 2;
    }

    private static double getArmorWeight(PlayerEntity p) {
        double armorWeight = 0;
        for (ItemStack is : p.getArmorInventoryList()) {
            if (is.isEmpty()) continue;

            armorWeight += WeightManager.getWeight(is);
        }
        return armorWeight;
    }

    private static void handleStatEffects(ServerPlayerEntity p, Profile profile) {
        if (!profile.getCreated()) return;

        int slow = 0;
        int nausea = 0;
        boolean blind = false;
        boolean addPain = false;
        boolean addTorpor = false;

        double thirst = profile.getConsumable(Consumable.THIRST);
        if (thirst < 1) {
            slow = 3;
            nausea = 4;
            blind = true;
            addPain = true;
            addTorpor = true;
        } else if (thirst < 5) {
            slow = 2;
            nausea = 1;
        } else if (thirst < 12.5) {
            slow = 1;
        }

        double nutrition = profile.getAverageNutrition();
        if (nutrition < 1) {
            slow = 3;
            nausea = 4;
            blind = true;
            addPain = true;
            addTorpor = true;
        } else if (nutrition < 5) {
            slow = 2;
            nausea = 1;
        } else if (nutrition < 12.5) {
            slow = 1;
        }

        double temperature = profile.getConsumable(Consumable.TEMPERATURE);
        Genetic primaryGenetic = profile.getGenetic();
        if (temperature > primaryGenetic.getIdealTemperature() + 2.5 + profile.getStat(StatType.HEAT_TOLLERANCE)) {
            slow = 1;
        }
        if (temperature > primaryGenetic.getIdealTemperature() + 5 + profile.getStat(StatType.HEAT_TOLLERANCE)) {
            slow = 2;
            nausea = 1;
        }

        if (temperature < primaryGenetic.getIdealTemperature() - 2.5 - profile.getStat(StatType.COLD_TOLLERANCE)) {
            slow = 1;
        }
        if (temperature < primaryGenetic.getIdealTemperature() - 5 - profile.getStat(StatType.COLD_TOLLERANCE)) {
            slow = 1;
            addPain = true;
        }

        if (slow > 0) {
            EffectInstance pe = new EffectInstance(Effects.SLOWNESS, 200, slow - 1, false, false);
            p.addPotionEffect(pe);
        }

        if (nausea > 0) {
            EffectInstance pe = new EffectInstance(Effects.NAUSEA, 200, nausea - 1, false, false);
            p.addPotionEffect(pe);
        }

        if (blind) {
            EffectInstance pe = new EffectInstance(Effects.BLINDNESS, 200, 0, false, false);
            p.addPotionEffect(pe);
        }

        if (addPain) {
            profile.setConsumable(Consumable.PAIN, profile.getConsumable(Consumable.PAIN) + 0.05);
        }

        if (addTorpor) {
            profile.setConsumable(Consumable.TORPOR, profile.getConsumable(Consumable.TORPOR) + 0.05);
        }
    }
}
