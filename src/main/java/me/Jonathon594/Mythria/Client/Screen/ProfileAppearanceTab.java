package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.ISkinPartGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Skin.SkinParts;
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
                GeneticType genetic = MythriaRegistries.GENETICS.getValue(new ResourceLocation(message));
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
                SkinParts.getSkinPartNamesFor(getSelectedGeneticType().getAllowedHairs())));
        eyes = addWidget(new GuiButtonSkinPartSelector(xPos, yPos += yStep, width, height,
                "Eyes: %s", this::onSkinPartChanged, () ->
                SkinParts.getSkinPartNamesFor(getSelectedGeneticType().getAllowedEyes())));
        skin = addWidget(new GuiButtonSkinPartSelector(xPos, yPos += yStep, width, height,
                "Skin: %s", this::onSkinPartChanged, () ->
                SkinParts.getSkinPartNamesFor(getSelectedGeneticType().getAllowedSkins())));
        clothes = addWidget(new GuiButtonSkinPartSelector(xPos, yPos += yStep, width, height,
                "Clothes: %s", this::onSkinPartChanged, () ->
                SkinParts.getSkinPartNamesFor(getSelectedGeneticType().getAllowedClothes())));
        unique = addWidget(new SpecialSkinPartSelector(xPos, yPos + yStep, width, height, this::onSkinPartChanged, () -> {
            SkinPart.Type type = getSelectedGeneticType().getSpecialSkinPartType();
            if (type == null) return ImmutableList.of();
            return SkinParts.getSkinPartNamesFor(SkinParts.getSkinPartsFor(type));
        }));

        updateProfileSkin();
    }

    public SkinPart getSelectedClothing() {
        return MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(clothes.getSelectedName()));
    }

    public SkinPart getSelectedEyes() {
        return MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(eyes.getSelectedName()));
    }

    public Gender getSelectedGender() {
        return Gender.valueOf(gender.selectedName);
    }

    public GeneticType getSelectedGeneticType() {
        return MythriaRegistries.GENETICS.getValue(new ResourceLocation(race.getSelectedName()));
    }

    public SkinPart getSelectedHair() {
        return MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(hair.getSelectedName()));
    }

    public SkinPart getSelectedSkin() {
        return MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(skin.getSelectedName()));
    }

    public SkinPart getSelectedUnique() {
        return MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(unique.getSelectedName()));
    }

    private List<String> getRaceNames() {
        List<String> races = Lists.newArrayList();
        MythriaRegistries.GENETICS.getValues().forEach((genetic -> races.add(genetic.getRegistryName().toString())));
        return races;
    }

    private List<String> getValidGenders() {
        List<String> genders = Lists.newArrayList();
        double genderBias = getSelectedGeneticType().getDefaultInstance().getGenderBias();
        if (genderBias != 1) genders.add(Gender.MALE.name());
        if (genderBias != 0) genders.add(Gender.FEMALE.name());
        return genders;
    }

    private void onGenderClicked(Button button) {
        updateSkinPartIndices();
    }

    private void onRaceClicked(Button button) {
        gender.updateIndex();
        updateSkinPartIndices();
        parent.profileGiftTab.giftSelectorButton.updateIndex();
    }

    private void onSkinPartChanged(Button button) {
        updateProfileSkin();
    }

    private void updateProfileSkin() {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        Profile profile = ProfileProvider.getProfile(playerEntity);
        profile.setGenetic(getSelectedGeneticType().createGenetic());
        profile.setGender(getSelectedGender());
        profile.getGenetic().getHair().setSkinPart(getSelectedHair());
        profile.getGenetic().getEyes().setSkinPart(getSelectedEyes());
        profile.getGenetic().getSkin().setSkinPart(getSelectedSkin());
        profile.setClothing(getSelectedClothing());

        SkinPart.Type specialSkinPartType = getSelectedGeneticType().getSpecialSkinPartType();
        profile.getGenetic().getExtraGenes().forEach(gene -> {
            if (gene instanceof ISkinPartGene) {
                ISkinPartGene skinPartGene = (ISkinPartGene) gene;
                if (skinPartGene.getSkinPart().getType().equals(specialSkinPartType)) {
                    skinPartGene.setSkinPart(getSelectedUnique());
                }
            }
        });

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
