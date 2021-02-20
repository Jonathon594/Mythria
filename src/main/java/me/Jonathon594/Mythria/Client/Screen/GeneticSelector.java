package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.function.Supplier;

class GeneticSelector extends GuiButtonSelector {
    public GeneticSelector(int xPosition, int yPosition, IPressable buttonHandler, Supplier<List<String>> optionFactory) {
        super(xPosition, yPosition, 200, 20, "Race: %s", buttonHandler, optionFactory);
    }

    @Override
    public void updateText() {
        if (index >= optionFactory.get().size()) return;
        String name = optionFactory.get().get(index);
        setMessage(new StringTextComponent(format.replace("%s",
                MythriaRegistries.GENETICS.getValue(new ResourceLocation(name)).getDisplayName())));

        selectedName = name;
    }
}
