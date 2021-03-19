package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.MythriaBarrelBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MythriaBarrelTileEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> barrelContents = NonNullList.withSize(9, ItemStack.EMPTY);
    private int numPlayersUsing;

    public MythriaBarrelTileEntity() {
        this(MythriaTileEntities.THATCH_BASKET.get());
    }

    private MythriaBarrelTileEntity(TileEntityType<?> barrelType) {
        super(barrelType);
    }

    public void barrelTick() {
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        this.numPlayersUsing = ChestTileEntity.calculatePlayersUsing(this.world, this, i, j, k);
        if (this.numPlayersUsing > 0) {
            this.scheduleTick();
        } else {
            BlockState blockstate = this.getBlockState();
            if (!(blockstate.getBlock() instanceof MythriaBarrelBlock)) {
                this.remove();
                return;
            }

            boolean flag = blockstate.get(MythriaBarrelBlock.PROPERTY_OPEN);
            if (flag) {
                this.playSound(blockstate, SoundEvents.BLOCK_BARREL_CLOSE);
                this.setOpenProperty(blockstate, false);
            }
        }

    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return 9;
    }

    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            BlockState blockstate = this.getBlockState();
            boolean flag = blockstate.get(BarrelBlock.PROPERTY_OPEN);
            if (!flag) {
                this.playSound(blockstate, SoundEvents.BLOCK_BARREL_OPEN);
                this.setOpenProperty(blockstate, true);
            }

            this.scheduleTick();
        }

    }

    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }

    }

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.barrelContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.barrelContents);
        }

    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.barrelContents);
        }

        return compound;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.thatch_basket");
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return new ChestContainer(ContainerType.GENERIC_9X1, id, player, this, 1);
    }

    private void playSound(BlockState state, SoundEvent sound) {
        Vector3i vector3i = state.get(MythriaBarrelBlock.PROPERTY_FACING).getDirectionVec();
        double d0 = (double) this.pos.getX() + 0.5D + (double) vector3i.getX() / 2.0D;
        double d1 = (double) this.pos.getY() + 0.5D + (double) vector3i.getY() / 2.0D;
        double d2 = (double) this.pos.getZ() + 0.5D + (double) vector3i.getZ() / 2.0D;
        this.world.playSound(null, d0, d1, d2, sound, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    }

    private void scheduleTick() {
        this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
    }

    private void setOpenProperty(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), state.with(MythriaBarrelBlock.PROPERTY_OPEN, open), 3);
    }

    protected NonNullList<ItemStack> getItems() {
        return this.barrelContents;
    }

    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.barrelContents = itemsIn;
    }
}

