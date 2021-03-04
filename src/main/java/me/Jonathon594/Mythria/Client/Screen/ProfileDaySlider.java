package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.util.text.StringTextComponent;

public class ProfileDaySlider extends IntSlider {

    public ProfileDaySlider(int left, int top, int width, int minValue, int maxValue) {
        super(left, top, width, minValue, maxValue);
    }

    @Override
    protected void valueChanged() {

    }

    @Override
    protected void func_230979_b_() {
        setMessage(new StringTextComponent("Birth Day: " + getValueInt()));
    }
}
