package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.function.Supplier;

class SpecialSkinPartSelector extends GuiButtonSkinPartSelector {
    public SpecialSkinPartSelector(int xPos, int yPos, int width, int height, IPressable buttonHandler, Supplier<List<String>> optionFactory) {
        super(xPos, yPos, width, height, "Race Trait: %s", buttonHandler, optionFactory);
    }

    @Override
    public void updateText() {
        super.updateText();
        if (index == -1) {
            setMessage(new StringTextComponent(format.replace("%s", "None")));
        }
    }
}
