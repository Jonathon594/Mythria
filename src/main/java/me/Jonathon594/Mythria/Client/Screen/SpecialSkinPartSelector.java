package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.function.Supplier;

class SpecialSkinPartSelector extends GuiButtonSkinPartSelector {
    public SpecialSkinPartSelector(int xPosition, int yPosition, IPressable buttonHandler, Supplier<List<String>> optionFactory) {
        super(xPosition, yPosition += 25, 200, 20, "Race Trait: %s", buttonHandler, optionFactory);
    }

    @Override
    public void updateText() {
        super.updateText();
        if (index == -1) {
            setMessage(new StringTextComponent(format.replace("%s", "None")));
        }
    }
}
