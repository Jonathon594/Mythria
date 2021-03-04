package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.util.text.StringTextComponent;

class ProfileAgeSlider extends IntSlider {
    public ProfileAgeSlider(int left, int top, int width) {
        super(left, top, width, 12, 46);
    }

    @Override
    protected void func_230979_b_() {
        setMessage(new StringTextComponent("Age: " + getValueInt()));
    }

    @Override
    protected void valueChanged() {
        
    }
}
