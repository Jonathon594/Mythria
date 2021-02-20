package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Event.ChargeConsumableEvent;
import me.Jonathon594.Mythria.Interface.IHeatProvider;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatManager {

    private HashMap<Consumable, Double> deltaStats;

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

    //Called when the player jumps to consume stamina
    public static void onJump(final PlayerEntity player, final Profile p) {
        chargeConsumable(player, 5, Consumable.STAMINA);
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

    public static void onTick(final TickEvent.ServerTickEvent event) {
        final PlayerList players = ServerLifecycleHooks.getCurrentServer().getPlayerList();
        for (final ServerPlayerEntity p : players.getPlayers()) {
            int tickCounter = ServerLifecycleHooks.getCurrentServer().getTickCounter();
            if (tickCounter % 10 == 0) {
                updateStats(p);
            }
            if (tickCounter % 2400 == 0) {
                consumeHunger(p);
            }

            updateCarryWeight(p);
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

    //Update the profile's stats 2x per second.
    public static void updateStats(final ServerPlayerEntity p) {
        HashMap<Consumable, Double> deltaStats = new HashMap<>();
        for (Consumable c : Consumable.values()) {
            deltaStats.put(c, 0.0);
        }

        HashMap<Consumable.Nutrition, Double> deltaNutrition = new HashMap<>();
        for (Consumable.Nutrition nutrition : Consumable.Nutrition.values()) {
            deltaNutrition.put(nutrition, 0.0);
        }

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
        double thirst;
        if(profile.getGenetic().isWaterNeeded()) {
            thirst = 0;
        } else {
            thirst = Math.min(1 + (Math.pow(Math.max(temperature - idealTemperature, 0), 2) * 0.047), 4);
        }
        double staminaRegen = Math.max(1 - (Math.pow(Math.abs(temperature - idealTemperature), 3)) / 1000.0, 0);

        //Torpor Decay
        deltaStats.put(Consumable.TORPOR, deltaStats.get(Consumable.TORPOR) - 0.05);
        //Pain Decay
        deltaStats.put(Consumable.PAIN, deltaStats.get(Consumable.PAIN) - 0.05);

        //Move undigested nutrition into nutrition
        for (Consumable.Nutrition nutrition : Consumable.Nutrition.values()) {
            deltaNutrition.put(nutrition, deltaNutrition.get(nutrition) - 0.00027);
            double undigested = profile.getUndigestedNutrition(nutrition) * 0.0025;
            deltaNutrition.put(nutrition, deltaNutrition.get(nutrition) + undigested);
            profile.setUndigestedNutrition(nutrition, profile.getUndigestedNutrition(nutrition) - undigested);
        }

        //Handle Thirst and Fatigue Decay
        deltaStats.put(Consumable.THIRST,
                deltaStats.get(Consumable.THIRST) - (0.005 / 3.0) * thirst); //Thirst Decay
        double deltaFatigue = p.isSleeping() ? 0.005 : 0.001;
        deltaStats.put(Consumable.FATIGUE,
                deltaStats.get(Consumable.FATIGUE) - deltaFatigue); //FatigueDecay

        //Mana Regeneration
        deltaStats.put(Consumable.MANA, deltaStats.get(Consumable.MANA) + profile.getStat(StatType.MANA_REGEN));

        //Adjust Temperature based on environment temperature
        double eTemp = getActualTemperature(p);
        double dTemp = (eTemp - temperature) * 0.01;
        deltaStats.put(Consumable.TEMPERATURE, deltaStats.get(Consumable.TEMPERATURE) + dTemp);

        //Refil Thirst
        if (p.isInWater()) {
            deltaStats.put(Consumable.THIRST, deltaStats.get(Consumable.THIRST) + 0.5);
        }

        //Bleeding
        double bleedOut = (profile.getBleeding() / 2) * MythriaUtil.scaleChance(100, 1.0, 0.5,
                profile.getAttributeLevel(Attribute.VIGOR));
        if (profile.hasFlag(AttributeFlag.ELF_HEALING)) {
            boolean canHeal = profile.getConsumable(Consumable.FATIGUE) < 0.8 && p.isAlive() && p.getHealth() > 0;
            if (p.getHealth() < p.getMaxHealth()) {
                if (canHeal) {
                    p.setHealth(p.getHealth() + 0.5f);
                    deltaStats.put(Consumable.FATIGUE, deltaStats.get(Consumable.FATIGUE) + 0.005);
                }
            }
            Double blood = profile.getConsumable(Consumable.BLOOD);
            if (blood < 20 && canHeal) {
                deltaStats.put(Consumable.BLOOD, blood + 0.5 - bleedOut);
                deltaStats.put(Consumable.FATIGUE, deltaStats.get(Consumable.FATIGUE) + 0.005);
            }
            if (profile.getBleeding() > 0 && canHeal) {
                profile.setBleeding(profile.getBleeding() - 0.05);
                deltaStats.put(Consumable.FATIGUE, deltaStats.get(Consumable.FATIGUE) + 0.005);
            }
        } else {
            deltaStats.put(Consumable.BLOOD, deltaStats.get(Consumable.BLOOD) + 0.01 - bleedOut);
            profile.setBleeding(profile.getBleeding() - 0.005);
        }

        //Stamina loss from sprinting
        if (p.isSprinting() && !p.isActualySwimming() && !p.isElytraFlying())
            deltaStats.put(Consumable.STAMINA, deltaStats.get(Consumable.STAMINA) - 5);

        //Stamina loss from swimming
        if (p.isInWater() && !p.isOnGround()) {
            staminaRegen = 0;
            int amount = 2;
            if (p.isActualySwimming()) {
                amount = 4;
            }
            deltaStats.put(Consumable.STAMINA, deltaStats.get(Consumable.STAMINA) - amount);
        }

//        if (!SexManager.isSex(p))
//            deltaStats.put(Consumable.PLEASURE, deltaStats.get(Consumable.PLEASURE) - 0.25);

        //Stamina regen
        double staminaRegenRate = profile.getStat(StatType.MAX_STAMINA) *
                0.015 * staminaRegen;
        deltaStats.put(Consumable.STAMINA, deltaStats.get(Consumable.STAMINA));

        deltaStats.put(Consumable.STAMINA,
                deltaStats.get(Consumable.STAMINA) + staminaRegenRate);

        //Apply delta stats
        for (Map.Entry<Consumable, Double> e : deltaStats.entrySet()) {
            Double value = e.getValue();
            if (value == 0) continue;
            if (value < 0)
                chargeConsumable(p, -value, e.getKey());
            else
                profile.setConsumable(e.getKey(), profile.getConsumable(e.getKey()) + value);
        }

        //Apply delta nutrition
        for (Consumable.Nutrition nutrition : Consumable.Nutrition.values()) {
            profile.setNutrition(nutrition, profile.getNutrition(nutrition) + deltaNutrition.get(nutrition));
        }

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
    public static double getActualTemperature(ServerPlayerEntity p) {
        final double bTemp = p.getEntityWorld().getBiome(p.getPosition()).getTemperature(p.getPosition()) * 5 + 10;
        double eTemp = bTemp;

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

        eTemp += (p.getTotalArmorValue() / 8);
        return eTemp;
    }

    public static void UpdateMaxHealth(final Profile profile, final PlayerEntity p) {
        MythriaUtil.applyMythriaAttributeModifier(p, "Mythria.ProfileHealth",
                profile.getStat(StatType.MAX_HEALTH), Attributes.MAX_HEALTH);
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
            profile.setConsumable(Consumable.PAIN, profile.getConsumable(Consumable.PAIN) + 0.5);
        }

        if (addTorpor) {
            profile.setConsumable(Consumable.TORPOR, profile.getConsumable(Consumable.TORPOR) + 0.5);
        }
    }

    private static double getArmorWeight(PlayerEntity p) {
        double armorWeight = 0;
        Iterator<ItemStack> i = p.getArmorInventoryList().iterator();
        while (i.hasNext()) {
            ItemStack is = i.next();
            if (is.isEmpty()) continue;

            armorWeight += WeightManager.getWeight(is);
        }
        return armorWeight;
    }

    private static int getArmorMitigation(int strength) {
        return strength * 2;
    }

    public static void UpdateSpeed(final Profile profile, final PlayerEntity p) {
        if (p == null) return;
        if (p.world.isRemote)
            return;

        double value = profile.getStat(StatType.MAX_SPEED);
        double maxWeight = profile.getStat(StatType.MAX_WEIGHT);
        Double currentWeight = profile.getConsumable(Consumable.WEIGHT);
        net.minecraft.entity.ai.attributes.Attribute movementSpeed = Attributes.MOVEMENT_SPEED;
        double baseSpeed = p.getAttribute(movementSpeed).getBaseValue();
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

        MythriaUtil.applyMythriaAttributeModifier(p, "Mythria.ProfileSpeed", value, movementSpeed);
    }
}
