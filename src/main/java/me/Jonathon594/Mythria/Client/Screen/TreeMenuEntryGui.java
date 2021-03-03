package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.advancements.AdvancementState;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

import javax.annotation.Nullable;
import java.util.List;

public class TreeMenuEntryGui extends AbstractGui {
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/advancements/widgets.png");
    private static final int[] LINE_BREAK_VALUES = new int[]{0, 10, -10, 25, -25};
    private final TreeMenuTabGui treeMenuTabGui;
    private final TreeMenuOption advancement;
    private final Minecraft minecraft;
    private final List<TreeMenuEntryGui> children = Lists.newArrayList();
    private final int x;
    private final int y;
    private IReorderingProcessor title;
    private int width;
    private List<IReorderingProcessor> description;
    private DisplayInfo displayInfo;
    private TreeMenuEntryGui parent;
    private boolean highlight = false;

    public TreeMenuEntryGui(TreeMenuTabGui tab, Minecraft mc, TreeMenuOption option, DisplayInfo displayInfo) {
        this.treeMenuTabGui = tab;
        this.advancement = option;

        this.minecraft = mc;

        this.x = MathHelper.floor(displayInfo.getX() * 28.0F);
        this.y = MathHelper.floor(displayInfo.getY() * 27.0F);

        setDisplayData(displayInfo);
    }

    private static float getTextWidth(CharacterManager manager, List<ITextProperties> text) {
        return (float) text.stream().mapToDouble(manager::func_238356_a_).max().orElse(0.0D);
    }

    private void setDisplayData(DisplayInfo displayInfo) {
        this.displayInfo = displayInfo;
        this.title = LanguageMap.getInstance().func_241870_a(minecraft.fontRenderer.func_238417_a_(displayInfo.getTitle(), 163));
        int l = 29 + minecraft.fontRenderer.func_243245_a(this.title) + 16;
        this.description = LanguageMap.getInstance().func_244260_a(getDescriptionLines(TextComponentUtils.func_240648_a_(displayInfo.getDescription().deepCopy(), Style.EMPTY.setFormatting(displayInfo.getFrame().getFormat())), l));

        for (IReorderingProcessor ireorderingprocessor : this.description) {
            l = Math.max(l, minecraft.fontRenderer.func_243245_a(ireorderingprocessor));
        }

        this.width = l + 16;
    }

    private List<ITextProperties> getDescriptionLines(ITextComponent component, int maxWidth) {
        CharacterManager charactermanager = this.minecraft.fontRenderer.getCharacterManager();
        List<ITextProperties> list = null;
        float f = Float.MAX_VALUE;

        for (int i : LINE_BREAK_VALUES) {
            List<ITextProperties> list1 = charactermanager.func_238362_b_(component, maxWidth - i, Style.EMPTY);
            float f1 = Math.abs(getTextWidth(charactermanager, list1) - (float) maxWidth);
            if (f1 <= 10.0F) {
                return list1;
            }

            if (f1 < f) {
                f = f1;
                list = list1;
            }
        }

        return list;
    }

    public void setDisplayInfo(DisplayInfo displayInfo) {
        this.displayInfo = displayInfo;
        setDisplayData(displayInfo);
    }

    public void drawConnectionLineToParent(MatrixStack matrixStack, int x, int y, boolean dropShadow) {
        if (displayInfo.isHidden()) return;

        if (this.parent != null) {
            int i = x + this.parent.x + 13;
            int j = x + this.parent.x + 26 + 4;
            int k = y + this.parent.y + 13;
            int l = x + this.x + 13;
            int i1 = y + this.y + 13;
            int j1 = dropShadow ? -16777216 : -1;
            if (dropShadow) {
                this.hLine(matrixStack, j, i, k - 1, j1);
                this.hLine(matrixStack, j + 1, i, k, j1);
                this.hLine(matrixStack, j, i, k + 1, j1);
                this.hLine(matrixStack, l, j - 1, i1 - 1, j1);
                this.hLine(matrixStack, l, j - 1, i1, j1);
                this.hLine(matrixStack, l, j - 1, i1 + 1, j1);
                this.vLine(matrixStack, j - 1, i1, k, j1);
                this.vLine(matrixStack, j + 1, i1, k, j1);
            } else {
                this.hLine(matrixStack, j, i, k, j1);
                this.hLine(matrixStack, l, j, i1, j1);
                this.vLine(matrixStack, j, i1, k, j1);
            }
        }

        for (TreeMenuEntryGui option : this.children) {
            option.drawConnectionLineToParent(matrixStack, x, y, dropShadow);
        }

    }

