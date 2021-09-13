package me.Jonathon594.Mythria.Client.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashSet;

public class AbilityInventoryScreen extends Screen {
    public static final ResourceLocation INVENTORY_BACKGROUND = new MythriaResourceLocation("textures/gui/ability_inventory.png");
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new MythriaResourceLocation("textures/gui/ability_button.png");
    private final AbilityBookGui abilityBookGui = new AbilityBookGui(this);
    private final HashSet<AbilitySlot> abilitySlots;
    //** The X size of the inventory window in pixels. */
    protected int xSize = 176;
    /**
     * The Y size of the inventory window in pixels.
     */
    protected int ySize = 166;
    protected int titleX;
    protected int titleY;
    /**
     * The old x position of the mouse pointer
     */
    private float oldMouseX;
    /**
     * The old y position of the mouse pointer
     */
    private float oldMouseY;
    private boolean removeRecipeBookGui;
    private boolean widthTooNarrow;
    private boolean buttonClicked;
    private int guiLeft;
    private int guiTop;
    private Profile profile;
    private AbilitySlot hoveredSlot = null;
    private Ability cursorAbility = null;

    public AbilityInventoryScreen() {
        super(new TranslationTextComponent("screen.spell_inventory"));
        this.titleX = 8;
        this.titleY = 6;
        abilitySlots = new HashSet<>();
    }

