package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.Managers.TimeManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketProfileCreation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.command.arguments.NBTTagArgument;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;

public class ProfileSummaryTab extends ProfileCreationTab {
    private final Button create;

    public ProfileSummaryTab(ScreenProfileCreation parent, FontRenderer font, int left, int top) {
        super(parent, new StringTextComponent("Finish"), font, left, top);
        create = addWidget(new Button(left + 8, top + 139, 157, 20,
                new StringTextComponent("Begin Adventure!"), this::onCreate));
    }

    private void onCreate(Button button) {
        if (parent.canCreate()) {
            Profile profile = ProfileProvider.getProfile(Minecraft.getInstance().player);
            ProfileNamesTab profileNamesTab = parent.profileNamesTab;
            profile.setFirstName(profileNamesTab.firstName.getText());
            profile.setMiddleName(profileNamesTab.middleName.getText());
            profile.setLastName(profileNamesTab.lastName.getText());
            Date date = MythriaUtil.getDateFromAgeMonthDay(profileNamesTab.age.getValueInt(),
                    profileNamesTab.month.getValueInt(),
                    profileNamesTab.day.getValueInt());
            profile.setBirthDay(date);
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("Profile", profile.toNBT());
            MythriaPacketHandler.sendToServer(
                    new CPacketProfileCreation(compoundNBT));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
