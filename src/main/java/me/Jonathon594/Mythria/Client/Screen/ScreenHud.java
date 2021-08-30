package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Renderer.StatRenderer;
import me.Jonathon594.Mythria.Enum.CombatMode;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public class ScreenHud extends AbstractGui {
    public static final ScreenHud INSTANCE = new ScreenHud();
    private static final ResourceLocation MINECRAFT_ICONS = new ResourceLocation("textures/gui/icons.png");
    private static final StatRenderer BLOOD = new StatRenderer(0, 72, 9, 18, true, false, 0);
    private static final StatRenderer THIRST = new StatRenderer(0, 45, 9, 18, true, false, 1);
    private static final StatRenderer HUNGER = new StatRenderer(0, 36, 9, 18, true, false, 2);
    private static final StatRenderer TORPOR = new StatRenderer(0, 18, 9, 18, true, false, 4);
    private static final StatRenderer STAMINA = new StatRenderer(0, 9, 9, 18, true, false, 5);
    private static final StatRenderer MANA = new StatRenderer(0, 54, 9, 18, true, false, 6);
    private static final StatRenderer TEMPERATURE = new StatRenderer(0, 27, 9, 18, true, false, 8);
    private static final StatRenderer OVERWEIGHT = new StatRenderer(0, 0, 27, 36, false, false, 9);
    private static final StatRenderer WEIGHT = new StatRenderer(0, 0, 9, 18, true, false, 9);
    private final ResourceLocation ICONS = new MythriaResourceLocation("textures/gui/icons.png");
    private final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
    private final ResourceLocation ABILITY_BOOK = new MythriaResourceLocation("textures/items/magic/book_spell.png");
    private final ResourceLocation ATTACK_BARS = new MythriaResourceLocation("textures/gui/attack_bars.png");
    private final Minecraft mc;
    private double levelValue = 0.0;

    public ScreenHud() {
        this.mc = Minecraft.getInstance();
    }

    public void render(MatrixStack matrixStack, float partialTicks) {
        final int width = mc.getMainWindow().getScaledWidth();
        final int height = mc.getMainWindow().getScaledHeight();

//        if (TorpidityManager.isKnockedOut()) {
//            renderKnockedOutOverlay(scaledRes);
//        }

        mc.getTextureManager().bindTexture(MINECRAFT_ICONS);

        final short barWidth = 182;

        ClientPlayerEntity player = mc.player;
        final Profile profile = ProfileProvider.getProfile(player);
        if (profile == null) return;
        if (player.isCreative() || player.isSpectator()) return;
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);

        drawBar(matrixStack, width / 2 - barWidth / 2, height - 32 + 3, (int) (levelValue * (barWidth + 1)), barWidth, true); // LEVEL
        drawStatRenderers(matrixStack);
        renderAbilityWheel(matrixStack, width, height);

        if (mythriaPlayer.getCombatMode().equals(CombatMode.DUAL)) {
            //renderDualWieldingIndicator(matrixStack, width, height, player);
        }
    }

    public void tick() {
        ClientPlayerEntity player = mc.player;
        if (player == null) return;
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
        OVERWEIGHT.setValueFront((int) (Math.max(weightValue - 20, 0) / 3.0));

        TEMPERATURE.setValueFront((int) profile.getConsumable(Consumable.TEMPERATURE)).setValueBack(20);
        TORPOR.setValueFront((int) (20 - profile.getConsumable(Consumable.TORPOR))).setValueBack(20);

        levelValue = profile.getPlayerLevelProgressBuffer();

        double maxMana = profile.getStat(StatType.MAX_MANA);
        MANA.setValueFront((int) (maxMana > 0 ? profile.getConsumable(Consumable.MANA) / maxMana * 20 : 0)).setValueBack(20);

        BLOOD.setValueFront((int) profile.getConsumable(Consumable.BLOOD)).setValueBack(20);
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

    private ImmutableList<StatRenderer> getStatRenderers() {
        return ImmutableList.of(HUNGER, BLOOD, TORPOR, MANA, STAMINA, THIRST, TEMPERATURE, WEIGHT, OVERWEIGHT);
    }

    private void renderAbilityWheel(MatrixStack matrixStack, int width, int height) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderHelper.enableStandardItemLighting();
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(mc.player);

        String modeText;
        switch (mythriaPlayer.getControlMode()) {
            case NORMAL:
                switch (mythriaPlayer.getCombatMode()) {
                    case NORMAL:
                        modeText = "Normal";
                        mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.IRON_SWORD), 8, height - 24);
                        break;
                    case DUAL:
                        modeText = "Dual Wielding";
                        mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.IRON_SWORD), 4, height - 24);
                        mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.IRON_AXE), 14, height - 24);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + mythriaPlayer.getCombatMode());
                }
                break;
            case ABILITY:
                modeText = "Ability";
                mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.ENCHANTED_BOOK), 8, height - 24);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mythriaPlayer.getControlMode());
        }
        drawString(matrixStack, mc.fontRenderer, modeText, 32, height - 20, 0xffffff);
    }

    private void renderDualWieldingIndicator(MatrixStack matrixStack, int width, int height, ClientPlayerEntity player) {

    }
}
