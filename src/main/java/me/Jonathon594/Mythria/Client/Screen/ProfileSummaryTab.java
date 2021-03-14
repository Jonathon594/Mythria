package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketProfileCreation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class ProfileSummaryTab extends ProfileCreationTab {
    private final Button create;

    public ProfileSummaryTab(ScreenProfileCreation parent, FontRenderer font, int left, int top) {
        super(parent, new StringTextComponent("Finish"), font, left, top);
        create = addWidget(new Button(left + 8, top + 139, 157, 20,
                new StringTextComponent("Begin Adventure!"), this::onCreate));
    }

    private void onCreate(Button button) {
        if (parent.canCreate()) {
            ProfileNamesTab profileNamesTab = parent.profileNamesTab;
            ProfileAppearanceTab profileLooksTab = parent.profileLooksTab;
            MythriaPacketHandler.sendToServer(
                    new CPacketProfileCreation(profileNamesTab.firstName.getText(), profileNamesTab.middleName.getText(),
                            profileNamesTab.lastName.getText(), profileNamesTab.month.getValueInt(), profileNamesTab.day.getValueInt(),
                            profileLooksTab.getSelectedGeneticType(), profileLooksTab.getSelectedGender(), profileLooksTab.getSelectedHair(),
                            profileLooksTab.getSelectedEyes(), profileLooksTab.getSelectedClothing(), profileLooksTab.getSelectedSkin(),
                            profileLooksTab.getSelectedUnique(), parent.profileGiftTab.getSelectedGift()));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
