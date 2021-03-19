package me.Jonathon594.Mythria.Enum;

public enum CookType {
    ROAST(AttributeFlag.COOKING_ROASTING, 0), BAKE(AttributeFlag.COOKING_BAKING, 10), BOIL(AttributeFlag.COOKING_BOILING, 20);

    private final AttributeFlag requiredFlag;
    private final int requiredLevel;

    CookType(AttributeFlag requiredFlag, int requiredLevel) {
        this.requiredFlag = requiredFlag;
        this.requiredLevel = requiredLevel;
    }

    public AttributeFlag getRequiredFlag() {
        return requiredFlag;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }
}
