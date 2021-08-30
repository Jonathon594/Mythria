package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.function.Supplier;

public class GiftSelectorButton extends GuiButtonSelector {

    public GiftSelectorButton(int xPos, int yPos, int width, int height, String format, IPressable handler, Supplier<List<String>> optionFactory) {
        super(xPos, yPos, width, height, format, handler, optionFactory);

    }


    @Override
    public void updateText() {
        if (index >= optionFactory.get().size()) return;
        if (index == -1) {
            setMessage(new StringTextComponent(""));
            selectedName = "";
            return;
        }
        String name = optionFactory.get().get(index);
        String displayName = MythriaRegistries.ORIGINS.getValue(new ResourceLocation(name)).getDisplayName();
        setMessage(new StringTextComponent(format.replace("%s", displayName)));

        selectedName = name;
    }
}
