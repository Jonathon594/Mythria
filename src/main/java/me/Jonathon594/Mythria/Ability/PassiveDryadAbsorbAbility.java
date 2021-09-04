package me.Jonathon594.Mythria.Ability;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class PassiveDryadAbsorbAbility extends PassiveAbility {
    private final ArrayList<Block> growables = new ArrayList<>();

    public PassiveDryadAbsorbAbility(String name) {
        super(name);
        for (Block b : ForgeRegistries.BLOCKS.getValues()) {
            if (b instanceof IGrowable) growables.add(b);
        }
    }

    @Override
    protected void onPassiveTick(AbilityInstance abilityInstance) {
        PlayerEntity playerEntity = abilityInstance.getOwner();
        World world = playerEntity.world;
        Profile profile = ProfileProvider.getProfile(playerEntity);
        if (world.isRemote) return;
        if (world.getGameTime() % 20 == 0) {
            BlockPos pos = playerEntity.getPosition().down();
            ArrayList<BlockPos> positions = new ArrayList<>();
            BlockUtils.getConnected(world, pos, positions, growables, Integer.MAX_VALUE, 10, 12, pos);
            int totalBlocks = positions.size();

            Consumable mana = Consumable.MANA;
            double playerMana = profile.getConsumable(mana);
            playerMana += totalBlocks * 0.03;
            double maxMana = profile.getStat(StatType.MAX_MANA);
            if (playerMana > maxMana) playerMana = maxMana;

            if (playerMana != profile.getConsumable(Consumable.MANA)) profile.setConsumable(mana, playerMana);
        }
    }
}
