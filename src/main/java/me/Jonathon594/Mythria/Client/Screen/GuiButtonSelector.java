package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.function.Supplier;

public class GuiButtonSelector extends Button {
    protected final Supplier<List<String>> optionFactory;
    protected String format;
    protected int index = 0;
    protected String selectedName = "";
    public GuiButtonSelector(int xPos, int yPos, int width, int height, String format, IPressable handler, Supplier<List<String>> optionFactory) {
        super(xPos, yPos, width, height, new StringTextComponent(format.replace("%", optionFactory.get().size() == 0 ? "" : optionFactory.get().get(0))), handler);
        this.format = format;
        this.optionFactory = optionFactory;
        updateIndex();
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void updateIndex() {
        int size = this.optionFactory.get().size();
        if (index >= size) {
            index = size - 1;
        }
        if (index < 0 && size > 0) index = 0;

        updateText();
    }

    public void updateText() {
        if (index >= optionFactory.get().size()) return;
        if (index == -1) {
            setMessage(new StringTextComponent(""));
            selectedName = "";
            return;
        }
        String name = optionFactory.get().get(index);
        setMessage(new StringTextComponent(format.replace("%s", name)));

        selectedName = name;
    }

    public int getIndex() {
        return index;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(mouseButton)) {
                boolean clicked = this.clicked(mouseX, mouseY);
                if (clicked) {
                    this.playDownSound(Minecraft.getInstance().getSoundHandler());
                    cycleOptions(mouseButton);
                    onPress();
                    return true;
                }
            }

        }
        return false;
    }

    private void cycleOptions(int mouseButton) {
        if (mouseButton == 0) {
            index++;
        } else {
            index--;
        }

        if (index >= optionFactory.get().size())
            index = 0;
        if (index < 0)
            index = optionFactory.get().size() - 1;

        updateText();
    }

    @Override
    protected boolean isValidClickButton(int p_isValidClickButton_1_) {
        return p_isValidClickButton_1_ == 0 || p_isValidClickButton_1_ == 1;
    }

    public String getCurrentOption() {
        return optionFactory.get().get(index);
    }

    public String getSelectedName() {
        return selectedName;
    }
}
