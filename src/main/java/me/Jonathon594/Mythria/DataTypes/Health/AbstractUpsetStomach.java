package me.Jonathon594.Mythria.DataTypes.Health;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.HealthConst;
import me.Jonathon594.Mythria.DataTypes.HealthCondition;
import me.Jonathon594.Mythria.DataTypes.HealthConditionType;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.FoodStats;

public abstract class AbstractUpsetStomach extends HealthCondition {
    public AbstractUpsetStomach(HealthConditionType type) {
        super(type);
    }

    private void triggerCramps(ServerPlayerEntity player) {
        Profile profile = ProfileProvider.getProfile(player);
        profile.setConsumable(Consumable.PAIN, profile.getConsumable(Consumable.PAIN) + 12);
        player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20 * 8, 4, false, false));
        sendConditionMessage(player, HealthConst.CRAMPS);
    }

    private void triggerVomit(ServerPlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20 * 18, 4, false, false));
        player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 4, 4, false, false));
        MythriaUtil.spawnItemStack(player.world, player.getPosX(), player.getPosY() + player.getEyeHeight(), player.getPosZ(), new ItemStack(Items.ROTTEN_FLESH, 1),
                player.getLookVec().scale(0.03), Integer.MAX_VALUE);
        FoodStats foodStats = player.getFoodStats();
        foodStats.setFoodLevel(Math.max(foodStats.getFoodLevel() - 4, 0));
        sendConditionMessage(player, HealthConst.THROW_UP);
    }

    @Override
    protected void uniqueUpdate(ServerPlayerEntity player) {
        if (Math.random() < getVomitChance()) {
            triggerVomit(player);
            return;
        }

        if (Math.random() < getCrampChance()) {
            triggerCramps(player);
        }
    }

    protected abstract double getVomitChance();

    protected abstract double getCrampChance();
}
