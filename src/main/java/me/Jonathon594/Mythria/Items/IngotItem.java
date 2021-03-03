package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Enum.EnumMetalShape;
import me.Jonathon594.Mythria.Enum.MythriaMaterial;
import me.Jonathon594.Mythria.Interface.IWorkable;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IngotItem extends MythriaItem implements IWorkable {
    private final MythriaMaterial type;
    private final EnumMetalShape metalShape;

    public IngotItem(String name, MythriaMaterial material, EnumMetalShape shape) {
        super(name, new Item.Properties().group(ItemGroup.MATERIALS));
        this.type = material;
        this.metalShape = shape;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        Hand hand = context.getHand();
        Direction facing = context.getFace();
        BlockPos pos = context.getPos();
        if (world.isRemote) return ActionResultType.PASS; // If client side, return
        if (hand.equals(Hand.OFF_HAND)) return ActionResultType.PASS; // If offhand, return
        if (!metalShape.equals(EnumMetalShape.INGOT))
            return ActionResultType.PASS; // If not ingot shape, return
        if (!facing.equals(Direction.UP))
            return ActionResultType.PASS; // If not clicking on the top of a block, return
        if (!world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
            return ActionResultType.PASS; // If the block above is not air
//        if (!world.getBlockState(pos).is) return ActionResultType.PASS; // If top is not solid, return //Todo
        //world.setBlockState(pos.up(), MythriaBlocks.INGOT_PILE.getDefaultState());
        //TileEntityIngotPile tep = (TileEntityIngotPile) world.getTileEntity(pos.up());
        //tep.getInventory().setStackInSlot(0, new ItemStack(this, 1));
        // if (!player.isCreative()) player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        return ActionResultType.SUCCESS;
    }

    @Override
    public MythriaMaterial getMetalType() {
        return type;
    }

    @Override
    public EnumMetalShape getMetalShape() {
        return metalShape;
    }
}
