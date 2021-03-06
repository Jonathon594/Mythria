package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Genetic.Genetic;
import me.Jonathon594.Mythria.Managers.SkinPartManager;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class ProfileAppearanceTab extends ProfileCreationTab {
    public final GuiButtonSelector race;
    public final GuiButtonSelector gender;
    public final GuiButtonSkinPartSelector hair;
    public final GuiButtonSkinPartSelector eyes;
    public final GuiButtonSkinPartSelector skin;
    public final GuiButtonSkinPartSelector clothes;
    public final GuiButtonSkinPartSelector unique;

    public ProfileAppearanceTab(ScreenProfileCreation parent, FontRenderer font, int left, int top) {
        super(parent, new StringTextComponent("Looks"), font, left, top);

        int yPos = top + 7;
        final int xPos = left + 8;
        final int width = 157;
        final int height = 20;
        race = addWidget(new GuiButtonSelector(xPos, yPos, width, height,
                "Race: %s", this::onRaceClicked, () -> getRaceNames()) {
            @Override
            protected String modifyMessage(String message) {
                Genetic genetic = MythriaRegistries.GENETICS.getValue(new ResourceLocation(message));
                return genetic.getDisplayName();
            }
        });

        final int yStep = 22;
        gender = addWidget(new GuiButtonSelector(xPos, yPos += yStep, width, height,
                "Gender: %s", this::onGenderClicked, () -> getValidGenders()) {
            @Override
            protected String modifyMessage(String message) {
                return MythriaUtil.capitalize(message);
            }
        });

        hair = addWidget(new GuiButtonSkinPartSelector(xPos, yPos += yStep, width, height,
                "Hair: %s", this::onSkinPartChanged, () ->
                SkinPartManager.getSkinPartNamesFor(SkinPart.Type.HAIR, getSelectedGender(), getSelectedRace())));
        eyes = addWidget(new GuiButtonSkinPartSelector(xPos, yPos += yStep, width, height,
                "Eyes: %s", this::onSkinPartChanged, () ->
                SkinPartManager.getSkinPartNamesFor(SkinPart.Type.EYES, getSelectedGender(), getSelectedRace())));
        skin = addWidget(new GuiButtonSkinPartSelector(xPos, yPos += yStep, width, height,
                "Skin: %s", this::onSkinPartChanged, () ->
                SkinPartManager.getSkinPartNamesFor(SkinPart.Type.SKIN, getSelectedGender(), getSelectedRace())));
        clothes = addWidget(new GuiButtonSkinPartSelector(xPos, yPos += yStep, width, height,
                "Clothes: %s", this::onSkinPartChanged, () ->
                SkinPartManager.getSkinPartNamesFor(SkinPart.Type.CLOTHING, getSelectedGender(), getSelectedRace())));
        unique = addWidget(new SpecialSkinPartSelector(xPos, yPos + yStep, width, height, this::onSkinPartChanged, () -> {
            SkinPart.Type type = getSelectedRace().getSpecialSkinPartType();
            if (type == null) return ImmutableList.of();
            return SkinPartManager.getSkinPartNamesFor(type, getSelectedGender(), getSelectedRace());
        }));

        updateProfileSkin();
    }

    private int getSelectedGender() {
        return gender.selectedName == "male" ? 0 : 1;
    }

    private List<String> getValidGenders() {
        List<String> genders = Lists.newArrayList();
        if (getSelectedRace().getGenderBias() != 1) genders.add("male");
        if (getSelectedRace().getGenderBias() != 0) genders.add("female");
        return genders;
    }


    private Genetic getSelectedRace() {
        return MythriaRegistries.GENETICS.getValue(new ResourceLocation(race.getSelectedName()));
    }

    private List<String> getRaceNames() {
        List<String> races = Lists.newArrayList();
        MythriaRegistries.GENETICS.getValues().forEach((genetic -> races.add(genetic.getRegistryName().toString())));
        return races;
    }

    private void onRaceClicked(Button button) {
        gender.updateIndex();
        updateSkinPartIndices();
        int lifeExpectancy = getSelectedRace().getLifeExpectancy();
        parent.profileNamesTab.age.setMaxValue(lifeExpectancy <= 0 ? 500 : lifeExpectancy / 2);
    }

    private void onGenderClicked(Button button) {
        updateSkinPartIndices();
    }

    private void onSkinPartChanged(Button button) {
        updateProfileSkin();
    }

    private void updateProfileSkin() {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        Profile profile = ProfileProvider.getProfile(playerEntity);
        profile.setGenetic(getSelectedRace());
        profile.setGender(getSelectedGender());
        profile.setSkinData(SkinPart.Type.HAIR, MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(hair.getSelectedName())));
        profile.setSkinData(SkinPart.Type.EYES, MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(eyes.getSelectedName())));
        profile.setSkinData(SkinPart.Type.SKIN, MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(skin.getSelectedName())));
        profile.setSkinData(SkinPart.Type.CLOTHING, MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(clothes.getSelectedName())));

        SkinPart.Type specialSkinPartType = getSelectedRace().getSpecialSkinPartType();
        for (SkinPart.Type type : ImmutableList.of(SkinPart.Type.WINGS, SkinPart.Type.DRYAD_VINES)) {
            profile.setSkinData(type, type.equals(specialSkinPartType) ?
                    MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(unique.getSelectedName())) : null);
        }

        profile.copySkinToMythriaPlayer(MythriaPlayerProvider.getMythriaPlayer(playerEntity));
    }

    private void updateSkinPartIndices() {
        hair.updateIndex();
        eyes.updateIndex();
        skin.updateIndex();
        clothes.updateIndex();
        unique.updateIndex();
        updateProfileSkin();
    }
}
