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

public class ProfileOriginTab extends ProfileCreationTab {
    public static final ResourceLocation TEXT_BOX = new MythriaResourceLocation("textures/gui/profile_creation_text_box.png");
    public final GiftSelectorButton originSelectorButton;

    public ProfileOriginTab(ScreenProfileCreation parent, FontRenderer font, int left, int top) {
        super(parent, new StringTextComponent("Origin"), font, left, top);
        originSelectorButton = addWidget(new GiftSelectorButton(left + 8, top + 139, 157, 20, "Origin: %s", (slider) -> {
        }, this::getValidOriginNames));
    }

    public Origin getSelectedOrigin() {
        return MythriaRegistries.ORIGINS.getValue(new ResourceLocation(originSelectorButton.getSelectedName()));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        Minecraft.getInstance().textureManager.bindTexture(TEXT_BOX);
        blit(matrixStack, left + 8, top + 7, 0, 0, 157, 128);
        ITextComponent description = getSelectedOrigin().getDescription();
        int i = 0;
        for (IReorderingProcessor processor : font.trimStringToWidth(description, 154)) {
            font.func_238422_b_(matrixStack, processor, left + 11, top + 10 + i * 9, 0xFFFFFFFF);
            i++;
        }
    }

    private List<String> getValidOriginNames() {
        List<String> list = new ArrayList<>();
        for (Origin gift : parent.profileLooksTab.getSelectedGeneticType().getAllowedOrigins()) {
            list.add(gift.getRegistryName().toString());
        }
        return list;
    }
}
