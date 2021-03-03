package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.DataTypes.Time.Month;
import me.Jonathon594.Mythria.Entity.NPCEntity;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Genetic.Genetics;
import me.Jonathon594.Mythria.Managers.SkinPartManager;
import me.Jonathon594.Mythria.Managers.TimeManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Packet.CPacketProfileCreation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.util.ArrayList;
import java.util.List;

public class ScreenProfileCreation extends AbstractMythriaScreen {
    private NPCEntity npc;
    private TextFieldWidget txtFirstName;
    private TextFieldWidget txtMiddleName;
    private TextFieldWidget txtLastName;
    private GuiButtonSelector btsRace;
    private Slider sldMonth;
    private Slider sldDay;
    private Slider sldAge;
    private GuiButtonSelector btsGender;
    private Button btnCreate;
    private GuiButtonSkinPartSelector btsSkin;
    private GuiButtonSkinPartSelector btsHair;
    private GuiButtonSkinPartSelector btsEyes;
    private GuiButtonSkinPartSelector btsClothes;
    private GuiButtonSkinPartSelector btsSpecial;

    private final Button.IPressable buttonHandler = new Button.IPressable() {
        @Override
        public void onPress(Button button) {
            actionPerformed(button);

            if (button instanceof GuiButtonSelector) {
                resetSkinOptions();
                setSkinData();
                GuiButtonSelector guiButtonSelector = (GuiButtonSelector) button;
                guiButtonSelector.updateText();
                Genetic selectedGenetic = getGenetic();
                if (selectedGenetic != null) {
                    sldAge.maxValue = selectedGenetic.getLifeExpectancy() == 0 ? 100 : selectedGenetic.getLifeExpectancy();
                    sldAge.updateSlider();
                }
            }
        }
    };

