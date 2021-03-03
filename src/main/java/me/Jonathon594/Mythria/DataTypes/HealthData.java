package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Capability.Food.IFood;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AnatomySlot;
import me.Jonathon594.Mythria.Enum.ObtainConditionType;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Managers.HealthManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HealthData {
    private final ArrayList<HealthCondition> conditions = new ArrayList<>();

    public ArrayList<HealthCondition> getConditions() {
        return conditions;
    }

    public void fromNBT(CompoundNBT compoundTag) {
        if (compoundTag == null) return;

        Set<String> conditionKeys = compoundTag.keySet();
        for (String s : conditionKeys) {
            Class clazz;
            try {
                clazz = Class.forName(s);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            CompoundNBT data = compoundTag.getCompound(s);
            HealthCondition condition = null;
            try {
                condition = (HealthCondition) clazz.getConstructor(HealthConditionType.class)
                        .newInstance(HealthManager.getHealthCondition(data.getString("Type")));
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            condition.fromNBT(data);
            conditions.add(condition);
        }
    }

    public CompoundNBT toNBT() {
        CompoundNBT compound = new CompoundNBT();

        for (HealthCondition condition : conditions) {
            compound.put(condition.getClass().getName(), condition.toNBT());
        }

        return compound;
    }

    public void onUpdate(ServerPlayerEntity player) {
        ArrayList<HealthCondition> toRemove = new ArrayList<>();
        for (HealthCondition condition : conditions) {
            condition.onUpdate(player);

            if (condition.getCureProgress() >= 1.0) {
                toRemove.add(condition);
            }
        }

        if (toRemove.size() > 0) {
            removeConditions(player, toRemove);
        }

        if (System.currentTimeMillis() % 1000 < 50) {
            attemptApplyConditions(player, 0, ObtainConditionType.BIOME, player.world.getBiome(player.getPosition()));
        }
    }

    public void removeConditions(ServerPlayerEntity player, ArrayList<HealthCondition> toRemove) {
        conditions.removeAll(toRemove);
    }

    private void attemptApplyConditions(ServerPlayerEntity player, float threshhold, ObtainConditionType obtainType, Object object) {
        for (AnatomySlot slot : AnatomySlot.values()) {
            for (HealthConditionType type : HealthManager.getHealthConditions()) {
                if (!type.getValidSlots().contains(slot)) continue;

                for (ObtainCondition obtainCondition : type.getObtainConditions()) {
                    if (!obtainCondition.getType().equals(obtainType)) continue;
                    if (obtainCondition.getThreshhold() > threshhold) continue;
                    if (Math.random() >= obtainCondition.getChance()) continue;
                    if (object != null && !obtainCondition.getObject().equals(object)) continue;

                    addHealthCondition(player, type, slot);
                }
            }
        }
    }

    public void addHealthCondition(ServerPlayerEntity player, HealthConditionType type, AnatomySlot slot) {
        try {
            for (HealthCondition hc : conditions) {
                if (slot.equals(hc.getSlot()) && !slot.canHaveMultipleConditions()) return;
            }
            conditions.add(type.createNewInstance().setSlot(slot));
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void onDamaged(LivingAttackEvent event, PlayerEntity player, Profile profile) {
        if (event.getSource().equals(DamageSource.FALL)) {
            attemptApplyConditions((ServerPlayerEntity) player, event.getAmount(), ObtainConditionType.FALL, null);
        }
    }

    public HashMap<StatType, Double> getStatModifiers() {
        HashMap<StatType, Double> map = new HashMap<>();
        for (HealthCondition condition : conditions) {
            HealthConditionType type = condition.getConditionType();
            for (Map.Entry<StatType, Double> e : type.getStatModifiers().entrySet()) {
                map.put(e.getKey(), map.containsKey(e.getKey()) ? map.get(e.getKey()) : 0 + e.getValue());
            }
        }
        return map;
    }

    public void cureAll() {
        conditions.clear();
    }

    public void onConsumeItem(LivingEntityUseItemEvent.Finish event, PlayerEntity player, Profile profile) {
        ItemStack is = event.getItem();
        Item item = is.getItem();

        if (item.equals(Items.BEEF) || item.equals(Items.MUTTON) || item.equals(Items.RABBIT)) {
            attemptApplyConditions((ServerPlayerEntity) player, 0, ObtainConditionType.RAW_RED_MEAT, null);
        }
        if (ItemTags.FISHES.contains(item)) {
            attemptApplyConditions((ServerPlayerEntity) player, 0, ObtainConditionType.RAW_FISH, null);
        }
        if (item.equals(Items.CHICKEN)) {
            attemptApplyConditions((ServerPlayerEntity) player, 0, ObtainConditionType.RAW_CHICKEN, null);
        }
        if (item.equals(Items.PORKCHOP)) {
            attemptApplyConditions((ServerPlayerEntity) player, 0, ObtainConditionType.RAW_PORK, null);
        }
        if (item.equals(Items.ROTTEN_FLESH)) {
            attemptApplyConditions((ServerPlayerEntity) player, 0, ObtainConditionType.ROTTEN_MEAT, null);
        }

        if (item.isFood()) {
            IFood food = FoodProvider.getFood(is);
            if (food.getAgeProportion(is) >= 1) {
                attemptApplyConditions((ServerPlayerEntity) player, 0, ObtainConditionType.ROTTEN_MEAT, null);
            }
        }
    }
}
