package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.Map;

public class TreeMenuTabGui extends AbstractGui {
    private final Minecraft minecraft;
    private final AbstractTreeMenuScreen screen;
    private final TreeTabType type;
    private final int index;
    private final Map<TreeMenuOption, TreeMenuEntryGui> guis = Maps.newLinkedHashMap();
    private TreeMenuOption rootOption;
    private DisplayInfo display;
    private ItemStack icon;
    private String title;
    private TreeMenuEntryGui root;
    private double scrollX;
    private double scrollY;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private float fade;
    private boolean centered;
    private int page;

    public TreeMenuTabGui(Minecraft mc, AbstractTreeMenuScreen screen, TreeTabType type, int index, int page, DisplayInfo info) {
        this(mc, screen, type, index, info);
        this.page = page;
    }

    public TreeMenuTabGui(Minecraft mc, AbstractTreeMenuScreen screen, TreeTabType type, int index, DisplayInfo display) {
        this.minecraft = mc;
        this.screen = screen;
        this.type = type;
        this.index = index;
        setDisplayData(display);
    }

    @Nullable
    public static TreeMenuTabGui create(Minecraft mc, AbstractTreeMenuScreen screen, int index, DisplayInfo display) {
        for (TreeTabType advancementtabtype : TreeTabType.values()) {
            if ((index % TreeTabType.MAX_TABS) < advancementtabtype.getMax()) {
                return new TreeMenuTabGui(mc, screen, advancementtabtype, index % TreeTabType.MAX_TABS, index / TreeTabType.MAX_TABS, display);
            }

            index -= advancementtabtype.getMax();
        }

        return null;
    }

    public TreeMenuEntryGui getRoot() {
        return root;
    }

    public void setDisplayData(DisplayInfo display) {
        this.display = display;
        this.icon = display.getIcon();
        this.title = display.getTitle().getString();
    }

