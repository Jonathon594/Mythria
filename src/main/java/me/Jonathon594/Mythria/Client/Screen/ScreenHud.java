package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public class ScreenHud extends IngameGui {
    private final ResourceLocation BARS = new ResourceLocation(
            Mythria.MODID, "textures/gui/bars.png");
    private final ResourceLocation ICONS = new MythriaResourceLocation("textures/gui/icons.png");
    private final ResourceLocation WIDGETS_TEX_PATH = new MythriaResourceLocation("textures/gui/widgets.png");

    public double weightValue = 0.0;
    public double staminaValue = 0.0;
    public double thirstValue = 0.0;
    public double hungerValue = 0.0;
    public double temperatureValue = 0.0;
    public double torporValue = 0.0;
    public double fatigueValue = 0.0;
    public double levelValue = 0.0;
    private double manaValue = 0.0;
    private double pleaseValue = 0.0;
    private Double bloodValue = 0.0;

    public ScreenHud(Minecraft mc) {
        super(mc);
    }

    @Override
    public void renderIngameGui(MatrixStack matrixStack, float partialTicks) {
        final int width = mc.getMainWindow().getScaledWidth();
        final int height = mc.getMainWindow().getScaledHeight();

//        if (TorpidityManager.isKnockedOut()) {
//            renderKnockedOutOverlay(scaledRes);
//        }

        mc.getTextureManager().bindTexture(BARS);

        final short barWidth = 182;

        ClientPlayerEntity player = mc.player;
        final Profile profile = ProfileProvider.getProfile(player);
        if (profile == null) return;
        if (player.isCreative() || player.isSpectator()) return;

        hungerValue = profile.getAverageNutrition();
        thirstValue = profile.getConsumable(Consumable.THIRST);
        fatigueValue = (1 - profile.getConsumable(Consumable.FATIGUE)) * 20;
        double maxStamina = profile.getStat(StatType.MAX_STAMINA);
        if (maxStamina > 0) staminaValue = profile.getConsumable(Consumable.STAMINA) / maxStamina * 20;
        double maxWeight = profile.getStat(StatType.MAX_WEIGHT);
        if (maxWeight > 0) weightValue = 20 - profile.getConsumable(Consumable.WEIGHT) / maxWeight * 20;
        temperatureValue = profile.getConsumable(Consumable.TEMPERATURE);
        torporValue = 20 - profile.getConsumable(Consumable.TORPOR);

        levelValue = profile.getPlayerLevelProgressBuffer();

        double maxMana = profile.getStat(StatType.MAX_MANA);
        if (maxMana > 0) manaValue = profile.getConsumable(Consumable.MANA) / maxMana * 20;
        pleaseValue = profile.getConsumable(Consumable.PLEASURE);

        bloodValue = profile.getConsumable(Consumable.BLOOD);


        mc.getTextureManager().bindTexture(BARS);
        drawBar(matrixStack, width / 2 - barWidth / 2, height - 32 + 3, (int) (levelValue * (barWidth + 1)), barWidth, true); // LEVEL

        drawStats(matrixStack);
    }

    private void drawBar(MatrixStack matrixStack, final int left, final int top, final int filled, final int barWidth, final boolean back) {
        blit(matrixStack, left, top, 0, 80, barWidth, 5);
        if (filled > 0)
            blit(matrixStack, left, top, 0, 85, MathHelper.clamp(filled, 0, barWidth), 5);
    }

    private void drawStats(MatrixStack matrixStack) {
        final int width = mc.getMainWindow().getScaledWidth();
        final int height = mc.getMainWindow().getScaledHeight();
        mc.getTextureManager().bindTexture(ICONS);

        if (mc.getRenderViewEntity() instanceof PlayerEntity) {
            RenderSystem.enableBlend();

            int yOff = -10;
            int y = height - 39 - 20;
            int x = width / 2 - 91 - 82 - 9;

            int blood = (int) Math.round(bloodValue);
            drawStatBar(matrixStack, x, y + yOff, blood, 0, 9, 18, 72, true, 20, true, true);
            yOff += 10;

            int torpor = (int) Math.round(torporValue);
            drawStatBar(matrixStack, x, y + yOff, torpor, 0, 9, 18, 18, true, 20);
            yOff += 10;

            int mana = (int) Math.round(manaValue);
            drawStatBar(matrixStack, x, y + yOff, mana, 0, 9, 18, 54, true, 20);
            yOff += 10;

            int stamina = (int) Math.round(staminaValue);
            drawStatBar(matrixStack, x, y + yOff, stamina, 0, 9, 18, 9, true, (int) Math.floor(fatigueValue));
            yOff += 10;

            x = width / 2 + 91 + 9;
            y = height - 39 - 20;
            yOff = -10;

            int thirst = (int) Math.round(thirstValue);
            drawStatBar(matrixStack, x, y + yOff, thirst, 0, 9, 18, 45, true, 20, true, true);
            yOff += 10;

            int hunger = (int) Math.round(hungerValue);
            drawStatBar(matrixStack, x, y + yOff, hunger, 0, 9, 18, 36, true, 20, true, true);
            yOff += 10;

            int temp = (int) Math.round(temperatureValue);
            drawStatBar(matrixStack, x, y + yOff, temp, 0, 9, 18, 27, true, 20, true, false);
            yOff += 10;

            int overWeight = (int) Math.round(20 - Math.abs(Math.min(weightValue, 0) / 3));
            drawStatBar(matrixStack, x, y + yOff, overWeight, 0, 27, 36, 0, true, 20, true, false);
            int weight = (int) Math.round(weightValue);
            drawStatBar(matrixStack, x, y + yOff, weight, 0, 9, 18, 0, false, 20, true, false);

//            int please = (int) Math.round(pleaseValue);
//            drawStatBar(matrixStack, x, y + yOff, please, 0, 9, 18, 63, false, 20, true, false);
//            yOff += 10;
        }
    }

    private void drawStatBar(MatrixStack matrixStack, int x, int y, int weight, int txBack, int txFront, int txFrontHalf, int ty, boolean back, int backValue) {
        drawStatBar(matrixStack, x, y, weight, txBack, txFront, txFrontHalf, ty, back, backValue, false, false);
    }

    private void drawStatBar(MatrixStack matrixStack, int x, int y, int weight, int txBack, int txFront, int txFrontHalf, int ty, boolean renderBackground, int backValue, boolean reverse, boolean showWobble) {
        RenderSystem.enableBlend();

        for (int i = 0; i < 10; i++) {
            int idx = i * 2 + 1;
            int offset = reverse ? 72 - (i * 8) : i * 8;

            if (renderBackground) {
                if (idx <= backValue) blit(matrixStack, x + offset, y, txBack, ty, 9, 9);
            }

            if (idx < weight) {
                blit(matrixStack, x + offset, y, txFront, ty, 9, 9);
            }

            if (idx == weight) {
                blit(matrixStack, x + offset, y, txFrontHalf, ty, 9, 9);
            }
        }

        RenderSystem.disableBlend();
    }
}