    public AbilityBookGui getSpellBookGui() {
        return this.abilityBookGui;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.abilityBookGui.mouseClicked(mouseX, mouseY, button)) {
            this.setListener(this.abilityBookGui);
            return true;
        } else {
            if (this.widthTooNarrow && this.abilityBookGui.isVisible()) {
                return false;
            }
            if (hoveredSlot != null) {
                return slotClicked(hoveredSlot, button);
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(mouseX, mouseY, button);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
        //this.hasActivePotionEffects = !this.spellBookGui.isVisible();
        if (this.abilityBookGui.isVisible() && this.widthTooNarrow) {
            this.abilityBookGui.render(matrixStack, mouseX, mouseY, partialTicks);
        } else {
            this.abilityBookGui.render(matrixStack, mouseX, mouseY, partialTicks);
            super.render(matrixStack, mouseX, mouseY, partialTicks);
        }


        this.renderSlots(matrixStack, mouseX, mouseY);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        this.abilityBookGui.func_238924_c_(matrixStack, this.guiLeft, this.guiTop, mouseX, mouseY);


        this.font.func_243248_b(matrixStack, this.title, (float) this.titleX, (float) this.titleY, 4210752);

        if (cursorAbility != null) {
            renderAbilityTexture(matrixStack, cursorAbility.getAbilityTexturePath(), mouseX - 8, mouseY - 8);
        }

        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode, scanCode);
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey)) {
            this.closeScreen();
            return true;
        }
        return false;
    }

    protected void init() {
        super.init();
        profile = ProfileProvider.getProfile(minecraft.player);
        this.widthTooNarrow = this.width < 379;
        this.abilityBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow);
        this.removeRecipeBookGui = true;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.guiLeft = this.abilityBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
        this.children.add(this.abilityBookGui);
        this.setFocusedDefault(this.abilityBookGui);
        this.addButton(new ImageButton(this.guiLeft + 104, this.height / 2 - 22, 20, 18, 0, 0,
                19, RECIPE_BUTTON_TEXTURE, (button) -> {
            this.abilityBookGui.initSearchBar(this.widthTooNarrow);
            this.abilityBookGui.toggleVisibility();
            this.guiLeft = this.abilityBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
            ((ImageButton) button).setPosition(this.guiLeft + 104, this.height / 2 - 22);
            this.buttonClicked = true;
        }));

        abilitySlots.clear();
        for (int i = 0; i < 9; i++) {
            abilitySlots.add(new AbilitySlot(i, 8 + i * 18, 142));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                abilitySlots.add(new AbilitySlot(x + (y * 9) + 9, 8 + x * 18, 84 + y * 18));
            }
        }
    }

    public void onClose() {
        if (this.removeRecipeBookGui) {
            this.abilityBookGui.removed();
        }

        super.onClose();
    }

    public void setCursorAbility(Ability ability) {
        this.cursorAbility = ability;
    }

    private Ability getAbilityInSlot(AbilitySlot slot) {
        return profile.getBoundAbility(getRelativeAbilitySlotIndex(slot.getIndex()));
    }

    private int getRelativeAbilitySlotIndex(int index) {
        return MythriaUtil.wrapInt(profile.getAbilityPreset() * 9 + index, 0, 35);
    }

    private void pickAbility() {
        cursorAbility = profile.getBoundAbility(getRelativeAbilitySlotIndex(hoveredSlot.getIndex()));
    }

    private void removeAbility() {
        profile.setBoundAbility(getRelativeAbilitySlotIndex(hoveredSlot.getIndex()), null);
    }

    private void renderAbilityTexture(MatrixStack matrixStack, ResourceLocation abilityTexturePath, int x, int y) {
        minecraft.getTextureManager().bindTexture(abilityTexturePath);
        matrixStack.push();
        matrixStack.scale(1 / 16f, 1 / 16f, 1);
        this.blit(matrixStack, x * 16, y * 16, 0, 0, 256, 256);
        matrixStack.pop();
    }

    private void renderHoveredTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (hoveredSlot != null) {
            Ability ability = profile.getBoundAbility(getRelativeAbilitySlotIndex(hoveredSlot.getIndex()));
            if (ability == null) return;
            renderWrappedToolTip(matrixStack, ability.getHoveredToolTip(), mouseX, mouseY, font);
        }
    }

    private void renderSlots(MatrixStack matrixStack, int mouseX, int mouseY) {
        for (AbilitySlot abilitySlot : abilitySlots) {
            Ability ability = profile.getBoundAbility(getRelativeAbilitySlotIndex(abilitySlot.getIndex()));
            if (ability == null) continue;
            renderAbilityTexture(matrixStack, ability.getAbilityTexturePath(), abilitySlot.getX() + guiLeft, abilitySlot.getY() + guiTop);
        }

        hoveredSlot = null;
        for (AbilitySlot abilitySlot : abilitySlots) {
            if (abilitySlot.isHovered(mouseX - guiLeft, mouseY - guiTop)) {
                hoveredSlot = abilitySlot;

                RenderSystem.disableDepthTest();
                int j1 = abilitySlot.getX() + guiLeft;
                int k1 = abilitySlot.getY() + guiTop;
                RenderSystem.colorMask(true, true, true, false);
                int slotColor = -2130706433;
                this.fillGradient(matrixStack, j1, k1, j1 + 16, k1 + 16, slotColor, slotColor);
                RenderSystem.colorMask(true, true, true, true);
                RenderSystem.enableDepthTest();
            }
        }
    }

    private void setAbility(int slotIndex, Ability toPick) {
        profile.setBoundAbility(slotIndex, cursorAbility);
        cursorAbility = toPick;
    }

    private boolean slotClicked(AbilitySlot slot, int button) {
        Ability slotAbility = getAbilityInSlot(slot);
        int slotIndex = getRelativeAbilitySlotIndex(hoveredSlot.getIndex());
        if (slotAbility == null) {
            if (cursorAbility != null) {
                setAbility(slotIndex, null);
            }
        } else {
            if (button == 0) {
                Ability abilityOld = profile.getBoundAbility(slotIndex);
                setAbility(slotIndex, abilityOld);
            }
            if (button == 1) {
                if (cursorAbility != null) {
                    setAbility(slotIndex, null);
                } else {
                    removeAbility();
                }
            }
            if (button == 2) {
                pickAbility();
            }
        }

        return true;
    }

    protected void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        InventoryScreen.drawEntityOnScreen(i + 51, j + 75, 30, (float) (i + 51) - this.oldMouseX, (float) (j + 75 - 50) - this.oldMouseY, this.minecraft.player);
    }
}
