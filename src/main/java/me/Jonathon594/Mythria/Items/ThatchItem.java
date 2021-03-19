package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Blocks.PitFurnaceBlock;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Interface.IPitFurnaceFilling;
import me.Jonathon594.Mythria.TileEntity.PitFurnaceTileEntity;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ThatchItem extends MythriaItem implements IPitFurnaceFilling {
    public ThatchItem(String name) {
        super(name, new Properties().group(ItemGroup.MATERIALS).maxStackSize(16));
    }

    @Override
    public ResourceLocation getFillingTexture() {
        return new MythriaResourceLocation("blocks/thatch_block_side");
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
            Hand hand = context.getHand();
            if (!blockstate.isReplaceable(new BlockItemUseContext(context))) {
                pos = pos.offset(face);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            PitFurnaceBlock pitKiln = MythriaBlocks.PIT_FURNACE;
            if (player.canPlayerEdit(pos, face, itemstack) && pitKiln.isValidPosition(null, world, pos)) {
                Profile profile = ProfileProvider.getProfile(player);
                if (!profile.hasFlag(AttributeFlag.PRIMITIVE_FURNACES)) {
                    player.sendMessage(new StringTextComponent(MythriaConst.NO_PERK), Util.DUMMY_UUID);
                    return ActionResultType.PASS;
                }

                world.setBlockState(pos, Block.getValidBlockForPosition(pitKiln.getDefaultState(), world, pos));
                SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
                world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                PitFurnaceTileEntity tile = (PitFurnaceTileEntity) world.getTileEntity(pos);
                tile.addItem(itemstack);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }
}
