package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Renderer.StatRenderer;
import me.Jonathon594.Mythria.Enum.CombatMode;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.ControlMode;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public class ScreenHud extends AbstractGui {
    public static final ScreenHud INSTANCE = new ScreenHud();

    private final ResourceLocation ICONS = new MythriaResourceLocation("textures/gui/icons.png");
    private final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
    private final ResourceLocation ABILITY_BOOK = new MythriaResourceLocation("textures/items/magic/book_spell.png");
    private final ResourceLocation ATTACK_BARS = new MythriaResourceLocation("textures/gui/attack_bars.png");

    private static final StatRenderer BLOOD = new StatRenderer(0, 72, 9, 18, true, false, 0);
    private static final StatRenderer THIRST = new StatRenderer(0, 45, 9, 18, true, false, 1);
    private static final StatRenderer HUNGER = new StatRenderer(0, 36, 9, 18, true, false, 2);

    private static final StatRenderer TORPOR = new StatRenderer(0, 18, 9, 18, true, false, 4);
    private static final StatRenderer STAMINA = new StatRenderer(0, 9, 9, 18, true, false, 5);
    private static final StatRenderer MANA = new StatRenderer(0, 54, 9, 18, true, false, 6);

    private static final StatRenderer TEMPERATURE = new StatRenderer(0, 27, 9, 18, true, false, 8);
    private static final StatRenderer OVERWEIGHT = new StatRenderer(0, 0, 27, 36, false, false, 9);
    private static final StatRenderer WEIGHT = new StatRenderer(0, 0, 9, 18, true, false, 9);
    private double levelValue = 0.0;
    private final Minecraft mc;

    public ScreenHud() {
        this.mc = Minecraft.getInstance();
    }

    private ImmutableList<StatRenderer> getStatRenderers() {
        return ImmutableList.of(HUNGER, BLOOD, TORPOR, MANA, STAMINA, THIRST, TEMPERATURE, WEIGHT, OVERWEIGHT);
    }

    public void render(MatrixStack matrixStack, float partialTicks) {
        final int width = mc.getMainWindow().getScaledWidth();
        final int height = mc.getMainWindow().getScaledHeight();

//        if (TorpidityManager.isKnockedOut()) {
//            renderKnockedOutOverlay(scaledRes);
//        }

        mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));

        final short barWidth = 182;

        ClientPlayerEntity player = mc.player;
        final Profile profile = ProfileProvider.getProfile(player);
        if (profile == null) return;
        if (player.isCreative() || player.isSpectator()) return;

        drawBar(matrixStack, width / 2 - barWidth / 2, height - 32 + 3, (int) (levelValue * (barWidth + 1)), barWidth, true); // LEVEL
        drawStatRenderers(matrixStack);
        renderAbilityWheel(matrixStack, width, height);

        if(MythriaPlayerProvider.getMythriaPlayer(player).getCombatMode().equals(CombatMode.DUAL)) {
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            RenderSystem.enableBlend();
            RenderSystem.color3f(1.0f, 0f, 0f);
            RenderSystem.enableTexture();
            HandSide handside = player.getPrimaryHand().opposite();
            int i = width / 2;
            if (handside == HandSide.LEFT) {
                this.blit(matrixStack, i - 91 - 29, height - 23, 24, 22, 29, 24);
            } else {
                this.blit(matrixStack, i + 91, height - 23, 53, 22, 29, 24);
            }
        }
    }

    private void renderAbilityWheel(MatrixStack matrixStack, int width, int height) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderHelper.enableStandardItemLighting();
        boolean abilityMode = MythriaPlayerProvider.getMythriaPlayer(mc.player).getControlMode().equals(ControlMode.ABILITY);

        mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.ENCHANTED_BOOK), 8, height - 24);
        if (abilityMode) {
            int j = 5;
            for (int i = 0; i < j + 1; i++) {
                double a = (Math.PI / 2 / (double) j) * i - Math.PI / 2;
                int x = (int) (Math.cos(a) * 64D);
                int y = (int) (Math.sin(a) * 64D);

                mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.FIRE_CHARGE), 8 + x, height - 24 + y);
            }
            mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            blit(matrixStack, 64 + 5, height - 24 - 3, 24, 23, 22, 22);
            drawString(matrixStack, mc.fontRenderer, "Test Spell", 96, height - 20, 0xffffff);
        }
    }

    private void drawBar(MatrixStack matrixStack, final int left, final int top, final int filled, final int barWidth, final boolean back) {
        blit(matrixStack, left, top, 0, 64, barWidth, 5);
        if (filled > 0)
            blit(matrixStack, left, top, 0, 69, MathHelper.clamp(filled, 0, barWidth), 5);
    }

    private void drawStatRenderers(MatrixStack matrixStack) {
        final int width = mc.getMainWindow().getScaledWidth();
        final int height = mc.getMainWindow().getScaledHeight();
        mc.getTextureManager().bindTexture(ICONS);

        if (mc.getRenderViewEntity() instanceof PlayerEntity) {
            int x = width - 81 - 9;
            int y = height - 108 - 9;
            for (StatRenderer statRenderer : getStatRenderers()) {
                statRenderer.render(matrixStack, x, y + statRenderer.getSlotOrder() * 11);
            }
        }
    }

    public void tick() {
        ClientPlayerEntity player = mc.player;
        if(player == null) return;
        final Profile profile = ProfileProvider.getProfile(player);
        if (profile == null) return;
        if (player.isCreative() || player.isSpectator()) return;

        HUNGER.setValueFront((int) profile.getAverageNutrition()).setValueBack(20);
        THIRST.setValueFront((int) profile.getConsumable(Consumable.THIRST)).setValueBack(20);

        double maxStamina = profile.getStat(StatType.MAX_STAMINA);
        STAMINA.setValueFront((int) (maxStamina > 0 ? profile.getConsumable(Consumable.STAMINA) / maxStamina * 20 : 0))
                .setValueBack((int) ((1 - profile.getConsumable(Consumable.FATIGUE)) * 20.0));

        double maxWeight = profile.getStat(StatType.MAX_WEIGHT);
        int weightValue = (int) (maxWeight > 0 ? profile.getConsumable(Consumable.WEIGHT) / maxWeight * 20.0 : 0);
        WEIGHT.setValueFront(weightValue).setValueBack(20);
        OVERWEIGHT.setValueFront((int) (Math.max(weightValue-20, 0) / 3.0));

        TEMPERATURE.setValueFront((int) profile.getConsumable(Consumable.TEMPERATURE)).setValueBack(20);
        TORPOR.setValueFront((int) (20 - profile.getConsumable(Consumable.TORPOR))).setValueBack(20);

        levelValue = profile.getPlayerLevelProgressBuffer();

        double maxMana = profile.getStat(StatType.MAX_MANA);
        MANA.setValueFront((int) (maxMana > 0 ? profile.getConsumable(Consumable.MANA) / maxMana * 20 : 0)).setValueBack(20);

        BLOOD.setValueFront((int) profile.getConsumable(Consumable.BLOOD)).setValueBack(20);
    }
}
