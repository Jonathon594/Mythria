package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Blocks.CampfireBlock;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class LogItem extends MythriaItem {
    public LogItem(String name, double weight) {
        super(name, weight, new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(8));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        Direction face = context.getFace();
        if (face != Direction.UP) {
            return ActionResultType.SUCCESS;
        } else {
            World world = context.getWorld();
            if (world.isRemote) return ActionResultType.PASS;
            BlockPos pos = context.getPos();
            BlockState blockstate = world.getBlockState(pos);

            PlayerEntity player = context.getPlayer();
            Profile profile = ProfileProvider.getProfile(player);
            if (!profile.hasFlag(AttributeFlag.FIREMAKING1)) {
                player.sendMessage(new StringTextComponent(MythriaConst.NO_PERK), Util.DUMMY_UUID);
                return ActionResultType.SUCCESS;
            }
            Hand hand = context.getHand();
            if (!blockstate.isReplaceable(new BlockItemUseContext(context))) {
                pos = pos.offset(face);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, face, itemstack)) {
                world.setBlockState(pos, MythriaBlocks.CAMPFIRE.getDefaultState().with(CampfireBlock.FACING, context.getPlacementHorizontalFacing()));
                SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
                world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.SUCCESS;
            }
        }
    }
}
