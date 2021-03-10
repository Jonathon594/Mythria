package me.Jonathon594.Mythria.Genetic;

import net.minecraft.nbt.CompoundNBT;

public abstract class Gene {
    private GeneType type;

    protected Gene(GeneType type) {
        this.type = type;
    }

    public GeneType getType() {
        return type;
    }

    public abstract GeneSerializer<? extends Gene> getSerializer();

    public abstract CompoundNBT toNBT(boolean writeSerializer);

    public enum GeneType {
        HAIR(false, true),
        EYES(false, true),
        SKIN(false, true),
        STAMINA(false, true),
        SPEED(false, true),
        WEIGHT(false, true),
        HEALTH(false, true),
        INTELLIGENCE(false, true),
        MANA(false, true),
        MANA_REGEN(false, true),
        LIFESPAN(false, true),
        TEMPERATURE(false, true),
        GENDER_BIAS(false, true),

        IMMUNITY(true, false),
        ENTITY_RELATION(true, false),
        ABILITY(true, false),
        WINGS(false, false),
        VINES(false, false);

        private final boolean stackable;
        private final boolean essential;

        GeneType(boolean stackable, boolean essential) {
            this.stackable = stackable;
            this.essential = essential;
        }

        public boolean canStack() {
            return stackable;
        }

        public boolean isEssential() {
            return essential;
        }
    }
}
