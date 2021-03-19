package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Interface.ILightable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public abstract class AbstractFireStarterItem extends MythriaItem {
    public AbstractFireStarterItem(String name, Properties properties) {
        super(name, properties);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity == null) return ActionResultType.PASS;
        if (tileEntity instanceof ILightable) {
            if (world.isRemote) return ActionResultType.SUCCESS;
            ILightable lightable = (ILightable) tileEntity;
            PlayerEntity player = context.getPlayer();
            if (lightable.isLit()) return ActionResultType.PASS;
            if (lightable.canBeLit()) {
                double experience;
                if (doesInstantLight()) {
                    lightable.light();
                    experience = EXPConst.LIGHT_FIRE;
                } else {
                    if (Math.random() < getBreakChance()) {
                        Hand hand = context.getHand();
                        player.getHeldItem(hand).shrink(1);
                        player.sendMessage(new StringTextComponent(getBreakMessage()).mergeStyle(ColorConst.MAIN_COLOR), Util.DUMMY_UUID);
                    }
                    lightable.tryLight(getFriction());
                    experience = EXPConst.LIGHT_FIRE_TICK;
                }
                ProfileProvider.getProfile(player).addSkillExperience(MythicSkills.FIREMAKING, experience, (ServerPlayerEntity) player, getRequiredLevel());
            } else {
                player.sendMessage(new StringTextComponent(MythriaConst.FIREMAKING_NO_FUEL).mergeStyle(ColorConst.MAIN_COLOR), Util.DUMMY_UUID);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    protected abstract boolean doesInstantLight();

    protected double getBreakChance() {
        return 0.05;
    }

    protected abstract String getBreakMessage();

    protected double getFriction() {
        return 0.1;
    }

    protected abstract int getRequiredLevel();
}
