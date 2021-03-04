package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

public class ProfileNamesTab extends ProfileCreationTab {

    public final TextFieldWidget firstName;
    public final TextFieldWidget middleName;
    public final TextFieldWidget lastName;

    public final ProfileMonthSlider month;
    public final ProfileDaySlider day;
    public final ProfileAgeSlider age;
    public ProfileNamesTab(ScreenProfileCreation parent, FontRenderer font, int left, int top) {
        super(parent, new StringTextComponent("Profile"), font, left, top);
        int y = this.top + 8;
        int textWidth = 95;
        firstName = addWidget(new TextFieldWidget(font, left + 169 - 100, y, textWidth, 16,
                StringTextComponent.EMPTY));
        middleName = addWidget(new TextFieldWidget(font, left + 169 - 100, y+= 22, textWidth, 16,
                StringTextComponent.EMPTY));
        lastName = addWidget(new TextFieldWidget(font, left + 169 - 100, y += 22, textWidth, 16,
                StringTextComponent.EMPTY));

        month = addWidget(new ProfileMonthSlider(left + 9, y += 22, 157, this));
        day = addWidget(new ProfileDaySlider(left + 9, y += 22, 157, 1, 31));
        age = addWidget(new ProfileAgeSlider(left + 9, y + 22, 157));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        int x = left + 6;
        int y = top + 11;
        drawString(matrixStack, font, "First Name:" , x + 5, y, 0xFFFFFFFF);
        drawString(matrixStack, font, "Middle Name:" , x, y += 22, 0xFFFFFFFF);
        drawString(matrixStack, font, "Last Name:" , x + 7, y + 22, 0xFFFFFFFF);
    }
}
