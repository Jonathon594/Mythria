package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public abstract class ProfileCreationTab extends FocusableGui implements INestedGuiEventHandler, IRenderable {
    protected final FontRenderer font;
    protected final int left;
    protected final int top;
    protected StringTextComponent title;
    protected final List<IGuiEventListener> children = Lists.newArrayList();
    protected List<Widget> widgets = Lists.newArrayList();
    protected ScreenProfileCreation parent;

    protected boolean selected = false;


    public ProfileCreationTab(ScreenProfileCreation parent, StringTextComponent title, FontRenderer font, int left, int top) {
        this.parent = parent;
        this.title = title;
        this.font = font;
        this.left = left;
        this.top = top;
    }

    public void renderTitle(MatrixStack matrixStack, FontRenderer fontRenderer, int color, int x, int y) {
        drawCenteredString(matrixStack, fontRenderer, title, color, x, y);
    }

    public <T extends Widget> T addWidget(T widget) {
        widgets.add(widget);
        return this.addListener(widget);
    }

    protected <T extends IGuiEventListener> T addListener(T listener) {
        this.children.add(listener);
        return listener;
    }

    public List<? extends IGuiEventListener> getEventListeners() {
        return this.children;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for(int i = 0; i < this.widgets.size(); ++i) {
            this.widgets.get(i).render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(selected) {
            for (IGuiEventListener iguieventlistener : this.getEventListeners()) {
                if (iguieventlistener.mouseClicked(mouseX, mouseY, button)) {
                    this.setListener(iguieventlistener);
                    if (button == 0) {
                        this.setDragging(true);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isSelected() {
        return selected;
    }

    public ProfileCreationTab setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }
}
