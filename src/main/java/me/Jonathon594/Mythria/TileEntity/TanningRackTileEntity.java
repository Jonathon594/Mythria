package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TanningRackTileEntity extends TileEntity implements IClearable, ITickableTileEntity {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private int tanningTime = 0;
    private int tanningTotalTime = 0;

    public TanningRackTileEntity() {
        super(MythriaTileEntities.TANNING_RACK.get());
    }

    public boolean addItem(ItemStack itemStackIn, int tanningTime) {
        ItemStack itemstack = this.inventory.get(0);
        if (itemStackIn.getItem().equals(MythriaItems.ANIMAL_HIDE)) {
            if (itemstack.isEmpty()) {
                this.tanningTotalTime = tanningTime;
                this.tanningTime = 0;
                this.inventory.set(0, itemStackIn.split(1));
                playSound();
                this.inventoryChanged();
                return true;
            }
        }
        return false;
    }

    public void clear() {
        this.inventory.clear();
    }

    public void dropAllItems() {
        if (!this.getWorld().isRemote) {
            InventoryHelper.dropItems(this.getWorld(), this.getPos().add(0.5D, 0.5D, 0.5D), this.getInventory());
        }

        this.inventoryChanged();
    }

    /**
     * Returns a NonNullList<ItemStack> of items currently held in the campfire.
     */
    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(world.getBlockState(pos), pkt.getNbtCompound());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.inventory.clear();
        ItemStackHelper.loadAllItems(nbt, this.inventory);
        if (nbt.contains("TanningTime")) {
            this.tanningTime = nbt.getInt("TanningTime");
        }

        if (nbt.contains("TanningTotalTime")) {
            this.tanningTotalTime = nbt.getInt("TanningTotalTime");
        }
    }

    public CompoundNBT write(CompoundNBT compound) {
        this.writeItems(compound);
        compound.putInt("TanningTime", this.tanningTime);
        compound.putInt("TanningTotalTime", this.tanningTotalTime);
        return compound;
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 13, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag() {
        return this.writeItems(new CompoundNBT());
    }

    public boolean takeItem(PlayerEntity player) {
        ItemStack itemstack = this.inventory.get(0);
        if (itemstack.getItem().equals(Items.LEATHER)) {
            if (!player.inventory.addItemStackToInventory(this.inventory.get(0))) {
                BlockPos blockpos = this.getPos();
                InventoryHelper.spawnItemStack(this.world, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 1D, (double) blockpos.getZ() + 0.5D, itemstack);
            }
            this.inventory.set(0, ItemStack.EMPTY);
            playSound();
            this.inventoryChanged();
            return true;
        }
        return false;
    }

    public void tick() {
        boolean flag1 = this.world.isRemote;
        if (flag1) {
        } else {
            this.tan();
        }
    }

    private void inventoryChanged() {
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    private void playSound() {
        world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    /**
     * Individually tracks the cooking of each item, then spawns the finished product in-world and clears the
     * corresponding held item.
     */
    private void tan() {
        if (world.isNightTime()) return;
        if (!world.canBlockSeeSky(pos)) {
            dropAllItems();
            return;
        }
        ItemStack itemstack = this.inventory.get(0);
        if (!itemstack.isEmpty()) {
            ++this.tanningTime;
            if (this.tanningTime >= this.tanningTotalTime) {
                ItemStack itemstack1 = new ItemStack(Items.LEATHER, 1);
                this.inventory.set(0, itemstack1);
                this.inventoryChanged();
            }
        }
    }

    private CompoundNBT writeItems(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory, true);
        return compound;
    }
}