    private void addGuiAdvancement(TreeMenuEntryGui p_193937_1_, TreeMenuOption p_193937_2_) {
        this.guis.put(p_193937_2_, p_193937_1_);
        int i = p_193937_1_.getX();
        int j = i + 28;
        int k = p_193937_1_.getY();
        int l = k + 27;
        this.minX = Math.min(this.minX, i);
        this.maxX = Math.max(this.maxX, j);
        this.minY = Math.min(this.minY, k);
        this.maxY = Math.max(this.maxY, l);

        for (TreeMenuEntryGui treeMenuEntry : this.guis.values()) {
            treeMenuEntry.attachToParent();
        }

    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public int getPage() {
        return page;
    }

    public TreeMenuOption getRootOption() {
        return this.rootOption;
    }

    public String getTitle() {
        return this.title;
    }

    public void drawTab(MatrixStack matrixStack, int p_191798_1_, int p_191798_2_, boolean p_191798_3_) {
        this.type.draw(matrixStack, this, p_191798_1_, p_191798_2_, p_191798_3_, this.index);
    }

    public void drawIcon(int p_191796_1_, int p_191796_2_, ItemRenderer p_191796_3_) {
        this.type.drawIcon(p_191796_1_, p_191796_2_, this.index, p_191796_3_, this.icon);
    }

    public void drawContents(MatrixStack matrixStack) {
        if (!this.centered) {
            this.scrollX = (117 - (this.maxX + this.minX) / 2f);
            this.scrollY = (56 - (this.maxY + this.minY) / 2f);
            this.centered = true;
        }

        RenderSystem.pushMatrix();
        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.depthFunc(518);
        fill(matrixStack, 234, 113, 0, 0, -16777216);
        RenderSystem.depthFunc(515);
        ResourceLocation resourcelocation = this.display.getBackground();
        if (resourcelocation != null) {
            this.minecraft.getTextureManager().bindTexture(resourcelocation);
        } else {
            this.minecraft.getTextureManager().bindTexture(TextureManager.RESOURCE_LOCATION_EMPTY);
        }

        int i = MathHelper.floor(this.scrollX);
        int j = MathHelper.floor(this.scrollY);
        int k = i % 16;
        int l = j % 16;

        for (int i1 = -1; i1 <= 15; ++i1) {
            for (int j1 = -1; j1 <= 8; ++j1) {
                blit(matrixStack, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }

        this.root.drawConnectionLineToParent(matrixStack, i, j, true);
        this.root.drawConnectionLineToParent(matrixStack, i, j, false);
        this.root.draw(matrixStack, i, j);
        RenderSystem.depthFunc(518);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.depthFunc(515);
        RenderSystem.popMatrix();
    }

    public void drawToolTips(MatrixStack matrixStack, int p_192991_1_, int p_192991_2_, int p_192991_3_, int p_192991_4_) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0F, 0.0F, 200.0F);
        fill(matrixStack, 0, 0, 234, 113, MathHelper.floor(this.fade * 255.0F) << 24);
        boolean flag = false;
        int i = MathHelper.floor(this.scrollX);
        int j = MathHelper.floor(this.scrollY);
        if (p_192991_1_ > 0 && p_192991_1_ < 234 && p_192991_2_ > 0 && p_192991_2_ < 113) {
            for (TreeMenuEntryGui entry : this.guis.values()) {
                if (entry.isMouseOver(i, j, p_192991_1_, p_192991_2_)) {
                    flag = true;
                    entry.drawHover(matrixStack, i, j, this.fade, p_192991_3_, p_192991_4_);
                    break;
                }
            }
        }

        RenderSystem.popMatrix();
        if (flag) {
            this.fade = MathHelper.clamp(this.fade + 0.02F, 0.0F, 0.3F);
        } else {
            this.fade = MathHelper.clamp(this.fade - 0.04F, 0.0F, 1.0F);
        }

    }

    public boolean func_195627_a(int p_195627_1_, int p_195627_2_, double p_195627_3_, double p_195627_5_) {
        return this.type.func_198891_a(p_195627_1_, p_195627_2_, this.index, p_195627_3_, p_195627_5_);
    }

    public void func_195626_a(double p_195626_1_, double p_195626_3_) {
        if (this.maxX - this.minX > 234) {
            this.scrollX = MathHelper.clamp(this.scrollX + p_195626_1_, -(this.maxX - 234), 0.0D);
        }

        if (this.maxY - this.minY > 113) {
            this.scrollY = MathHelper.clamp(this.scrollY + p_195626_3_, -(this.maxY - 113), 0.0D);
        }

    }

    public void addOption(TreeMenuOption option) {
        if (option.getDisplay() != null) {
            TreeMenuEntryGui treeMenuGui = new TreeMenuEntryGui(this, this.minecraft, option, option.getDisplay());
            treeMenuGui.setHighlight(option.isHighlight());

            if (option.getParent() == null) {
                rootOption = option;
                root = treeMenuGui;
            }

            this.addGuiAdvancement(treeMenuGui, option);
        }
    }

    @Nullable
    public TreeMenuEntryGui getAdvancementGui(TreeMenuOption p_191794_1_) {
        return this.guis.get(p_191794_1_);
    }

    public AbstractTreeMenuScreen getScreen() {
        return this.screen;
    }

    public TreeMenuOption getTreeMenuOption(ResourceLocation id) {
        for (TreeMenuOption option : guis.keySet()) {
            if (option.getId().equals(id)) return option;
        }
        return null;
    }

    public TreeMenuOption onMouseClicked(double mouseX, double mouseY, int i, int j, int button) {
        for (Map.Entry<TreeMenuOption, TreeMenuEntryGui> e : getGuis().entrySet()) {
            if (e.getKey() == null) continue;
            if (e.getValue().isMouseOver((int) this.scrollX, (int) this.scrollY, (int) mouseX - i - 9, (int) mouseY - j - 18)) {
                return e.getKey();
            }
        }
        return null;
    }

    public Map<me.Jonathon594.Mythria.Client.Screen.TreeMenuOption, TreeMenuEntryGui> getGuis() {
        return guis;
    }
}