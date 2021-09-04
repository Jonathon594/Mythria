package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Enum.AbilityBookCategories;
import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketAbilityBookState;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class AbilityBookGui extends AbstractGui implements IRenderable, IGuiEventListener {
    protected static final ResourceLocation ABILITY_BOOK = new MythriaResourceLocation("textures/gui/ability_book.png");
    private static final ITextComponent SEARCH_HINT = (new TranslationTextComponent("gui.ability_book.search_hint")).mergeStyle(TextFormatting.ITALIC).mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent KNOWN_TOOLTIP = new TranslationTextComponent("gui.ability_book.toggleAbilities.usable");
    private static final ITextComponent ALL_TOOLTIP = new TranslationTextComponent("gui.ability_book.toggleAbilities.all");
    private final List<AbilityTabToggleWidget> abilityTabs = Lists.newArrayList();
    private final AbilityBookPage abilityBookPage = new AbilityBookPage(this);
    private final AbilityInventoryScreen parentScreen;
    protected ToggleWidget toggleAbilitiesBtn;
    protected Minecraft mc;
    private String lastSearch = "";
    private int xOffset;
    private int width;
    private int height;
    private AbilityTabToggleWidget currentTab;
    private TextFieldWidget searchBar;
    private boolean keyDown;
    private boolean showOnlyUsable = true;

    public AbilityBookGui(AbilityInventoryScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    public void abilitiesUpdated() {
        this.updateTabs();
        if (this.isVisible()) {
            this.updateCollections(false);
        }
    }

    public void func_238924_c_(MatrixStack p_238924_1_, int p_238924_2_, int p_238924_3_, int p_238924_4_, int p_238924_5_) {
        if (this.isVisible()) {
            this.abilityBookPage.func_238926_a_(p_238924_1_, p_238924_4_, p_238924_5_);
            if (this.toggleAbilitiesBtn.isHovered()) {
                ITextComponent itextcomponent = this.func_230478_f_();
                if (this.mc.currentScreen != null) {
                    this.mc.currentScreen.renderTooltip(p_238924_1_, itextcomponent, p_238924_4_, p_238924_5_);
                }
            }

            this.func_238925_d_(p_238924_1_, p_238924_2_, p_238924_3_, p_238924_4_, p_238924_5_);
        }
    }

    public AbilityInventoryScreen getParentScreen() {
        return parentScreen;
    }

    public void init(int width, int height, Minecraft minecraft, boolean widthTooNarrow) {
        this.mc = minecraft;
        this.width = width;
        this.height = height;
        if (this.isVisible()) {
            this.initSearchBar(widthTooNarrow);
        }

        minecraft.keyboardListener.enableRepeatEvents(true);
    }

    public void initSearchBar(boolean widthTooNarrow) {
        this.xOffset = widthTooNarrow ? 0 : 86;
        int i = (this.width - 147) / 2 - this.xOffset;
        int j = (this.height - 166) / 2;
        String s = this.searchBar != null ? this.searchBar.getText() : "";
        this.searchBar = new TextFieldWidget(this.mc.fontRenderer, i + 25, j + 14, 80, 9 + 5, new TranslationTextComponent("itemGroup.search"));
        this.searchBar.setMaxStringLength(50);
        this.searchBar.setEnableBackgroundDrawing(false);
        this.searchBar.setVisible(true);
        this.searchBar.setTextColor(16777215);
        this.searchBar.setText(s);
        this.abilityBookPage.init(this.mc, i, j);
        this.toggleAbilitiesBtn = new ToggleWidget(i + 110, j + 12, 26, 16,
                showOnlyUsable);
        this.setAbilitiesButtonTexture();
        this.abilityTabs.clear();

        for (AbilityBookCategories category : AbilityBookCategories.values()) {
            this.abilityTabs.add(new AbilityTabToggleWidget(category));
        }

        if (this.currentTab != null) {
            this.currentTab = this.abilityTabs.stream().filter((abilityTab) ->
                    abilityTab.getCategory().equals(this.currentTab.getCategory())).findFirst().orElse(null);
        }

        if (this.currentTab == null) {
            this.currentTab = this.abilityTabs.get(0);
        }

        this.currentTab.setStateTriggered(true);
        this.updateCollections(false);
        this.updateTabs();
    }

    public boolean isVisible() {
        return MythriaPlayerProvider.getMythriaPlayer(mc.player).isAbilityBookOpen();
    }

    protected void setVisible(boolean visible) {
        if (!visible) {
            this.abilityBookPage.setInvisible();
        }

        MythriaPlayerProvider.getMythriaPlayer(mc.player).setAbilityBookOpen(visible);

        MythriaPacketHandler.sendToServer(new CPacketAbilityBookState(visible));
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.abilityBookPage.mouseClicked(mouseX, mouseY, button,
                    (this.width - 147) / 2 - this.xOffset, (this.height - 166) / 2, 147, 166)) {
                return true;
            } else if (this.searchBar.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (this.toggleAbilitiesBtn.mouseClicked(mouseX, mouseY, button)) {
                toggleShowUsable();
                this.toggleAbilitiesBtn.setStateTriggered(showOnlyUsable);
                //this.sendUpdateSettings();
                this.updateCollections(false);
                return true;
            } else {
                for (AbilityTabToggleWidget abilityTabToggleWidget : this.abilityTabs) {
                    if (abilityTabToggleWidget.mouseClicked(mouseX, mouseY, button)) {
                        if (this.currentTab != abilityTabToggleWidget) {
                            this.currentTab.setStateTriggered(false);
                            this.currentTab = abilityTabToggleWidget;
                            this.currentTab.setStateTriggered(true);
                            this.updateCollections(true);
                        }

                        return true;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.keyDown = false;
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (keyCode == 256 && !this.isOffsetNextToMainGUI()) {
                this.setVisible(false);
                return true;
            } else if (this.searchBar.keyPressed(keyCode, scanCode, modifiers)) {
                this.updateSearch();
                return true;
            } else if (this.searchBar.isFocused() && this.searchBar.getVisible() && keyCode != 256) {
                return true;
            } else if (this.mc.gameSettings.keyBindChat.matchesKey(keyCode, scanCode) && !this.searchBar.isFocused()) {
                this.keyDown = true;
                this.searchBar.setFocused2(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.keyDown = false;
        return IGuiEventListener.super.keyReleased(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char codePoint, int modifiers) {
        if (this.keyDown) {
            return false;
        } else if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.searchBar.charTyped(codePoint, modifiers)) {
                this.updateSearch();
                return true;
            } else {
                return IGuiEventListener.super.charTyped(codePoint, modifiers);
            }
        } else {
            return false;
        }
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    public void removed() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible()) {
            matrixStack.push();
            matrixStack.translate(0.0F, 0.0F, 100.0F);
            this.mc.getTextureManager().bindTexture(ABILITY_BOOK);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (this.width - 147) / 2 - this.xOffset;
            int j = (this.height - 166) / 2;
            this.blit(matrixStack, i, j, 1, 1, 147, 166);
            if (!this.searchBar.isFocused() && this.searchBar.getText().isEmpty()) {
                drawString(matrixStack, this.mc.fontRenderer, SEARCH_HINT, i + 25, j + 14, -1);
            } else {
                this.searchBar.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            for (AbilityTabToggleWidget abilityTabWidget : this.abilityTabs) {
                abilityTabWidget.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            this.toggleAbilitiesBtn.render(matrixStack, mouseX, mouseY, partialTicks);
            this.abilityBookPage.render(matrixStack, i, j, mouseX, mouseY, partialTicks);
            matrixStack.pop();
        }
    }

    public void toggleVisibility() {
        this.setVisible(!this.isVisible());
    }

    public int updateScreenPosition(boolean widthTooNarrow, int width, int xSize) {
        int i;
        if (this.isVisible() && !widthTooNarrow) {
            i = 177 + (width - xSize - 200) / 2;
        } else {
            i = (width - xSize) / 2;
        }

        return i;
    }

    private ITextComponent func_230478_f_() {
        return this.toggleAbilitiesBtn.isStateTriggered() ? this.func_230479_g_() : ALL_TOOLTIP;
    }

    private void func_238925_d_(MatrixStack p_238925_1_, int p_238925_2_, int p_238925_3_, int p_238925_4_, int p_238925_5_) {
        ItemStack itemstack = null;

        if (itemstack != null && this.mc.currentScreen != null) {
            this.mc.currentScreen.func_243308_b(p_238925_1_, this.mc.currentScreen.getTooltipFromItem(itemstack), p_238925_4_, p_238925_5_);
        }
    }

    private boolean isOffsetNextToMainGUI() {
        return this.xOffset == 86;
    }

    private void toggleShowUsable() {
        this.showOnlyUsable = !this.showOnlyUsable;
    }

    private void updateCollections(boolean p_193003_1_) {
        Collection<Ability> list = currentTab.getCategory().getAbilities();
        List<Ability> list1 = Lists.newArrayList(list);

        String s = this.searchBar.getText();
        if (!s.isEmpty()) {
            ObjectSet<Ability> objects = new ObjectLinkedOpenHashSet<>(this.mc.getSearchTree(ClientManager.ABILITIES).search(s.toLowerCase(Locale.ROOT)));
            list1.removeIf((ability) -> !objects.contains(ability));
        }

        list1.removeIf((ability) -> ability.isHidden() || !ProfileProvider.getProfile(mc.player).getAbilities().contains(ability));

        if (showOnlyUsable) {
            list1.removeIf((ability) -> !ability.canBeBound());
        }

        this.abilityBookPage.updateLists(list1, p_193003_1_);
    }

    private void updateSearch() {
        String s = this.searchBar.getText().toLowerCase(Locale.ROOT);
        if (!s.equals(this.lastSearch)) {
            this.updateCollections(false);
            this.lastSearch = s;
        }
    }

    private void updateTabs() {
        int i = (this.width - 147) / 2 - this.xOffset - 30;
        int j = (this.height - 166) / 2 + 3;
        int k = 27;
        int l = 0;

        for (AbilityTabToggleWidget abilityTabToggleWidget : this.abilityTabs) {
            AbilityBookCategories category = abilityTabToggleWidget.getCategory();
            if (category != AbilityBookCategories.SEARCH) {
                if (abilityTabToggleWidget.checkVisibility()) {
                    abilityTabToggleWidget.setPosition(i, j + 27 * l++);
                }
            } else {
                abilityTabToggleWidget.visible = true;
                abilityTabToggleWidget.setPosition(i, j + 27 * l++);
            }
        }

    }

    protected ITextComponent func_230479_g_() {
        return KNOWN_TOOLTIP;
    }

    protected void setAbilitiesButtonTexture() {
        this.toggleAbilitiesBtn.initTextureValues(152, 41, 28, 18, ABILITY_BOOK);
    }
}
