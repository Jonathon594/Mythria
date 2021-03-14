package me.Jonathon594.Mythria.Enum;

import me.Jonathon594.Mythria.Interface.IPerkRegistry;
import me.Jonathon594.Mythria.Perk.*;

public enum PerkType {
    SURVIVAL(new SurvivalPerks()),
    WOODWORKING(new WoodworkingPerks()),
    MINING(new MiningPerks()),
    DIGGING(new DiggingPerks()),
    LUMBERING(new LumberingPerks()),
    CARPENTRY(new CarpentryPerks()),
    POTTERY(new PotteryPerks()),
    METALLURGY(new MetallurgyPerks()),
    COOKING(new CookingPerks()),
    FLETCHING(new FletchingPerks()),
    LEATHER_WORKING(new LeatherWorkingPerks()),
    FARMING(new FarmingPerks());

    private final IPerkRegistry registry;

    PerkType(final IPerkRegistry registry) {
        this.registry = registry;
    }

    public IPerkRegistry getRegistry() {
        return registry;
    }
}
