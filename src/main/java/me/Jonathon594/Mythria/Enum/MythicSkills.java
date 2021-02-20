package me.Jonathon594.Mythria.Enum;

public enum MythicSkills {
    AGILITY(false, Attribute.AGILITY), AXES(false, Attribute.STRENGTH), SWORDS(false, Attribute.DEXTERITY), UNARMED(false, Attribute.WILLPOWER),
    ARCHERY(false, Attribute.ENDURANCE), CRAFTING(true, Attribute.DEXTERITY), MASONRY(true, Attribute.DEXTERITY),
    METALLURGY(true, Attribute.DEXTERITY), ENGINEERING(true, Attribute.INTELLIGENCE), ALCHEMY(true, Attribute.INTELLIGENCE), MINING(true, Attribute.ENDURANCE),
    MEDICAL(true, Attribute.DEXTERITY), FISHING(true, Attribute.WILLPOWER), FARMING(true, Attribute.ENDURANCE),
    COOKING(true, Attribute.INTELLIGENCE), FIREMAKING(true, Attribute.INTELLIGENCE), BLOCK(false, Attribute.STRENGTH), DAGGERS(false, Attribute.DEXTERITY),
    HAMMERS(false, Attribute.STRENGTH), HEAVY_ARMOR(false, Attribute.ENDURANCE),
    GLASSBLOWING(true, Attribute.DEXTERITY), PICKPOCKET(false, Attribute.DEXTERITY), ENCHANTING(true, Attribute.INTELLIGENCE),
    WORSHIP(true, Attribute.FAITH), ATHEISM(true, Attribute.INTELLIGENCE), WOODCUTTING(true, Attribute.ENDURANCE),
    POTTERY(true, Attribute.DEXTERITY), CARPENTRY(true, Attribute.DEXTERITY);

    private final boolean isMental;

    private final Attribute majorAttribute;

    MythicSkills(boolean isMental, Attribute majorAttribute) {
        this.isMental = isMental;
        this.majorAttribute = majorAttribute;
    }

    public boolean isMental() {
        return isMental;
    }

    public Attribute getMajorAttribute() {
        return majorAttribute;
    }
}