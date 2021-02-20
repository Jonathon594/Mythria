package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.Enum.AnatomySlot;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public abstract class HealthCondition {
    private final HealthConditionType type;
    protected double cureProgress;
    protected int painTimer;
    protected ArrayList<CureCondition> appliedTreatments = new ArrayList<>();
    private AnatomySlot slot;

    public HealthCondition(HealthConditionType type) {
        this.type = type;
    }

    public void fromNBT(CompoundNBT compoundTag) {
        if (compoundTag == null) return;

        cureProgress = compoundTag.getDouble("CureProgress");
        painTimer = compoundTag.getInt("PainTimer");
        ListNBT treatments = compoundTag.getList("Treatments", 8);
        for (INBT nbt : treatments) {
            StringNBT tagString = (StringNBT) nbt;
            CureCondition treatment = CureCondition.valueOf(tagString.getString());
            if (treatment != null) appliedTreatments.add(treatment);
        }
        slot = AnatomySlot.valueOf(compoundTag.getString("Slot"));
    }

    public CompoundNBT toNBT() {
        CompoundNBT compound = new CompoundNBT();

        compound.putString("Type", type.getName());
        compound.putDouble("CureProgress", cureProgress);
        compound.putInt("PainTimer", painTimer);
        ListNBT treatments = new ListNBT();
        for (CureCondition treatment : appliedTreatments) {
            treatments.add(StringNBT.valueOf(treatment.name()));

        }
        compound.put("Treatments", treatments);
        compound.putString("Slot", slot.name());

        return compound;
    }

    public double getCureProgress() {
        return cureProgress;
    }

    public void onUpdate(ServerPlayerEntity player) {
        painTimer++;
        cureProgress += 1.0 / (getDuration() * 20 * 60 * 20);

        uniqueUpdate(player);
    }

    private double getDuration() {
        return isTreated() ? type.getCureDuration() : type.getNaturalDuration();
    }

    protected abstract void uniqueUpdate(ServerPlayerEntity player);

    public boolean isTreated() {
        for (CureCondition treatment : type.getRequiredTreatments()) {
            if (!appliedTreatments.contains(treatment)) return false;
        }
        return true;
    }

    public AnatomySlot getSlot() {
        return slot;
    }

    public HealthCondition setSlot(AnatomySlot slot) {
        this.slot = slot;
        return this;
    }

    public CureCondition getNextCureCondition() {
        HealthConditionType type = getConditionType();
        int count = appliedTreatments.size();
        if (count >= type.getRequiredTreatments().length) {
            return null;
        } else {
            return type.getRequiredTreatments()[count];
        }
    }

    public HealthConditionType getConditionType() {
        return type;
    }

    public void applyTreatment(CureCondition cureCondition) {
        appliedTreatments.add(cureCondition);
    }

    protected void sendConditionMessage(ServerPlayerEntity player, String message) {
        String prefix = ColorConst.CONT_COLOR + "[" + TextFormatting.GOLD + type.getDisplayName() + ColorConst.CONT_COLOR + "] ";
        player.sendMessage(new StringTextComponent(prefix + ColorConst.MAIN_COLOR + message), Util.DUMMY_UUID);
    }
}
