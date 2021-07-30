package me.Jonathon594.Mythria.Event;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.Event;

public class CombatEvent extends Event {
    private EntityDamageSource source;
    private Entity target;
    private PlayerEntity player;
    private Profile profile;
    protected float damage;
    private EnumAttackType type;
    private ItemStack weapon;
    private boolean fail = false;
    private ItemStack otherWeapon;
    private Hand hand;

    public CombatEvent(float damage, EntityDamageSource source, EnumAttackType type, Entity target, PlayerEntity player, Profile profile, ItemStack weapon, ItemStack otherWeapon, Hand hand) {
        this.source = source;
        this.target = target;
        this.player = player;
        this.profile = profile;
        this.damage = damage;
        this.type = type;
        this.weapon = weapon;
        this.otherWeapon = otherWeapon;
        this.hand = hand;
    }

    public EntityDamageSource getSource() {
        return source;
    }

    public Entity getTarget() {
        return target;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Profile getProfile() {
        return profile;
    }

    public float getDamage() {
        return damage;
    }

    public EnumAttackType getType() {
        return type;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public ItemStack getOtherWeapon() {
        return otherWeapon;
    }

    public Hand getHand() {
        return hand;
    }

    public static class Pre extends CombatEvent {
        private boolean forceCrit = false;
        private boolean isAirSmash = false;
        private double attackRange;

        public Pre(float damage, EntityDamageSource source, EnumAttackType type, Entity entity, PlayerEntity player, Profile profile, ItemStack weapon, ItemStack otherWeapon, Hand hand, double attackRange) {
            super(damage, source, type, entity, player, profile, weapon, otherWeapon, hand);
            this.attackRange = attackRange;
        }

        public void setDamage(float damage) {
            this.damage = damage;
        }

        public void setForceCrit(boolean force) {
            forceCrit = force;
        }

        public boolean isForceCrit() {
            return forceCrit;
        }

        public boolean isAirSmash() {
            return isAirSmash;
        }

        public void setAirSmash(boolean airSmash) {
            isAirSmash = airSmash;
        }

        public double getAttackRange() {
            return attackRange;
        }

        public void setAttackRange(double attackRange) {
            this.attackRange = attackRange;
        }

    }

    public static class Post extends CombatEvent {
        private int knockBack;
        private boolean shouldSweep = false;
        private int knockUp = 0;

        private float torpidityMultiplier;

        public Post(float damage, EntityDamageSource source, EnumAttackType type, Entity entity, PlayerEntity player, Profile profile, ItemStack weapon, ItemStack otherWeapon, int knockBack, Hand hand) {
            super(damage, source, type, entity, player, profile, weapon, otherWeapon, hand);
            this.knockBack = knockBack;
            torpidityMultiplier = 1.0f;
        }

        public int getKnockBack() {
            return knockBack;
        }

        public void setKnockBack(int knockBack) {
            this.knockBack = knockBack;
        }

        public boolean isShouldSweep() {
            return shouldSweep;
        }

        public void setShouldSweep(boolean shouldSweep) {
            this.shouldSweep = shouldSweep;
        }

        public float getTorpidityMultiplier() {
            return torpidityMultiplier;
        }

        public int getKnockUp() {
            return knockUp;
        }

        public void setKnockUp(int knockUp) {
            this.knockUp = knockUp;
        }
    }
}
