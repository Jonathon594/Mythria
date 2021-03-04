package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

public abstract class MessageSlider extends Slider {
    public MessageSlider(int xPos, int yPos, int width, int height,
                         double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, IPressable handler) {
        super(xPos, yPos, width, height, StringTextComponent.EMPTY, StringTextComponent.EMPTY,
                minVal, maxVal, currentVal, showDec, drawStr, handler);
    }

    @Override
    public void updateSlider() {
        super.updateSlider();

        updateText();
    }

    protected abstract void updateText();
}
