package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ScreenProfileCreation extends Screen {
    private static final ResourceLocation BACKGROUND = new MythriaResourceLocation("textures/gui/profile_creation.png");
    protected final int xSize = 256;
    protected final int ySize = 166;
    protected int left;
    protected int top;

    protected List<ProfileCreationTab> tabs = new ArrayList<>();
    protected int selectedTab = 0;
    public ProfileNamesTab profileNamesTab;
    public ProfileAppearanceTab profileLooksTab;
    public ProfileGiftTab profileGiftTab;

    public ScreenProfileCreation() {
        super(new StringTextComponent("Profile Creation"));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        this.blit(matrixStack, left, top, 0, 0, xSize, ySize);

        for (int i = 0; i < tabs.size(); i++) {
            int xuv = (i == 0 ? 0 : (i == 3 ? 2 : 1)) * 64;
            boolean selected = i == selectedTab;
            int xPos = left + i * 64;
            int yPos = top - 17;
            this.blit(matrixStack, xPos, yPos, xuv, selected ? 187 : 166, 64, selected ? 21 : 20);
        }
        for (int i = 0; i < tabs.size(); i++) {
            ProfileCreationTab tab = tabs.get(i);
            int xPos = left + i * 64;
            int yPos = top - 17;
            tab.renderTitle(matrixStack, font, xPos + 32, yPos + 6, 0xFFFFFFFF);
        }
        getSelectedTab().render(matrixStack, mouseX, mouseY, partialTicks);

        float posX = left + 207.5f;
        int posY = top + 100;
        ScreenUtils.drawEntityOnScreen(posX, posY, 42, (float) posX - mouseX,
                (float) posY - mouseY - 70, minecraft.player);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean b = super.mouseClicked(mouseX, mouseY, button);
        for (int i = 0; i < tabs.size(); i++) {
            int xPos = left + i * 64;
            int yPos = top - 17;
            if (mouseX >= xPos && mouseX < xPos + 64 && mouseY >= yPos && mouseY < yPos + 17) {
                setSelectedTab(i);
            }
        }
        return b;
    }

    protected void setSelectedTab(int index) {
        for (int i = 0; i < tabs.size(); i++) {
            ProfileCreationTab tab = tabs.get(i);
            if (i == index) tab.selected = true;
            else tab.selected = false;
        }
        selectedTab = index;
    }

    protected ProfileCreationTab getSelectedTab() {
        return tabs.size() > selectedTab ? tabs.get(selectedTab) : null;
    }

    @Override
    protected void init() {
        left = width / 2 - xSize / 2;
        top = height / 2 - ySize / 2;
        tabs.clear();
        tabs.add(profileNamesTab = new ProfileNamesTab(this, font, left, top));
        tabs.add(profileLooksTab = new ProfileAppearanceTab(this, font, left, top));
        tabs.add(profileGiftTab = new ProfileGiftTab(this, font, left, top));
        tabs.add(new ProfileSummaryTab(this, font, left, top));
        for (ProfileCreationTab tab : tabs) {
            addListener(tab);
        }
        setSelectedTab(0);
    }

    public boolean canCreate() {
        if (profileNamesTab.firstName.getText().length() < 3 ||
                profileNamesTab.middleName.getText().length() < 3 ||
                profileNamesTab.lastName.getText().length() < 3) {
            return false;
        }
        return true;
    }
}
