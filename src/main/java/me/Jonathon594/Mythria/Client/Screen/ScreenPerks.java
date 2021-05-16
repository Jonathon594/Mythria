package me.Jonathon594.Mythria.Client.Screen;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.RootPerk;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Packet.CPacketBuyPerk;
import me.Jonathon594.Mythria.Perk.Perks;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScreenPerks extends AbstractTreeMenuScreen {
    public ScreenPerks() {
        super(new StringTextComponent("Perks"));
    }

    @Override
    public void refreshTabs() {
        Profile p = ProfileProvider.getProfile(minecraft.player);
        for (TreeMenuTabGui tab : tabs.values()) {
            for (Map.Entry<TreeMenuOption, TreeMenuEntryGui> entry : tab.getGuis().entrySet()) {
                TreeMenuOption treeMenuOption = entry.getKey();
                TreeMenuEntryGui treeMenuEntryGui = entry.getValue();
                Perk perk = MythriaRegistries.PERKS.getValue(treeMenuOption.getId());
                if (perk == null) continue;
                DisplayInfo displayInfo = getDisplayInfo(p, perk);
                if (perk instanceof RootPerk) tab.setDisplayData(displayInfo);
                treeMenuOption.setDisplay(displayInfo);

                treeMenuEntryGui.setDisplayInfo(displayInfo);

                if (p.hasPerk(perk)) {
                    treeMenuOption.setHighlight(true);
                    treeMenuEntryGui.setHighlight(true);
                }
            }
        }
    }

    @Override
    protected void onOptionClicked(TreeMenuOption clicked) {
        CompoundNBT compound = new CompoundNBT();
        compound.putString("Perk", clicked.getId().toString());
        MythriaPacketHandler.sendToServer(new CPacketBuyPerk(compound));
    }

    @Override
    public void populateTabs() throws Exception {
        int i = 0;
        Profile p = ProfileProvider.getProfile(minecraft.player);
        for (PerkType type : PerkType.values()) {
            List<TreeMenuOption> optionList = new ArrayList<>();
            TreeMenuOption root = null;
            List<Perk> validPerks = new ArrayList<>();
            for (Perk perk : Perks.getPerksByType(type)) {
                addPerk(validPerks, perk);
            }
            if (validPerks.isEmpty()) return;
            for (Perk perk : validPerks) {
                DisplayInfo displayInfo = getDisplayInfo(p, perk);


                boolean isRoot = perk instanceof RootPerk;
                if (!isRoot && perk.getParent() == null)
                    throw new Exception("Perk " + perk.getRegistryName() + " has null parent.");
                TreeMenuOption option = TreeMenuOption.Builder.builder().withDisplay(displayInfo)
                        .withParentId(isRoot ? null : perk.getParent().getRegistryName())
                        .build(perk.getRegistryName(), optionList);
                if (p.hasPerk(perk)) option.setHighlight(true);

                if (isRoot) {
                    root = option;
                }
                optionList.add(option);
            }
            TreeMenuNode.layout(root);
            TreeMenuTabGui gui = TreeMenuTabGui.create(minecraft, this, i, root.getDisplay());
            for (TreeMenuOption option : optionList) {
                gui.addOption(option);
            }
            tabs.put(root, gui);
            i++;
        }
    }

    private DisplayInfo getDisplayInfo(Profile p, Perk perk) {
        boolean hidden = false;
        if (!minecraft.player.isCreative()) {
            if (perk.getRequiredPerk() != null) {
                final Perk req = perk.getRequiredPerk();
                if (req != null && !p.getPlayerSkills().contains(req)) {
                    hidden = true;
                }
                if (perk.isExcluded(p)) {
                    hidden = true;
                }
            }
            if (perk instanceof RootPerk) {
                if (!p.isPerkTypeUnlocked(perk.getType())) hidden = true;
            }
        }
        boolean hasPerk = p.hasPerk(perk);
        ItemStack icon = new ItemStack(perk.getMenuIcon(), 1);
        if (hidden) icon = new ItemStack(Items.IRON_BARS);
        IFormattableTextComponent title = new StringTextComponent(hidden ? "Locked" : (perk instanceof RootPerk) ? MythriaUtil.capitalize(perk.getType().name()) : perk.getDisplayName());
        title.mergeStyle(hasPerk ? ColorConst.MAIN_COLOR : ColorConst.HEAD_COLOR);
        StringBuilder desc = new StringBuilder();
        for (String s : perk.getDataLines(p)) {
            desc.append(s).append("\n");
        }
        desc = new StringBuilder(desc.substring(0, desc.length() - 2));
        StringTextComponent description = new StringTextComponent(desc.toString());

        ResourceLocation background = null;
        if (perk instanceof RootPerk) {
            background = ((RootPerk) perk).getBackground();
        }
        return new DisplayInfo(icon, title, description,
                background, FrameType.TASK, false, false, hidden);
    }

    protected void addPerk(List<Perk> validPerks, Perk perk) {
        if (!(perk instanceof RootPerk) && !validPerks.contains(perk.getParent()))
            addPerk(validPerks, perk.getParent());
        if (!validPerks.contains(perk)) validPerks.add(perk);
    }
}
