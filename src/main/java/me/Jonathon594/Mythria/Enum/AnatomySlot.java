package me.Jonathon594.Mythria.Enum;

public enum AnatomySlot {
    LEFT_ANKLE_BONE(false),
    RIGHT_ANKLE_BONE(false),
    LEFT_LEG_BONE(false),
    RIGHT_LEG_BONE(false),
    BACTERIA(true),
    LEFT_ARM_BONE(false),
    RIGHT_ARM_BONE(false),
    RIGHT_WRIST_BONE(false),
    LEFT_WRIST_BONE(false),
    LEFT_LEG_TENDON(false),
    RIGHT_LEG_TENDON(false);

    private final boolean canHaveMultipleConditions;

    AnatomySlot(boolean canHaveMultipleConditions) {
        this.canHaveMultipleConditions = canHaveMultipleConditions;
    }

    public boolean canHaveMultipleConditions() {
        return canHaveMultipleConditions;
    }
}
