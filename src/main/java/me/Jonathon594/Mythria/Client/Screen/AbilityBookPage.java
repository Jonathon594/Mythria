package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ToggleWidget;

import java.util.List;

public class AbilityBookPage {
    private final List<AbilityWidget> buttons = Lists.newArrayListWithCapacity(20);
    private final AbilityBookGui parentGui;
    private AbilityWidget hoveredButton;
    private Minecraft minecraft;
    //private final List<IRecipeUpdateListener> listeners = Lists.newArrayList();
    private List<Ability> abilityList;
    private ToggleWidget forwardButton;
    private ToggleWidget backButton;
    private int totalPages;
    private int currentPage;
    //private RecipeBook recipeBook;

    public AbilityBookPage(AbilityBookGui parentGui) {
        this.parentGui = parentGui;
        for (int i = 0; i < 20; ++i) {
            this.buttons.add(new AbilityWidget());
        }
    }

    public void func_238926_a_(MatrixStack p_238926_1_, int p_238926_2_, int p_238926_3_) {
        if (this.minecraft.currentScreen != null && this.hoveredButton != null) {
            this.minecraft.currentScreen.func_243308_b(p_238926_1_,
                    this.hoveredButton.getToolTipText(this.minecraft.currentScreen), p_238926_2_, p_238926_3_);
        }

    }

    public void init(Minecraft p_194194_1_, int p_194194_2_, int p_194194_3_) {
        this.minecraft = p_194194_1_;

        for (int i = 0; i < this.buttons.size(); ++i) {
            this.buttons.get(i).setPosition(p_194194_2_ + 11 + 25 * (i % 5), p_194194_3_ + 31 + 25 * (i / 5));
        }

        this.forwardButton = new ToggleWidget(p_194194_2_ + 93, p_194194_3_ + 137, 12, 17, false);
        this.forwardButton.initTextureValues(1, 208, 13, 18, AbilityBookGui.ABILITY_BOOK);
        this.backButton = new ToggleWidget(p_194194_2_ + 38, p_194194_3_ + 137, 12, 17, true);
        this.backButton.initTextureValues(1, 208, 13, 18, AbilityBookGui.ABILITY_BOOK);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button, int p_198955_6_, int p_198955_7_, int p_198955_8_, int p_198955_9_) {
        if (this.forwardButton.mouseClicked(mouseX, mouseY, button)) {
            ++this.currentPage;
            this.updateButtonsForPage();
            return true;
        } else if (this.backButton.mouseClicked(mouseX, mouseY, button)) {
            --this.currentPage;
            this.updateButtonsForPage();
            return true;
        } else {
            for (AbilityWidget recipewidget : this.buttons) {
                Ability ability = recipewidget.getAbility();
                if (ProfileProvider.getProfile(minecraft.player).getAbilities().contains(ability) && ability.canBeBound()) {
                    if (recipewidget.mouseClicked(mouseX, mouseY, button)) {
                        parentGui.getParentScreen().setCursorAbility(ability);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void render(MatrixStack p_238927_1_, int p_238927_2_, int p_238927_3_, int p_238927_4_, int p_238927_5_, float p_238927_6_) {
        if (this.totalPages > 1) {
            String s = this.currentPage + 1 + "/" + this.totalPages;
            int i = this.minecraft.fontRenderer.getStringWidth(s);
            this.minecraft.fontRenderer.drawString(p_238927_1_, s, (float) (p_238927_2_ - i / 2 + 73), (float) (p_238927_3_ + 141), -1);
        }

        this.hoveredButton = null;

        for (AbilityWidget abilityWidget : this.buttons) {
            abilityWidget.render(p_238927_1_, p_238927_4_, p_238927_5_, p_238927_6_);
            if (abilityWidget.visible && abilityWidget.isHovered()) {
                this.hoveredButton = abilityWidget;
            }
        }

        this.backButton.render(p_238927_1_, p_238927_4_, p_238927_5_, p_238927_6_);
        this.forwardButton.render(p_238927_1_, p_238927_4_, p_238927_5_, p_238927_6_);
    }

    public void setInvisible() {
        //this.overlay.setVisible(false);
    }

    public void updateLists(List<Ability> abilities, boolean flag) {
        this.abilityList = abilities;
        this.totalPages = (int) Math.ceil((double) abilities.size() / 20.0D);
        if (this.totalPages <= this.currentPage || flag) {
            this.currentPage = 0;
        }

        this.updateButtonsForPage();
    }

    private void updateArrowButtons() {
        this.forwardButton.visible = this.totalPages > 1 && this.currentPage < this.totalPages - 1;
        this.backButton.visible = this.totalPages > 1 && this.currentPage > 0;
    }

    private void updateButtonsForPage() {
        int i = 20 * this.currentPage;

        for (int j = 0; j < this.buttons.size(); ++j) {
            AbilityWidget abilityWidget = this.buttons.get(j);
            if (i + j < this.abilityList.size()) {
                Ability ability = this.abilityList.get(i + j);
                abilityWidget.func_203400_a(ability);
                abilityWidget.visible = true;
            } else {
                abilityWidget.visible = false;
            }
        }

        this.updateArrowButtons();
    }
}
