package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.CampfireBlock;
import me.Jonathon594.Mythria.Capability.Food.Food;
import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Interface.ILightable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class CampfireTileEntity extends TileEntity implements IClearable, ITickableTileEntity, ILightable {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private final int maxTicks = 24000;

    private float friction = 0;
    private int ticksLeft = maxTicks;

    public CampfireTileEntity() {
        super(MythriaTileEntities.CAMPFIRE.get());
    }

    public void tick() {
        boolean flag = this.getBlockState().get(CampfireBlock.LIT);
        boolean flag1 = this.world.isRemote;
        if (flag1) {
            if (flag) {
                this.addParticles();
            }

        } else {
            if (flag) {
                this.cookAndDrop();
                this.updateTicksAndBreak();
            } else {
                for (int i = 0; i < this.inventory.size(); ++i) {
                    if (this.cookingTimes[i] > 0) {
                        this.cookingTimes[i] = MathHelper.clamp(this.cookingTimes[i] - 2, 0, this.cookingTotalTimes[i]);
                    }
                }
                if (friction > 1) {
                    friction--;
                } else friction = 0;
            }

        }
    }

    private void updateTicksAndBreak() {
        ticksLeft--;
        if (ticksLeft <= 0) {
            for (int i = 0; i < this.inventory.size(); ++i) {
                BlockPos blockpos = this.getPos();
                ItemStack stack = inventory.get(i);
                if (stack.isEmpty()) continue;
                InventoryHelper.spawnItemStack(this.world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), stack);
                this.inventory.set(i, ItemStack.EMPTY);
                this.inventoryChanged();
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        } else if (ticksLeft < (maxTicks / 2)) {
            world.setBlockState(pos, getBlockState().with(CampfireBlock.CHARCOAL, true), 11);
        }
    }

    /**
     * Individually tracks the cooking of each item, then spawns the finished product in-world and clears the
     * corresponding held item.
     */
    private void cookAndDrop() {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.get(i);
            if (!itemstack.isEmpty()) {
                ++this.cookingTimes[i];
                Food food = FoodProvider.getFood(itemstack);
                food.setCooked(((double) this.cookingTimes[i] / (double) this.cookingTotalTimes[i]));
                if (this.cookingTimes[i] >= this.cookingTotalTimes[i]) {
                    IInventory iinventory = new Inventory(itemstack);
                    ItemStack itemstack1 = this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, iinventory, this.world).map((p_213979_1_) -> {
                        return p_213979_1_.getCraftingResult(iinventory);
                    }).orElse(itemstack);
                    BlockPos blockpos = this.getPos();
                    Food food1 = FoodProvider.getFood(itemstack1);
                    food1.setCooked(1D);
                    InventoryHelper.spawnItemStack(this.world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), itemstack1);
                    this.inventory.set(i, ItemStack.EMPTY);
                    this.inventoryChanged();
                }
            }
        }

    }

    private void addParticles() {
        World world = this.getWorld();
        if (world != null) {
            BlockPos blockpos = this.getPos();
            Random random = world.rand;
            if (random.nextFloat() < 0.11F) {
                for (int i = 0; i < random.nextInt(2) + 2; ++i) {
                    CampfireBlock.spawnSmokeParticles(world, blockpos, this.getBlockState().get(CampfireBlock.SIGNAL_FIRE), false);
                }
            }

            int l = this.getBlockState().get(CampfireBlock.FACING).getHorizontalIndex();

            for (int j = 0; j < this.inventory.size(); ++j) {
                if (!this.inventory.get(j).isEmpty() && random.nextFloat() < 0.2F) {
                    Direction direction = Direction.byHorizontalIndex(Math.floorMod(j + l, 4));
                    float f = 0.3125F;
                    double d0 = (double) blockpos.getX() + 0.5D - (double) ((float) direction.getXOffset() * 0.3125F) + (double) ((float) direction.rotateY().getXOffset() * 0.3125F);
                    double d1 = (double) blockpos.getY() + 0.5D;
                    double d2 = (double) blockpos.getZ() + 0.5D - (double) ((float) direction.getZOffset() * 0.3125F) + (double) ((float) direction.rotateY().getZOffset() * 0.3125F);

                    for (int k = 0; k < 4; ++k) {
                        world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
                    }
                }
            }

        }
    }

    /**
     * Returns a NonNullList<ItemStack> of items currently held in the campfire.
     */
    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.inventory.clear();
        ItemStackHelper.loadAllItems(nbt, this.inventory);
        if (nbt.contains("CookingTimes", 11)) {
            int[] aint = nbt.getIntArray("CookingTimes");
            System.arraycopy(aint, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, aint.length));
        }

        if (nbt.contains("CookingTotalTimes", 11)) {
            int[] aint1 = nbt.getIntArray("CookingTotalTimes");
            System.arraycopy(aint1, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, aint1.length));
        }
    }

    public CompoundNBT write(CompoundNBT compound) {
        this.writeItems(compound);
        compound.putIntArray("CookingTimes", this.cookingTimes);
        compound.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        return compound;
    }

    private CompoundNBT writeItems(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory, true);
        return compound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(world.getBlockState(pos), pkt.getNbtCompound());
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

    public Optional<CampfireCookingRecipe> findMatchingRecipe(ItemStack itemStackIn) {
        return this.inventory.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, new Inventory(itemStackIn), this.world);
    }

    public boolean addItem(ItemStack itemStackIn, int cookTime) {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.get(i);
            if (itemstack.isEmpty()) {
                Food food = FoodProvider.getFood(itemStackIn);
                this.cookingTotalTimes[i] = (int) (cookTime * (1.0 - food.getCooked()));
                this.cookingTimes[i] = 0;
                this.inventory.set(i, itemStackIn.split(1));
                this.inventoryChanged();
                return true;
            }
        }

        return false;
    }

    private void inventoryChanged() {
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void clear() {
        this.inventory.clear();
    }

    public void dropAllItems() {
        if (!this.getWorld().isRemote) {
            InventoryHelper.dropItems(this.getWorld(), this.getPos(), this.getInventory());
        }

        this.inventoryChanged();
    }

    @Override
    public void light() {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
    }

    @Override
    public boolean tryLight(double friction) {
        this.friction += Math.random() * 20;
        if (this.friction > 100) {
            light();
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeLit() {
        BlockState blockState = getBlockState();
        return blockState.get(CampfireBlock.FULL) == 3 && blockState.get(CampfireBlock.WATERLOGGED) == false;
    }

    @Override
    public boolean isLit() {
        return this.getBlockState().get(CampfireBlock.LIT);
    }
}
