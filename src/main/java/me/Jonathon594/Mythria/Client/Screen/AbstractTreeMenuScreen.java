package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class AbstractTreeMenuScreen extends Screen {
    private static final ResourceLocation WINDOW = new ResourceLocation("textures/gui/advancements/window.png");
    private static final ResourceLocation TABS = new ResourceLocation("textures/gui/advancements/tabs.png");
    private static int tabPage, maxPages;
    protected final Map<TreeMenuOption, TreeMenuTabGui> tabs = Maps.newLinkedHashMap();
    private TreeMenuTabGui selectedTab;
    private boolean isScrolling;

    public AbstractTreeMenuScreen(ITextComponent name) {
        super(name);

    }

    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;
        if (p_mouseClicked_5_ == 0) {
            for (TreeMenuTabGui treeTab : this.tabs.values()) {
                if (treeTab.getPage() == tabPage && treeTab.func_195627_a(i, j, p_mouseClicked_1_, p_mouseClicked_3_)
                        && !treeTab.getRootOption().getDisplay().isHidden()) {
                    setSelectedTab(treeTab.getRootOption());
                    break;
                }
            }
        } else {
            TreeMenuOption clicked = selectedTab.onMouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, i, j, p_mouseClicked_5_);
            if (clicked != null) {
                onOptionClicked(clicked);
            }
        }

        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    protected abstract void onOptionClicked(TreeMenuOption clicked);

    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        if (p_mouseDragged_5_ != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } else if (this.selectedTab != null) {
                this.selectedTab.func_195626_a(p_mouseDragged_6_, p_mouseDragged_8_);
            }

            return true;
        }
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;
        this.renderBackground(matrixStack);
        this.renderInside(matrixStack, mouseX, mouseY, i, j);
        if (maxPages != 0) {
            String page = String.format("%d / %d", tabPage + 1, maxPages + 1);
            int width = this.font.getStringWidth(page);
            RenderSystem.disableLighting();
            this.font.drawStringWithShadow(matrixStack, page, i + (252 / 2f) - (width / 2f), j - 44, -1);
        }
        this.renderWindow(matrixStack, i, j);
        this.renderToolTips(matrixStack, mouseX, mouseY, i, j);
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (this.minecraft.gameSettings.keyBindAdvancements.matchesKey(p_keyPressed_1_, p_keyPressed_2_)) {
            this.minecraft.displayGuiScreen(null);
            this.minecraft.mouseHelper.grabMouse();
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;

        try {
            populateTabs();

            if (this.selectedTab == null && !this.tabs.isEmpty()) {
                setSelectedTab(this.tabs.values().iterator().next().getRootOption());
            } else {
                setSelectedTab(this.selectedTab == null ? null : this.selectedTab.getRootOption());
            }

            int maxTabs = TreeTabType.MAX_TABS;
            if (this.tabs.size() > maxTabs) {
                int guiLeft = (this.width - 252) / 2;
                int guiTop = (this.height - 140) / 2;
                addButton(new net.minecraft.client.gui.widget.button.Button(guiLeft, guiTop - 50, 20, 20, new StringTextComponent("<"), b -> tabPage = Math.max(tabPage - 1, 0)));
                addButton(new net.minecraft.client.gui.widget.button.Button(guiLeft + 252 - 20, guiTop - 50, 20, 20, new StringTextComponent(">"), b -> tabPage = Math.min(tabPage + 1, maxPages)));
                maxPages = this.tabs.size() / maxTabs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void populateTabs() throws Exception;

    public void setSelectedTab(@Nullable TreeMenuOption advancementIn) {
        this.selectedTab = this.tabs.get(advancementIn);
    }

    private void renderInside(MatrixStack matrixStack, int p_191936_1_, int p_191936_2_, int p_191936_3_, int p_191936_4_) {
        TreeMenuTabGui tab = this.selectedTab;
        if (tab == null) {
            fill(matrixStack, p_191936_3_ + 9, p_191936_4_ + 18, p_191936_3_ + 9 + 234, p_191936_4_ + 18 + 113, -16777216);
            String s = I18n.format("perks.empty");
            int i = this.font.getStringWidth(s);
            this.font.drawString(matrixStack, s, (float) (p_191936_3_ + 9 + 117 - i / 2), (float) (p_191936_4_ + 18 + 56 - 9 / 2), -1);
            this.font.drawString(matrixStack, ":(", (float) (p_191936_3_ + 9 + 117 - this.font.getStringWidth(":(") / 2), (float) (p_191936_4_ + 18 + 113 - 9), -1);
        } else {
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float) (p_191936_3_ + 9), (float) (p_191936_4_ + 18), 0.0F);
            tab.drawContents(matrixStack);
            RenderSystem.popMatrix();
            RenderSystem.depthFunc(515);
            RenderSystem.disableDepthTest();
        }
    }

    public void renderWindow(MatrixStack matrixStack, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(WINDOW);
        this.blit(matrixStack, x, y, 0, 0, 252, 140);
        if (this.tabs.size() > 1) {
            this.minecraft.getTextureManager().bindTexture(TABS);

            for (TreeMenuTabGui treeTabGui : this.tabs.values()) {
                if (treeTabGui.getPage() == tabPage)
                    treeTabGui.drawTab(matrixStack, x, y, treeTabGui == this.selectedTab);
            }

            RenderSystem.enableRescaleNormal();
            RenderSystem.defaultBlendFunc();

            for (TreeMenuTabGui tab : this.tabs.values()) {
                if (tab.getPage() == tabPage)
                    tab.drawIcon(x, y, this.itemRenderer);
            }

            RenderSystem.disableBlend();
        }

        this.font.drawString(matrixStack, "Perks", (float) (x + 8), (float) (y + 6), 4210752);
    }

    private void renderToolTips(MatrixStack matrixStack, int p_191937_1_, int p_191937_2_, int p_191937_3_, int p_191937_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.selectedTab != null) {
            RenderSystem.pushMatrix();
            //RenderSystem.enableDepthTest();
            RenderSystem.translatef((float) (p_191937_3_ + 9), (float) (p_191937_4_ + 18), 400.0F);
            this.selectedTab.drawToolTips(matrixStack, p_191937_1_ - p_191937_3_ - 9, p_191937_2_ - p_191937_4_ - 18, p_191937_3_, p_191937_4_);
            RenderSystem.disableDepthTest();
            RenderSystem.popMatrix();
        }

        if (this.tabs.size() > 1) {
            for (TreeMenuTabGui advancementtabgui : this.tabs.values()) {
                if (advancementtabgui.getPage() == tabPage && advancementtabgui.func_195627_a(p_191937_3_, p_191937_4_, p_191937_1_, p_191937_2_)) {
                    this.renderTooltip(matrixStack, new StringTextComponent(advancementtabgui.getTitle()), p_191937_1_, p_191937_2_);
                }
            }
        }

    }

    @Nullable
    private TreeMenuTabGui getTab(TreeMenuOption option) {
        while (option.getParent() != null) {
            option = option.getParent();
        }

        return this.tabs.get(option);
    }

    public void advancementsCleared() {
        this.tabs.clear();
        this.selectedTab = null;
    }

    @Nullable
    public TreeMenuEntryGui getEntryGui(TreeMenuOption option) {
        TreeMenuTabGui tab = this.getTab(option);
        return tab == null ? null : tab.getAdvancementGui(option);
    }

    public abstract void refreshTabs();
}

