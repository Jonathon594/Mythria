package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.StringTextComponent;

public abstract class IntSlider extends AbstractSlider {
    private int maxValue;

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        func_230972_a_();
        func_230979_b_();
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        func_230972_a_();
        func_230979_b_();
    }

    private int minValue;

    public IntSlider(int left, int top, int width, int minValue, int maxValue) {
        super(left, top, width, 20, StringTextComponent.EMPTY, 0);
        this.minValue = minValue;
        this.maxValue = maxValue;
        func_230979_b_();
    }

    @Override
    protected void func_230972_a_() {
        int size = maxValue - minValue;
        sliderValue = Math.round(sliderValue * size) / (double) size;
        valueChanged();
    }

    protected abstract void valueChanged();

    public int getValueInt() {
        return (int) ((sliderValue * (maxValue - minValue)) + minValue);
    }
}
