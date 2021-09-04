package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class AbilityWidget extends Widget {
    private static final ResourceLocation RECIPE_BOOK = new MythriaResourceLocation("textures/gui/ability_book.png");
    private Ability ability;
    private float animationTime;

    public AbilityWidget() {
        super(0, 0, 25, 25, StringTextComponent.EMPTY);
        //this.animationTime = 15.0F;
    }

    public void func_203400_a(Ability p_203400_1_) {
        this.ability = p_203400_1_;
    }

    public Ability getAbility() {
        return ability;
    }

    public List<ITextComponent> getToolTipText(Screen p_191772_1_) {
        return ability.getHoveredToolTip();
    }

    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(RECIPE_BOOK);
        int i = 29;
        if (!ProfileProvider.getProfile(minecraft.player).getAbilities().contains(ability) || !ability.canBeBound()) { // isknown?
            i += 25;
        }

        int j = 206;

        boolean flag = this.animationTime > 0.0F;
        if (flag) {
            float f = 1.0F + 0.1F * (float) Math.sin((double) (this.animationTime / 15.0F * (float) Math.PI));
            matrixStack.push();
            matrixStack.translate((float) (this.x + 8), (float) (this.y + 12), 0.0F);
            matrixStack.scale(f, f, 1.0F);
            matrixStack.translate((float) (-(this.x + 8)), (float) (-(this.y + 12)), 0.0F);
            this.animationTime -= partialTicks;
        }

        this.blit(matrixStack, this.x, this.y, i, j, this.width, this.height);
        int k = 4;
        //minecraft.getItemRenderer().renderItemAndEffectIntoGUI(itemstack, this.x + k + 1, this.y + k + 1);
        --k;

        //minecraft.getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(itemstack, this.x + k, this.y + k);
        matrixStack.push();
        minecraft.getTextureManager().bindTexture(ability.getAbilityTexturePath());
        matrixStack.scale(1 / 16f, 1 / 16f, 1);
        this.blit(matrixStack, (this.x + k) * 16, (this.y + k) * 16, 0, 0, 256, 256);
        matrixStack.pop();

        if (flag) {
            matrixStack.pop();
        }
    }

    protected boolean isValidClickButton(int button) {
        return button == 0;
    }

    public int getWidth() {
        return 25;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
