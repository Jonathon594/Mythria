package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Client.Enum.AbilityBookCategories;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ToggleWidget;

import java.util.Collection;

public class AbilityTabToggleWidget extends ToggleWidget {
    private final AbilityBookCategories category;

    public AbilityTabToggleWidget(AbilityBookCategories category) {
        super(0, 0, 35, 27, false);
        this.category = category;
        this.initTextureValues(153, 2, 35, 0, AbilityBookGui.ABILITY_BOOK);
    }

    public boolean checkVisibility() {
        Collection<Ability> list = category.getAbilities();
        this.visible = !list.isEmpty();
        return this.visible;
    }

    public AbilityBookCategories getCategory() {
        return this.category;
    }

    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;
        if (this.stateTriggered) {
            i += this.xDiffTex;
        }

        if (this.isHovered()) {
            j += this.yDiffTex;
        }

        int k = this.x;
        if (this.stateTriggered) {
            k -= 2;
        }

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(matrixStack, k, this.y, i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
//        this.renderIcon(minecraft.getItemRenderer());
//        if (this.animationTime > 0.0F) {
//            RenderSystem.popMatrix();
//            this.animationTime -= partialTicks;
//        }
    }
}
