package me.Jonathon594.Mythria.Interface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface ITickableItem {
    void tick(PlayerEntity player, ItemStack is);
}
