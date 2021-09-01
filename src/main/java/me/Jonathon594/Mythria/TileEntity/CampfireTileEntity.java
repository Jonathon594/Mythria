package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.CampfireBlock;
import me.Jonathon594.Mythria.Capability.Food.Food;
import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Interface.ILightable;
import me.Jonathon594.Mythria.Items.LogItem;
import me.Jonathon594.Mythria.Util.MythriaUtil;
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
    private final NonNullList<ItemStack> cookingInventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private final NonNullList<ItemStack> logInventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private final int maxTicks = 24000;
    private final Random rand = new Random();
    private float friction = 0;
    private int ticksLeft = maxTicks;
    private boolean charcoal = false;

    public CampfireTileEntity() {
        super(MythriaTileEntities.CAMPFIRE.get());
    }

    public boolean addFoodItem(ItemStack itemStackIn, int cookTime) {
        for (int i = 0; i < this.cookingInventory.size(); ++i) {
            ItemStack itemstack = this.cookingInventory.get(i);
            if (itemstack.isEmpty()) {
                Food food = FoodProvider.getFood(itemStackIn);
                this.cookingTotalTimes[i] = (int) (cookTime * (1.0 - food.getCooked()));
                this.cookingTimes[i] = 0;
                this.cookingInventory.set(i, itemStackIn.split(1));
                this.inventoryChanged();
                return true;
            }
        }

        return false;
    }

    public boolean addLogItem(ItemStack itemStack) {
        for (int i = 0; i < this.logInventory.size(); ++i) {
            ItemStack itemstack = this.logInventory.get(i);
            if (itemstack.isEmpty()) {
                this.logInventory.set(i, itemStack.split(1));
                this.inventoryChanged();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canBeLit() {
        BlockState blockState = getBlockState();
        return getLogCount() == 4 && !blockState.get(CampfireBlock.WATERLOGGED);
    }

    @Override
    public boolean isLit() {
        return this.getBlockState().get(CampfireBlock.LIT);
    }

    @Override
    public void light() {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
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

    public void clear() {
        this.cookingInventory.clear();
    }

    public void dropAllItems() {
        if (!this.getWorld().isRemote) {
            InventoryHelper.dropItems(this.getWorld(), this.getPos(), this.getCookingInventory());
        }

        this.inventoryChanged();
    }

    public Optional<CampfireCookingRecipe> findMatchingRecipe(ItemStack itemStackIn) {
        return this.cookingInventory.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, new Inventory(itemStackIn), this.world);
    }

    /**
     * Returns a NonNullList<ItemStack> of items currently held in the campfire.
     */
    public NonNullList<ItemStack> getCookingInventory() {
        return this.cookingInventory;
    }

    public int getLogCount() {
        int count = 0;
        for (int i = 0; i < logInventory.size(); i++) {
            if (logInventory.get(i).getItem() instanceof LogItem) count++;
        }
        return count;
    }

    public NonNullList<ItemStack> getLogInventory() {
        return logInventory;
    }

    public boolean isSoulfire() {
        return world.getBlockState(pos).get(CampfireBlock.SOULFIRE);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(world.getBlockState(pos), pkt.getNbtCompound());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.cookingInventory.clear();
        this.logInventory.clear();
        ItemStackHelper.loadAllItems(nbt.getCompound("cookingInventory"), this.cookingInventory);
        ItemStackHelper.loadAllItems(nbt.getCompound("logInventory"), this.logInventory);
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

    public CampfireTileEntity setCharcoal(boolean charcoal) {
        this.charcoal = charcoal;
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(CampfireBlock.CHARCOAL, Boolean.TRUE), 11);
        return this;
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
                for (int i = 0; i < this.cookingInventory.size(); ++i) {
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

    private void addParticles() {
        World world = this.getWorld();
        if (world != null) {
            BlockPos blockpos = this.getPos();
            Random random = world.rand;
            if (random.nextFloat() < 0.11F) {
                for (int i = 0; i < random.nextInt(2) + 2; ++i) {
                    if (!isSoulfire())
                        MythriaUtil.spawnSmokeParticles(world, blockpos, isSignalFire(), false);
                }
            }

            int l = this.getBlockState().get(CampfireBlock.FACING).getHorizontalIndex();

            for (int j = 0; j < this.cookingInventory.size(); ++j) {
                if (!this.cookingInventory.get(j).isEmpty() && random.nextFloat() < 0.2F) {
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
     * Individually tracks the cooking of each item, then spawns the finished product in-world and clears the
     * corresponding held item.
     */
    private void cookAndDrop() {
        for (int i = 0; i < this.cookingInventory.size(); ++i) {
            ItemStack itemstack = this.cookingInventory.get(i);
            if (!itemstack.isEmpty()) {
                ++this.cookingTimes[i];
                Food food = FoodProvider.getFood(itemstack);
                food.setCooked(((double) this.cookingTimes[i] / (double) this.cookingTotalTimes[i]));
                if (this.cookingTimes[i] >= this.cookingTotalTimes[i]) {
                    IInventory iinventory = new Inventory(itemstack);
                    ItemStack itemstack1 = this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, iinventory, this.world).map((p_213979_1_) -> p_213979_1_.getCraftingResult(iinventory)).orElse(itemstack);
                    BlockPos blockpos = this.getPos();
                    Food food1 = FoodProvider.getFood(itemstack1);
                    food1.setCooked(1D);
                    InventoryHelper.spawnItemStack(this.world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), itemstack1);
                    this.cookingInventory.set(i, ItemStack.EMPTY);
                    this.inventoryChanged();
                    MythriaUtil.addExperienceToAllAroundPoint(world, 10, pos, MythicSkills.COOKING, EXPConst.COOK_ITEM, 0);
                }
            }
        }

    }

    private void inventoryChanged() {
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    private Boolean isSignalFire() {
        return this.getBlockState().get(CampfireBlock.SIGNAL_FIRE);
    }

    private void updateTicksAndBreak() {
        if (ticksLeft <= 0) {
            for (int i = 0; i < this.cookingInventory.size(); ++i) {
                BlockPos blockpos = this.getPos();
                ItemStack stack = cookingInventory.get(i);
                if (stack.isEmpty()) continue;
                InventoryHelper.spawnItemStack(this.world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), stack);
                this.cookingInventory.set(i, ItemStack.EMPTY);
                this.inventoryChanged();
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        } else if (ticksLeft < (maxTicks / 2)) {
            setCharcoal(true);
        }
        ticksLeft--;
    }

    private CompoundNBT writeItems(CompoundNBT compound) {
        super.write(compound);
        CompoundNBT cookingItems = new CompoundNBT();
        CompoundNBT logItems = new CompoundNBT();
        ItemStackHelper.saveAllItems(cookingItems, this.cookingInventory, true);
        ItemStackHelper.saveAllItems(logItems, this.logInventory, true);
        compound.put("cookingInventory", cookingItems);
        compound.put("logInventory", logItems);
        return compound;
    }
}
