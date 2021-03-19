package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.DataTypes.Date;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketSpendAttribute;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class ScreenProfile extends Screen {
    private static final ResourceLocation BACKGROUND = new MythriaResourceLocation("textures/gui/profile.png");
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/advancements/widgets.png");
    protected final int xSize = 256;
    protected final int ySize = 166;

    public ScreenProfile() {
        super(new StringTextComponent("Profile"));
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        int left = width / 2 - xSize / 2;
        int top = height / 2 - ySize / 2;

        for (AttributeDisplay attributeDisplay : AttributeDisplay.values()) {
            int x = left + attributeDisplay.getOffsetX();
            int y = top + attributeDisplay.getOffsetY();

            if (isAttributeHovered(mouseX, mouseY, x, y)) {
                MythriaPacketHandler.sendToServer(new CPacketSpendAttribute(attributeDisplay.getAttribute()));
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int left = width / 2 - xSize / 2;
        int top = height / 2 - ySize / 2;
        this.blit(matrixStack, left, top, 0, 0, xSize, ySize);

        renderText(matrixStack, left, top);

        ScreenUtils.drawEntityOnScreen(left + 36.5f, top + 75, 30, (float) (width / 2 - 110) - mouseX,
                (float) (height / 2 - 100) - mouseY, minecraft.player);
        renderAttributes(matrixStack, left, top, mouseX, mouseY);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        super.init();
    }

    private boolean isAttributeHovered(final double mouseX, final double mouseY, final int x, final int y) {
        return mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16;
    }

    private void renderAttributes(MatrixStack matrixStack, final int left, final int top, int mouseX, int mouseY) {
        Profile profile = ProfileProvider.getProfile(minecraft.player);

        for (AttributeDisplay attributeDisplay : AttributeDisplay.values()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            DisplayInfo displayInfo = attributeDisplay.getDisplayInfo();
            RenderHelper.enableStandardItemLighting();
            int x = left + attributeDisplay.getOffsetX();
            int y = top + attributeDisplay.getOffsetY();
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(null, displayInfo.getIcon(), x, y);
        }

        for (AttributeDisplay attributeDisplay : AttributeDisplay.values()) {
            int x = left + attributeDisplay.getOffsetX();
            int y = top + attributeDisplay.getOffsetY();

            if (isAttributeHovered(mouseX, mouseY, x, y)) {
                ArrayList<ITextComponent> lines = new ArrayList<>();
                lines.add(new StringTextComponent(TextFormatting.GREEN + attributeDisplay.getDisplayInfo().getTitle().getString()));
                lines.add(new StringTextComponent(ColorConst.CONT_COLOR + "Current Value: " + profile.getAttributeLevel(attributeDisplay.getAttribute()) + " / 25"));
                lines.add(new StringTextComponent(ColorConst.HIGH_COLOR + "Spendable Points: " + profile.getSpendableAttributePoints()));
                lines.add(new StringTextComponent(ColorConst.MAIN_COLOR + attributeDisplay.getDisplayInfo().getDescription().getString()));

                renderWrappedToolTip(matrixStack, lines, mouseX, mouseY, font);
            }
        }

        RenderHelper.disableStandardItemLighting();
    }

    private void renderText(MatrixStack matrixStack, final int left, final int top) {
        ClientPlayerEntity player = minecraft.player;
        final Profile p = ProfileProvider.getProfile(player);
        Date birthDay = p.getBirthDay();
        int age = birthDay.getYearsFromCurrent();

        String nameString = ColorConst.SUCCESS_COLOR + "" + TextFormatting.BOLD + p.getFullName();
        drawCenteredString(matrixStack, font, nameString, left + 160, top + 10, 0xFFFFFFFF);

        String ageString = ColorConst.MAIN_COLOR + "" + age + (age == 1 ? " year " : " years ") + "old";
        drawString(matrixStack, font, ageString, left + 75, top + 29, 0xFFFFFFFF);

        String birthDayString = ColorConst.MAIN_COLOR + "Born: " + birthDay.getDateString();
        drawString(matrixStack, font, birthDayString, left + 75, top + 41, 0xFFFFFFFF);

        drawString(matrixStack, font, ColorConst.HIGH_COLOR + "Stats:", left + 75, top + 61, 0xFFFFFFFF);

        ArrayList<String> lines = new ArrayList<>();
        lines.add(ColorConst.MAIN_COLOR + "Level: " + ColorConst.SUCCESS_COLOR + p.getPlayerLevel());
        lines.add(ColorConst.MAIN_COLOR + "Health: " + ColorConst.SUCCESS_COLOR + MythriaUtil.round(p.getStat(StatType.MAX_HEALTH) + 20, 1));
        lines.add(ColorConst.MAIN_COLOR + "Weight: " + ColorConst.SUCCESS_COLOR + MythriaUtil.round(p.getStat(StatType.MAX_WEIGHT), 1) + "kg");
        lines.add(ColorConst.MAIN_COLOR + "Stamina: " + ColorConst.SUCCESS_COLOR + MythriaUtil.round(p.getStat(StatType.MAX_STAMINA), 1));
        double speed = MythriaUtil.round(((43.178 * (player.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() + p.getStat(StatType.MAX_SPEED)))) - 0.02141f, 2);
        lines.add(ColorConst.MAIN_COLOR + "Speed: " + ColorConst.SUCCESS_COLOR + speed + " m/s");
        int i = 0;
        for (String s : lines) {
            drawString(matrixStack, font, s, left + 75, top + 73 + i * 12, 0xFFFFFFFF);
            i++;
        }

        drawString(matrixStack, font, ColorConst.HIGH_COLOR + "Nutrition:", left + 166, top + 61, 0xFFFFFFFF);

        lines.clear();
        ArrayList<Consumable.Nutrition> nutritions = new ArrayList<>(Arrays.asList(Consumable.Nutrition.values()));
        boolean showNutrition = p.hasFlag(AttributeFlag.SEE_NUTRITION) || player.isCreative();
        if (!showNutrition) Collections.shuffle(nutritions, new Random(p.getProfileUUID().hashCode()));
        for (Consumable.Nutrition nutrition : nutritions) {
            String nutritionString = "";
            int percent = (int) (p.getNutrition(nutrition) / 20.0 * 100);
            TextFormatting valueColor = ColorConst.SUCCESS_COLOR;
            if (percent < 70) valueColor = ColorConst.MAIN_COLOR;
            if (percent < 30) valueColor = ColorConst.CONT_COLOR;
            if (showNutrition) {
                nutritionString = MythriaUtil.capitalize(nutrition.name()) + ": " + valueColor + percent + "%";
            } else {
                nutritionString = valueColor + "" + TextFormatting.OBFUSCATED + "?!#$%";
            }

            lines.add(ColorConst.MAIN_COLOR + "" + nutritionString);
        }
        i = 0;
        for (String s : lines) {
            drawString(matrixStack, font, s, left + 166, top + 73 + i * 12, 0xFFFFFFFF);
            i++;
        }
    }

    private enum AttributeDisplay {
        VIGOR(new DisplayInfo(new ItemStack(Items.GOLDEN_APPLE, 1), new StringTextComponent("Vigor"),
                new StringTextComponent("Vigor shows how physically tough one is."), null, FrameType.TASK, false, false, false), Attribute.VIGOR, 8, 90),
        STRENGTH(new DisplayInfo(new ItemStack(MythriaItems.IRON_HAMMER, 1), new StringTextComponent("Strength"),
                new StringTextComponent("Strength indicates how much force the muscles can exert."), null, FrameType.TASK, false, false, false), Attribute.STRENGTH, 29, 90),
        VITALITY(new DisplayInfo(new ItemStack(Items.SHIELD, 1), new StringTextComponent("Vitality"),
                new StringTextComponent("Vitality is a measure of physical healthiness and resistance to disease."), null, FrameType.TASK, false, false, false), Attribute.VITALITY, 50, 90),

        INTELLIGENCE(new DisplayInfo(new ItemStack(Items.WRITABLE_BOOK, 1), new StringTextComponent("Intelligence"),
                new StringTextComponent("Intelligence dictates how quickly one can learn new things."), null, FrameType.TASK, false, false, false), Attribute.INTELLIGENCE, 8, 112),
        FAITH(new DisplayInfo(new ItemStack(Items.NETHER_STAR, 1), new StringTextComponent("Faith"),
                new StringTextComponent("Faith measures the sensitivity and connection to the divine."), null, FrameType.TASK, false, false, false), Attribute.FAITH, 29, 112),
        WILLPOWER(new DisplayInfo(new ItemStack(Items.ENDER_PEARL, 1), new StringTextComponent("Willpower"),
                new StringTextComponent("Willpower is a measure of maximum magical energy."), null, FrameType.TASK, false, false, false), Attribute.WILLPOWER, 50, 112),

        DEXTERITY(new DisplayInfo(new ItemStack(Items.BOW, 1), new StringTextComponent("Dexterity"),
                new StringTextComponent("Dexterity is one's ability to control precise movements and actions."), null, FrameType.TASK, false, false, false), Attribute.DEXTERITY, 8, 134),
        AGILITY(new DisplayInfo(new ItemStack(Items.LEATHER_BOOTS, 1), new StringTextComponent("Agility"),
                new StringTextComponent("Agility indicates flexibility and speed."), null, FrameType.TASK, false, false, false), Attribute.AGILITY, 29, 134),
        ENDURANCE(new DisplayInfo(new ItemStack(Items.IRON_CHESTPLATE, 1), new StringTextComponent("Endurance"),
                new StringTextComponent("Endurance affects how long one can maintain activity."), null, FrameType.TASK, false, false, false), Attribute.ENDURANCE, 50, 134);

        private final DisplayInfo displayInfo;
        private final Attribute attribute;
        private final int offsetX;
        private final int offsetY;

        AttributeDisplay(final DisplayInfo displayInfo, final Attribute attribute, final int offsetX, final int offsetY) {
            this.displayInfo = displayInfo;
            this.attribute = attribute;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }

        public Attribute getAttribute() {
            return attribute;
        }

        public DisplayInfo getDisplayInfo() {
            return displayInfo;
        }

        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }
    }
}
