package me.Jonathon594.Mythria.Client.Screen;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractMythriaScreen extends Screen {
    protected AbstractMythriaScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.setDragging(false);
        for(IGuiEventListener iGuiEventListener : getEventListeners()) {
            iGuiEventListener.mouseReleased(mouseX, mouseY, button);
        }
        return this.getEventListenerForPos(mouseX, mouseY).filter((listener) -> {
            return listener.mouseReleased(mouseX, mouseY, button);
        }).isPresent();
    }
}