    public void draw(MatrixStack matrixStack, int p_191817_1_, int p_191817_2_) {
        if (!this.displayInfo.isHidden()) {
            this.minecraft.getTextureManager().bindTexture(WIDGETS);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            this.blit(matrixStack, p_191817_1_ + this.x + 3, p_191817_2_ + this.y, this.displayInfo.getFrame().getIcon(), 128 + (highlight ? 0 : 26), 26, 26);
            RenderHelper.enableStandardItemLighting();
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(null, this.displayInfo.getIcon(), p_191817_1_ + this.x + 8, p_191817_2_ + this.y + 5);
        }

        for (TreeMenuEntryGui gui : this.children) {
            gui.draw(matrixStack, p_191817_1_, p_191817_2_);
        }

    }

    public void drawHover(MatrixStack matrixStack, int p_191821_1_, int p_191821_2_, float p_191821_3_, int p_191821_4_, int p_191821_5_) {
        boolean flag = p_191821_4_ + p_191821_1_ + this.x + this.width + 26 >= this.treeMenuTabGui.getScreen().width;
        String s = "";
        int i = s == null ? 0 : this.minecraft.fontRenderer.getStringWidth(s);
        boolean flag1 = 113 - p_191821_2_ - this.y - 26 <= 6 + this.description.size() * 9;
        float f = highlight ? 1.0f : 0.0f;
        int j = MathHelper.floor(f * (float) this.width);
        AdvancementState advancementstate;
        AdvancementState advancementstate1;
        AdvancementState advancementstate2;
        if (f >= 1.0F) {
            j = this.width / 2;
            advancementstate = AdvancementState.OBTAINED;
            advancementstate1 = AdvancementState.OBTAINED;
            advancementstate2 = AdvancementState.OBTAINED;
        } else if (j < 2) {
            j = this.width / 2;
            advancementstate = AdvancementState.UNOBTAINED;
            advancementstate1 = AdvancementState.UNOBTAINED;
            advancementstate2 = AdvancementState.UNOBTAINED;
        } else if (j > this.width - 2) {
            j = this.width / 2;
            advancementstate = AdvancementState.OBTAINED;
            advancementstate1 = AdvancementState.OBTAINED;
            advancementstate2 = AdvancementState.UNOBTAINED;
        } else {
            advancementstate = AdvancementState.OBTAINED;
            advancementstate1 = AdvancementState.UNOBTAINED;
            advancementstate2 = AdvancementState.UNOBTAINED;
        }

        int k = this.width - j;
        this.minecraft.getTextureManager().bindTexture(WIDGETS);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        int l = p_191821_2_ + this.y;
        int i1;
        if (flag) {
            i1 = p_191821_1_ + this.x - this.width + 26 + 6;
        } else {
            i1 = p_191821_1_ + this.x;
        }

        int j1 = 32 + this.description.size() * 9;
        if (!this.description.isEmpty()) {
            if (flag1) {
                this.drawDescriptionBox(matrixStack, i1, l + 26 - j1, this.width, j1, 10, 200, 26, 0, 52);
            } else {
                this.drawDescriptionBox(matrixStack, i1, l, this.width, j1, 10, 200, 26, 0, 52);
            }
        }

        this.blit(matrixStack, i1, l, 0, advancementstate.getId() * 26, j, 26);
        this.blit(matrixStack, i1 + j, l, 200 - k, advancementstate1.getId() * 26, k, 26);
        this.blit(matrixStack, p_191821_1_ + this.x + 3, p_191821_2_ + this.y, this.displayInfo.getFrame().getIcon(), 128 + advancementstate2.getId() * 26, 26, 26);
        if (flag) {
            this.minecraft.fontRenderer.func_238407_a_(matrixStack, this.title, (float) (i1 + 5), (float) (p_191821_2_ + this.y + 9), -1);
            if (s != null) {
                this.minecraft.fontRenderer.drawStringWithShadow(matrixStack, s, (float) (p_191821_1_ + this.x - i), (float) (p_191821_2_ + this.y + 9), -1);
            }
        } else {
            this.minecraft.fontRenderer.func_238407_a_(matrixStack, this.title, (float) (p_191821_1_ + this.x + 32), (float) (p_191821_2_ + this.y + 9), -1);
            if (s != null) {
                this.minecraft.fontRenderer.drawStringWithShadow(matrixStack, s, (float) (p_191821_1_ + this.x + this.width - i - 5), (float) (p_191821_2_ + this.y + 9), -1);
            }
        }

        if (flag1) {
            for (int k1 = 0; k1 < this.description.size(); ++k1) {
                this.minecraft.fontRenderer.func_238422_b_(matrixStack, this.description.get(k1), (float) (i1 + 5), (float) (l + 26 - j1 + 7 + k1 * 9), -5592406);
            }
        } else {
            for (int l1 = 0; l1 < this.description.size(); ++l1) {
                this.minecraft.fontRenderer.func_238422_b_(matrixStack, this.description.get(l1), (float) (i1 + 5), (float) (p_191821_2_ + this.y + 9 + 17 + l1 * 9), -5592406);
            }
        }

        this.minecraft.getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(this.displayInfo.getIcon(), p_191821_1_ + this.x + 8, p_191821_2_ + this.y + 5);
    }

