package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ProfileGiftTab extends ProfileCreationTab {
    public static final ResourceLocation TEXT_BOX = new MythriaResourceLocation("textures/gui/profile_creation_text_box.png");
    public final GiftSelectorButton giftSelectorButton;

    public ProfileGiftTab(ScreenProfileCreation parent, FontRenderer font, int left, int top) {
        super(parent, new StringTextComponent("Gift"), font, left, top);
        giftSelectorButton = addWidget(new GiftSelectorButton(left + 8, top + 139, 157, 20, "Gift: %s", (slider) -> {
        }, this::getValidGiftNames));
    }

    public Origin getSelectedGift() {
        return MythriaRegistries.SPAWN_GIFTS.getValue(new ResourceLocation(giftSelectorButton.getSelectedName()));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        Minecraft.getInstance().textureManager.bindTexture(TEXT_BOX);
        blit(matrixStack, left + 8, top + 7, 0, 0, 157, 128);
        ITextComponent description = getSelectedGift().getDescription();
        int i = 0;
        for (IReorderingProcessor processor : font.trimStringToWidth(description, 154)) {
            font.func_238422_b_(matrixStack, processor, left + 11, top + 10 + i * 9, 0xFFFFFFFF);
            i++;
        }
    }

    private List<String> getValidGiftNames() {
        List<String> list = new ArrayList<>();
        for (Origin gift : parent.profileLooksTab.getSelectedGeneticType().getAllowedSpawnGifts()) {
            list.add(gift.getRegistryName().toString());
        }
        return list;
    }
}
