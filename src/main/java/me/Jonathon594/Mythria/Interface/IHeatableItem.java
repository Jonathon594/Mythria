package me.Jonathon594.Mythria.Interface;

import net.minecraft.util.text.ITextComponent;

import java.util.Collection;

public interface IHeatableItem {
    Collection<? extends ITextComponent> getHeatToolTips(double temperature);

    default boolean shouldBurnPlayers(double temperature) {
        return temperature > 45;
    }
}
