package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Container.CrafterContainer;
import me.Jonathon594.Mythria.Recipe.CrafterRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class CrafterScreen<T extends CrafterContainer> extends ContainerScreen<T> {
    private float sliderProgress;
    private boolean selected;
    private int selectedIndex;
    private boolean hasRecipes;

    private int recipeIndexOffset;
    private boolean hasItemsInInputSlot;

    public CrafterScreen(final T p_i51105_1_, final PlayerInventory p_i51105_2_, final ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredTooltip(matrixStack, p_render_1_, p_render_2_);
    }

    private boolean canScroll() {
        return this.hasItemsInInputSlot && this.container.getRecipeCount() > 12;
    }

    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(getBackground());
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        int k = (int) (41.0F * this.sliderProgress);
        this.blit(matrixStack, i + 119, j + 15 + k, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        int l = this.guiLeft + 52;
        int i1 = this.guiTop + 14;
        int j1 = this.recipeIndexOffset + 12;
        this.func_238853_b_(matrixStack, x, y, l, i1, j1);

        this.drawRecipesItems(l, i1, j1);
    }

    private void drawRecipesItems(int left, int top, int recipeIndexOffsetMax) {
        List<? extends IRecipe> list = this.container.getRecipes();

        for (int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeCount(); ++i) {
            int j = i - this.recipeIndexOffset;
            int k = left + j % 4 * 16;
            int l = j / 4;
            int i1 = top + l * 18 + 2;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), k, i1);
        }

    }

    protected abstract ResourceLocation getBackground();

    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        this.selected = false;
        if (this.hasRecipes) {
            int i = this.guiLeft + 52;
            int j = this.guiTop + 14;
            int k = this.selectedIndex + 12;

            for (int l = this.selectedIndex; l < k; ++l) {
                int i1 = l - this.selectedIndex;
                double d0 = p_mouseClicked_1_ - (double) (i + i1 % 4 * 16);
                double d1 = p_mouseClicked_3_ - (double) (j + i1 / 4 * 18);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.container.enchantItem(this.minecraft.player, l)) {
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(getCraftSound(), 1.0F));
                    this.minecraft.playerController.sendEnchantPacket((this.container).windowId, l);
                    return true;
                }
            }

            i = this.guiLeft + 119;
            j = this.guiTop + 9;
            if (p_mouseClicked_1_ >= (double) i && p_mouseClicked_1_ < (double) (i + 12) && p_mouseClicked_3_ >= (double) j && p_mouseClicked_3_ < (double) (j + 54)) {
                this.selected = true;
            }
        }

        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    private void func_238853_b_(MatrixStack matrixStack, int x, int y, int p_238853_4_, int p_238853_5_, int p_238853_6_) {
        ArrayList<ITextComponent> tooltips = new ArrayList<>();
        for (int i = this.recipeIndexOffset; i < p_238853_6_ && i < this.container.getRecipeCount(); ++i) {
            int j = i - this.recipeIndexOffset;
            int k = p_238853_4_ + j % 4 * 16;
            int l = j / 4;
            int i1 = p_238853_5_ + l * 18 + 2;
            int j1 = this.ySize;

            boolean hovered = x >= k && y >= i1 && x < k + 16 && y < i1 + 18;
            CrafterRecipe recipe = container.getRecipes().get(i);
            if (i == this.container.getSelectedIndex()) {
                j1 += 18;
            } else if (!container.canSelectRecipe(minecraft.player, recipe)) {
                j1 += 54;
            } else if (hovered) {
                j1 += 36;
            }
            if (hovered) {
                tooltips.addAll(recipe.getRecipeTooltips(minecraft.player, minecraft.world.getGameTime(), container));
            }
            this.blit(matrixStack, k, i1 - 1, 0, j1, 16, 18);
        }

        if (tooltips.size() > 0) func_243308_b(matrixStack, tooltips, x, y);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.canScroll()) {
            int i = this.getHiddenRows();
            this.sliderProgress = (float) ((double) this.sliderProgress - delta / (double) i);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int) ((double) (this.sliderProgress * (float) i) + 0.5D) * 4;
        }

        return true;
    }

    protected int getHiddenRows() {
        return (this.container.getRecipeCount() + 4 - 1) / 4 - 3;
    }

    protected abstract SoundEvent getCraftSound();

    protected void onInventoryUpdate() {
        this.hasItemsInInputSlot = !this.container.getInventory().get(0).isEmpty();
        if (!this.hasItemsInInputSlot) {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }
        this.hasRecipes = this.container.getRecipeCount() > 0;
    }
}