    protected void drawDescriptionBox(MatrixStack matrixStack, int x, int y, int width, int height, int padding, int uWidth, int vHeight, int uOffset, int vOffset) {
        this.blit(matrixStack, x, y, uOffset, vOffset, padding, padding);
        this.drawDescriptionBoxBorder(matrixStack, x + padding, y, width - padding - padding, padding, uOffset + padding, vOffset, uWidth - padding - padding, vHeight);
        this.blit(matrixStack, x + width - padding, y, uOffset + uWidth - padding, vOffset, padding, padding);
        this.blit(matrixStack, x, y + height - padding, uOffset, vOffset + vHeight - padding, padding, padding);
        this.drawDescriptionBoxBorder(matrixStack, x + padding, y + height - padding, width - padding - padding, padding, uOffset + padding, vOffset + vHeight - padding, uWidth - padding - padding, vHeight);
        this.blit(matrixStack, x + width - padding, y + height - padding, uOffset + uWidth - padding, vOffset + vHeight - padding, padding, padding);
        this.drawDescriptionBoxBorder(matrixStack, x, y + padding, padding, height - padding - padding, uOffset, vOffset + padding, uWidth, vHeight - padding - padding);
        this.drawDescriptionBoxBorder(matrixStack, x + padding, y + padding, width - padding - padding, height - padding - padding, uOffset + padding, vOffset + padding, uWidth - padding - padding, vHeight - padding - padding);
        this.drawDescriptionBoxBorder(matrixStack, x + width - padding, y + padding, padding, height - padding - padding, uOffset + uWidth - padding, vOffset + padding, uWidth, vHeight - padding - padding);
    }

    protected void drawDescriptionBoxBorder(MatrixStack matrixStack, int x, int y, int borderToU, int borderToV, int uOffset, int vOffset, int uWidth, int vHeight) {
        for (int i = 0; i < borderToU; i += uWidth) {
            int j = x + i;
            int k = Math.min(uWidth, borderToU - i);

            for (int l = 0; l < borderToV; l += vHeight) {
                int i1 = y + l;
                int j1 = Math.min(vHeight, borderToV - l);
                this.blit(matrixStack, j, i1, uOffset, vOffset, k, j1);
            }
        }

    }

    public boolean isMouseOver(int p_191816_1_, int p_191816_2_, int p_191816_3_, int p_191816_4_) {
        if (!this.displayInfo.isHidden()) {
            int i = p_191816_1_ + this.x;
            int j = i + 26;
            int k = p_191816_2_ + this.y;
            int l = k + 26;
            return p_191816_3_ >= i && p_191816_3_ <= j && p_191816_4_ >= k && p_191816_4_ <= l;
        } else {
            return false;
        }
    }

    public void attachToParent() {
        if (this.parent == null && this.advancement.getParent() != null) {
            this.parent = this.getFirstVisibleParent(this.advancement);
            if (this.parent != null) {
                this.parent.addGuiEntry(this);
            }
        }

    }

    @Nullable
    private TreeMenuEntryGui getFirstVisibleParent(TreeMenuOption advancementIn) {
        do {
            advancementIn = advancementIn.getParent();
        } while (advancementIn != null && advancementIn.getDisplay() == null);

        if (advancementIn != null && advancementIn.getDisplay() != null) {
            return this.treeMenuTabGui.getAdvancementGui(advancementIn);
        } else {
            return null;
        }
    }

    public void addGuiEntry(TreeMenuEntryGui gui) {
        this.children.add(gui);
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }
}
