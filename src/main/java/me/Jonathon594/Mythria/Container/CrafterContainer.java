package me.Jonathon594.Mythria.Container;

import com.google.common.collect.Lists;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Managers.MaterialManager;
import me.Jonathon594.Mythria.Recipe.CrafterRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class CrafterContainer extends Container {
    protected final CraftResultInventory CraftResultInventory = new CraftResultInventory();
    protected final World world;
    protected final Slot input;
    protected final Slot output;
    private final List<CrafterRecipe> recipes = Lists.newArrayList();
    protected ItemStack tool;
    private Runnable runnable = () -> {
    };
    private int selectedRecipe = 0;
    private ItemStack stack = ItemStack.EMPTY;
    protected final CraftingInventory inventory = new CraftingInventory(this, 1, 1) {
        @Override
        public void markDirty() {
            super.markDirty();
            CrafterContainer.this.onCraftMatrixChanged(this);
            CrafterContainer.this.runnable.run();
        }
    };
    private long lastSound = 0L;

    public CrafterContainer(ContainerType<?> type, int windowID, PlayerInventory playerInventory) {
        super(type, windowID);
        world = playerInventory.player.world;
        input = createInputSlot();
        output = createOutputSlot();

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        }

        for (int row = 0; row < 9; ++row) {
            this.addSlot(new Slot(playerInventory, row, 8 + row * 18, 142));
        }
    }

    public boolean canSelectRecipe(PlayerEntity playerIn, CrafterRecipe recipe) {
        return hasRequiredQuantity(recipe) && hasRequiredPerk(playerIn, recipe);
    }

    public boolean enchantItem(PlayerEntity playerIn, int id) {
        if (id >= 0 && id < this.recipes.size()) {
            CrafterRecipe recipe = this.recipes.get(id);
            if (!canSelectRecipe(playerIn, recipe)) {
                return false;
            } else {
                this.selectedRecipe = id;
            }
            this.updateResult();
            return true;
        }
        return false;
    }

    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return false;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        CraftResultInventory.removeStackFromSlot(1);
        clearContainer(playerIn, world, inventory);
    }

    public void onCraftMatrixChanged(IInventory inventoryIn) {
        ItemStack itemstack = this.input.getStack();
        if (itemstack.getItem() != this.stack.getItem()) {
            this.stack = itemstack.copy();
            this.updateRecipes(inventoryIn, itemstack);
        }

        if (!hasRequiredQuantity(getRecipe())) {
            selectedRecipe = -1;
            this.output.putStack(ItemStack.EMPTY);
        }
    }

    public int getRecipeCount() {
        return this.recipes.size();
    }

    public List<? extends CrafterRecipe> getRecipes() {
        return this.recipes;
    }

    public int getSelectedIndex() {
        return this.selectedRecipe;
    }

    public ItemStack getTool() {
        return tool;
    }

    public boolean hasRecipes() {
        return this.input.getHasStack() && !this.recipes.isEmpty();
    }

    public boolean hasRequiredQuantity(IRecipe recipe) {
        if (recipe == null) return false;
        if (!(recipe instanceof CrafterRecipe)) return false;
        CrafterRecipe crafterRecipe = (CrafterRecipe) recipe;
        return crafterRecipe.getCost() <= this.input.getStack().getCount();
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    private boolean hasRequiredPerk(PlayerEntity playerIn, CrafterRecipe recipe) {
        Map<Item, List<Perk>> attributesForCrafting = MaterialManager.PERKS_FOR_CRAFTING;
        Item item = recipe.getRecipeOutput().getItem();
        if (attributesForCrafting.containsKey(item)) {
            Profile profile = ProfileProvider.getProfile(playerIn);
            for (Perk perk : attributesForCrafting.get(item)) {
                if (profile.hasPerk(perk)) return true;
            }
            return false;
        }
        return true;
    }

    private boolean hasTool() {
        return tool != null && isValidTool(tool);
    }

    private void updateRecipes(IInventory craftingInventory, ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipe = -1;
        this.output.putStack(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            Collection<? extends IRecipe> recipes = this.world.getRecipeManager().getRecipes(getRecipeType(), craftingInventory, world);
            for (IRecipe recipe : recipes) {
                if (!(recipe instanceof CrafterRecipe)) continue;
                CrafterRecipe crafterRecipe = (CrafterRecipe) recipe;
                this.recipes.add(crafterRecipe);
            }
        }
    }

    protected abstract Slot createInputSlot();

    protected abstract Slot createOutputSlot();

    protected boolean canCraft() {
        if (!hasRecipes()) return false;
        if (!hasTool() && needsTool()) return false;
        IRecipe recipe = getRecipe();
        if (recipe == null) return false;

        if (!needsTool()) return true;

        ItemStack tool = getTool();
        int damageRemaining = tool.getMaxDamage() - tool.getDamage();
        int count = recipe.getRecipeOutput().getCount();
        return count <= damageRemaining;
    }

    protected abstract boolean isValidTool(ItemStack tool);

    protected abstract IRecipeType getRecipeType();

    protected abstract Collection<Item> getValidItems();

    protected void onOutputTakeStack(PlayerEntity player, ItemStack stack) {
        if (needsTool()) damageTool(player);

        CrafterRecipe recipe = getRecipe();
        ItemStack itemstack = CrafterContainer.this.input.decrStackSize(recipe.getCost());
        if (!itemstack.isEmpty()) {
            CrafterContainer.this.updateResult();
        }

        stack.getItem().onCreated(stack, player.world, player);

        if (world.getGameTime() != lastSound) {
            world.playSound(null, player.getPosition(), getCraftSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            lastSound = world.getGameTime();
        }

        if (!stack.isEmpty()) addExperience(player, stack, recipe);
    }

    protected boolean needsTool() {
        return true;
    }

    protected void damageTool(final PlayerEntity player) {
        ItemStack tool = getTool();
        IRecipe recipe = getRecipe();
        int count = recipe.getRecipeOutput().getCount();
        tool.damageItem(count, player, (playerEntity) -> playerEntity.sendBreakAnimation(new ItemUseContext(player, Hand.MAIN_HAND, null).getHand()));
    }

    protected CrafterRecipe getRecipe() {
        if (this.selectedRecipe > this.recipes.size() - 1 || this.selectedRecipe < 0) return null;
        return this.recipes.get(this.selectedRecipe);
    }

    protected void updateResult() {
        IRecipe recipe = getRecipe();
        if (!this.recipes.isEmpty() && recipe != null) {
            this.output.putStack(recipe.getCraftingResult(this.inventory));
        } else {
            this.output.putStack(ItemStack.EMPTY);
        }

        this.detectAndSendChanges();
    }

    protected abstract SoundEvent getCraftSound();

    protected void addExperience(PlayerEntity player, ItemStack stack, CrafterRecipe recipe) {
        Map<Item, List<Perk>> attributesForCrafting = MaterialManager.PERKS_FOR_CRAFTING;
        Item item = recipe.getRecipeOutput().getItem();
        if (attributesForCrafting.containsKey(item) && !player.world.isRemote) {
            Profile profile = ProfileProvider.getProfile(player);
            for (Perk perk : attributesForCrafting.get(item)) {
                if (profile.hasPerk(perk)) {
                    for (final Map.Entry<Skill, Integer> s : perk.getRequiredSkills().entrySet())
                        profile.addSkillExperience(s.getKey(), EXPConst.ITEM_CRAFT * stack.getCount(), (ServerPlayerEntity) player, s.getValue());
                }
            }
        }
    }
}