    public ScreenProfileCreation() {
        super(new StringTextComponent("Profile Creation"));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        drawString(matrixStack, font, "First Name:", txtFirstName.x - 65, txtFirstName.y + 6, 0xFFFFFFFF);
        drawString(matrixStack, font, "Middle Name:", txtMiddleName.x - 65, txtMiddleName.y + 6, 0xFFFFFFFF);
        drawString(matrixStack, font, "Last Name:", txtLastName.x - 65, txtLastName.y + 6, 0xFFFFFFFF);

        setSkinData();
        ScreenUtils.drawEntityOnScreen(width / 2f + 110, height / 2f - 50, 50, (float) (width / 2 + 110) - mouseX,
                (float) (height / 2 - 100) - mouseY, npc);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void setSkinData() {
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(npc);
        mythriaPlayer.setSkinPart(SkinPart.Type.SKIN, getSkin().getResourceLocation().toString());
        mythriaPlayer.setSkinPart(SkinPart.Type.HAIR, getHair().getResourceLocation().toString());
        mythriaPlayer.setSkinPart(SkinPart.Type.EYES, getEyes().getResourceLocation().toString());
        mythriaPlayer.setSkinPart(SkinPart.Type.CLOTHING, getClothes().getResourceLocation().toString());
        SkinPart.Type specialType = getGenetic().getSpecialSkinPartType();
        mythriaPlayer.setSkinPart(SkinPart.Type.WINGS, specialType != null && specialType.equals(SkinPart.Type.WINGS) ? getSpecial().getResourceLocation().toString() : "");
        mythriaPlayer.setSkinPart(SkinPart.Type.DRYAD_VINES, specialType != null && specialType.equals(SkinPart.Type.DRYAD_VINES) ? getSpecial().getResourceLocation().toString() : "");
        mythriaPlayer.setSkinPart(SkinPart.Type.SKAEREN_SCALES, specialType != null && specialType.equals(SkinPart.Type.SKAEREN_SCALES) ? getSpecial().getResourceLocation().toString() : "");
    }

    protected SkinPart getClothes() {
        return SkinPartManager.getSkinPart(new ResourceLocation(btsClothes.getSelectedName()));
    }

    protected SkinPart getEyes() {
        return SkinPartManager.getSkinPart(new ResourceLocation(btsEyes.getSelectedName()));
    }

    protected SkinPart getHair() {
        return SkinPartManager.getSkinPart(new ResourceLocation(btsHair.getSelectedName()));
    }

    protected SkinPart getSkin() {
        return SkinPartManager.getSkinPart(new ResourceLocation(btsSkin.getSelectedName()));
    }

    protected SkinPart getSpecial() {
        return SkinPartManager.getSkinPart(new ResourceLocation(btsSpecial.getSelectedName()));
    }

    private String getRaceName() {
        return getGenetic().getRegistryName().toString();
    }

    @Override
    public void init() {
        buttons.clear();
        Minecraft mc = Minecraft.getInstance();
        npc = new NPCEntity(mc.player.world);
        int yPosition = height / 2 + (-5 * 25);
        int xPosition = width / 2 - 210;

        txtFirstName = new TextFieldWidget(font, xPosition, yPosition += 25, 200, 20, new StringTextComponent("First"));
        txtFirstName.setMaxStringLength(14);

        txtMiddleName = new TextFieldWidget(font, xPosition, yPosition += 25, 200, 20, new StringTextComponent("Middle"));
        txtMiddleName.setMaxStringLength(18);

        txtLastName = new TextFieldWidget(font, xPosition, yPosition += 25, 200, 20, new StringTextComponent("Last"));
        txtLastName.setMaxStringLength(14);

        addButton(txtFirstName);
        addButton(txtMiddleName);
        addButton(txtLastName);

        sldMonth = new Slider(xPosition, yPosition += 50, 200, 20, new StringTextComponent("Month "),
                new StringTextComponent(""), 0, 11, 0,
                false, false, buttonHandler, this::monthValueChanged);

        sldDay = new Slider(xPosition, yPosition += 25, 200, 20, new StringTextComponent("Day: "),
                new StringTextComponent(""), 1, 31, 1, false,
                true, buttonHandler);

        int lifeExpectancy = getGenetic().getLifeExpectancy() == 0 ? 486 : getGenetic().getLifeExpectancy();
        sldAge = new Slider(xPosition, yPosition += 25, 200, 20, new StringTextComponent("Age: "),
                new StringTextComponent(""), 5, lifeExpectancy, 16, false,
                true, buttonHandler);

        btsGender = new GuiButtonSelector(xPosition, yPosition += 25, 200, 20, "Gender: %s", buttonHandler, () -> {
            ArrayList<String> genders = new ArrayList<>();
            if (getGenetic().getGenderBias() != 1) genders.add("Male");
            if (getGenetic().getGenderBias() != 0) genders.add("Female");
            return genders;
        });

        sldMonth.setWidth(200);
        sldDay.setWidth(200);
        sldAge.setWidth(200);

        addButton(sldMonth);
        addButton(sldDay);
        addButton(btsGender);
        addButton(sldAge);

        addButton(btnCreate = new Button(width / 2 - 100, yPosition += 50, 200, 20, new StringTextComponent("Create Profile"), buttonHandler));

        yPosition = height / 2 - 50;
        xPosition = width / 2 + 10;

        btsRace = new GeneticSelector(xPosition, yPosition += 25, buttonHandler, this::getGeneticNames);

        btsSkin = new GuiButtonSkinPartSelector(xPosition, yPosition += 25, 200, 20, "Skin: %s", buttonHandler, () -> SkinPartManager.getSkinPartNamesFor(SkinPart.Type.SKIN, getSelectedGender(), getGenetic()));
        btsHair = new GuiButtonSkinPartSelector(xPosition, yPosition += 25, 200, 20, "Hair: %s", buttonHandler, () -> SkinPartManager.getSkinPartNamesFor(SkinPart.Type.HAIR, getSelectedGender(), getGenetic()));
        btsEyes = new GuiButtonSkinPartSelector(xPosition, yPosition += 25, 200, 20, "Eyes: %s", buttonHandler, () -> SkinPartManager.getSkinPartNamesFor(SkinPart.Type.EYES, getSelectedGender(), getGenetic()));
        btsClothes = new GuiButtonSkinPartSelector(xPosition, yPosition += 25, 200, 20, "Clothes: %s", buttonHandler, () -> SkinPartManager.getSkinPartNamesFor(SkinPart.Type.CLOTHING, getSelectedGender(), getGenetic()));

        btsSpecial = new SpecialSkinPartSelector(width / 2 - 100, yPosition, buttonHandler, () -> {
            if (getGenetic() == null || getGenetic().getSpecialSkinPartType() == null)
                return ImmutableList.of();
            return SkinPartManager.getSkinPartNamesFor(getGenetic().getSpecialSkinPartType(), getSelectedGender(), getGenetic());
        });

        addButton(btsRace);
        addButton(btsSkin);
        addButton(btsHair);
        addButton(btsEyes);
        addButton(btsClothes);
        addButton(btsSpecial);

        sldMonth.updateSlider();
    }

    protected List<String> getGeneticNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Genetic genetic : MythriaRegistries.GENETICS) {
            names.add(genetic.getRegistryName().toString());
        }
        return names;
    }

    @Override
    public void tick() {
        super.tick();
        txtFirstName.tick();
        txtMiddleName.tick();
        txtLastName.tick();

        npc.ticksExisted++;
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private void monthValueChanged(Slider slider) {
        Month month = TimeManager.getMonths().get(slider.getValueInt());
        slider.setMessage(new StringTextComponent("Month: " + month.getName()));
        sldDay.maxValue = month.getLength();
        sldDay.setValue(0);
        sldDay.updateSlider();
    }

    protected void actionPerformed(final Button button) {
        if (button == btnCreate) {
            final String fName = MythriaUtil.capitalize(txtFirstName.getText());
            final String mName = MythriaUtil.capitalize(txtMiddleName.getText());
            final String lName = MythriaUtil.capitalize(txtLastName.getText());

            final int month = Math.round(sldMonth.getValueInt());
            int day = Math.round(sldDay.getValueInt());
            final int gender = getSelectedGender();
            final int age = Math.round(sldAge.getValueInt());

            Minecraft mc = Minecraft.getInstance();
            if (fName.length() < 3 || mName.length() < 3 || lName.length() < 3) {
                mc.player.sendMessage(new StringTextComponent(MythriaConst.NAME_SHORT), Util.DUMMY_UUID);
                return;
            }
            if (fName.contains(" ") || lName.contains(" ")) {
                mc.player.sendMessage(new StringTextComponent(MythriaConst.NAMES_NO_SPACES), Util.DUMMY_UUID);
                return;
            }
            int nameLength = fName.length() + mName.length() + lName.length();
            if (nameLength > 26) {
                mc.player.sendMessage(new StringTextComponent(String.format(MythriaConst.NAME_TOO_LONG, nameLength)), Util.DUMMY_UUID);
                return;
            }

            final Month m = TimeManager.getMonths().get(month);
            if (day > m.getLength())
                day = m.getLength();

            final Date d = MythriaUtil.getDateFromAgeMonthDay(age, month, day);

            final Profile p = new Profile();
            p.setFirstName(fName);
            p.setMiddleName(mName);
            p.setLastName(lName);
            p.setBirthDay(d);
            p.setGender(gender);
            p.setSkinData(SkinPart.Type.SKIN, getSkin());
            p.setSkinData(SkinPart.Type.HAIR, getHair());
            p.setSkinData(SkinPart.Type.EYES, getEyes());
            p.setSkinData(SkinPart.Type.CLOTHING, getClothes());
            SkinPart.Type specialSkinPartType = getGenetic().getSpecialSkinPartType();
            if (specialSkinPartType != null) p.setSkinData(specialSkinPartType, getSpecial());
            p.setGenetic(getGenetic());
            mc.displayGuiScreen(null);

            CompoundNBT tag = new CompoundNBT();
            tag.put("Profile", p.toNBT());
            MythriaPacketHandler.sendToServer(new CPacketProfileCreation(tag));
        }
    }

    private Genetic getGenetic() {
        if (btsRace == null || btsRace.selectedName.isEmpty()) return Genetics.HUMAN;
        return MythriaRegistries.GENETICS.getValue(new ResourceLocation(btsRace.selectedName));
    }

    private int getSelectedGender() {
        return btsGender.getCurrentOption().equalsIgnoreCase("male") ? 0 : 1;
    }

    private void resetSkinOptions() {
        btsGender.updateIndex();
        btsHair.updateIndex();
        btsSkin.updateIndex();
        btsClothes.updateIndex();
        btsEyes.updateIndex();
        btsSpecial.updateIndex();
    }

}
