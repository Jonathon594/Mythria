package me.Jonathon594.Mythria.Enum;

public enum MythicSkills {
    AGILITY(false), AXES(false), SWORDS(false), MARTIAL_ARTS(false),
    ARCHERY(false), CRAFTING(true), MASONRY(true),
    METALLURGY(true), ENGINEERING(true), ALCHEMY(true), MINING(true),
    MEDICAL(true), FISHING(true), FARMING(true),
    COOKING(true), FIREMAKING(true), BLOCK(false), DAGGERS(false),
    HEAVY_WEAPONS(false), HEAVY_ARMOR(false),
    GLASSBLOWING(true), PICKPOCKET(false), ENCHANTING(true),
    WORSHIP(true), ATHEISM(true), WOODCUTTING(true),
    POTTERY(true), CARPENTRY(true), DIGGING(true);

    private final boolean isMental;

    MythicSkills(boolean isMental) {
        this.isMental = isMental;
    }

    public boolean isMental() {
        return isMental;
    }
}