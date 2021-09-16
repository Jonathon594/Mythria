package me.Jonathon594.Mythria.Recipe;

import com.google.gson.JsonObject;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.Container.CrafterContainer;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Managers.MaterialManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public abstract class CrafterRecipe implements IRecipe<IInventory> {
    protected final Ingredient ingredient;
    protected final int cost;
    protected final ItemStack result;
    protected final ResourceLocation id;
    protected final String group;
    private final IRecipeType<?> type;
    private final IRecipeSerializer<?> serializer;

    public CrafterRecipe(IRecipeType<?> type, IRecipeSerializer<?> serializer, ResourceLocation id, String group, Ingredient ingredient, int cost, ItemStack result) {
        this.type = type;
        this.serializer = serializer;
        this.id = id;
        this.group = group;
        this.ingredient = ingredient;
        this.cost = cost;
        this.result = result;
    }

    public int getCost() {
        return cost;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height) {
        return true;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    /**
     * Recipes with equal group are combined into one button in the recipe book
     */
    public String getGroup() {
        return this.group;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public IRecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    public IRecipeType<?> getType() {
        return this.type;
    }

    public List<ITextComponent> getRecipeTooltips(PlayerEntity playerEntity, long gameTime, CrafterContainer container) {
        ArrayList<ITextComponent> tooltips = new ArrayList<>();
        tooltips.add(getNameTooltip());
        tooltips.addAll(getPerkTooltips(playerEntity));
        tooltips.addAll(getIngredientTooltips(gameTime, container.hasRequiredQuantity(this)));
        return tooltips;
    }

    private List<ITextComponent> getIngredientTooltips(long gameTime, boolean hasRequiredIngredients) {
        ArrayList<ITextComponent> tooltips = new ArrayList<>();
        tooltips.add(new StringTextComponent(ColorConst.MAIN_COLOR + "Ingredients:"));
        gameTime = Math.floorDiv(gameTime, 20);
        ItemStack[] matchingStacks = ingredient.getMatchingStacks();
        int index = matchingStacks.length > 0 ? MythriaUtil.wrapLong(gameTime, 0, matchingStacks.length - 1) : 0;
        TextFormatting color = hasRequiredIngredients ? ColorConst.SUCCESS_COLOR : ColorConst.CONT_COLOR;
        tooltips.add(new StringTextComponent(color.toString() + cost + "x " +
                matchingStacks[index].getDisplayName().getString()));
        return tooltips;
    }

    public static class Serializer<T extends CrafterRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
        final CrafterRecipe.Serializer.IRecipeFactory<T> factory;

        public Serializer(CrafterRecipe.Serializer.IRecipeFactory<T> factory) {
            this.factory = factory;
        }

        public T read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            Ingredient ingredient;
            if (JSONUtils.isJsonArray(json, "ingredient")) {
                ingredient = Ingredient.deserialize(JSONUtils.getJsonArray(json, "ingredient"));
            } else {
                ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            }

            int cost = 1;
            if (JSONUtils.hasField(json, "cost")) {
                cost = JSONUtils.getInt(json, "cost");
            }

            String s1 = JSONUtils.getString(json, "result");
            int i = JSONUtils.getInt(json, "count");
            ItemStack itemstack = new ItemStack(Registry.ITEM.getOrDefault(new ResourceLocation(s1)), i);
            return this.factory.create(recipeId, s, ingredient, cost, itemstack);
        }

        public T read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            Ingredient ingredient = Ingredient.read(buffer);
            int cost = buffer.readInt();
            ItemStack itemstack = buffer.readItemStack();
            return this.factory.create(recipeId, s, ingredient, cost, itemstack);
        }

        public void write(PacketBuffer buffer, T recipe) {
            buffer.writeString(recipe.group);
            recipe.ingredient.write(buffer);
            buffer.writeInt(recipe.cost);
            buffer.writeItemStack(recipe.result);
        }

        interface IRecipeFactory<T extends CrafterRecipe> {
            T create(ResourceLocation p_create_1_, String p_create_2_, Ingredient ingredient, int cost, ItemStack result);
        }
    }

    protected StringTextComponent getNameTooltip() {
        ItemStack recipeOutput = getRecipeOutput();
        return new StringTextComponent(ColorConst.HIGH_COLOR.toString() + recipeOutput.getCount() + "x " + recipeOutput.getDisplayName().getString());
    }

    protected ArrayList<ITextComponent> getPerkTooltips(PlayerEntity playerEntity) {
        ArrayList<ITextComponent> tooltips = new ArrayList<>();
        List<Perk> perk = MaterialManager.PERKS_FOR_CRAFTING.get(getRecipeOutput().getItem());
        if (perk != null && perk.size() > 0) {
            tooltips.add(new StringTextComponent(ColorConst.MAIN_COLOR + "Granted by perk:"));
            for (Perk p : perk) {
                TextFormatting perkColor = ProfileProvider.getProfile(playerEntity).hasPerk(p) ?
                        ColorConst.SUCCESS_COLOR : ColorConst.CONT_COLOR;
                tooltips.add(new StringTextComponent(perkColor + p.getDisplayName()));
            }
        }
        return tooltips;
    }
}
