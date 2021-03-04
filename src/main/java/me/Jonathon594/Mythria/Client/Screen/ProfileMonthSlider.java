package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.DataTypes.Time.Month;
import me.Jonathon594.Mythria.Managers.TimeManager;
import net.minecraft.util.text.StringTextComponent;

class ProfileMonthSlider extends IntSlider {
    private final ProfileNamesTab profileNamesTab;

    public ProfileMonthSlider(int left, int top, int width, ProfileNamesTab profileNamesTab) {
        super(left, top, width, 0, TimeManager.getMonths().size() - 1);
        this.profileNamesTab = profileNamesTab;
    }

    @Override
    protected void func_230979_b_() {
        setMessage(new StringTextComponent("Birth Month: " + TimeManager.getMonths().get(getValueInt()).getName()));
    }

    @Override
    protected void valueChanged() {
        Month month = TimeManager.getMonths().get(getValueInt());
        profileNamesTab.day.setMaxValue(month.getLength());
    }
}
