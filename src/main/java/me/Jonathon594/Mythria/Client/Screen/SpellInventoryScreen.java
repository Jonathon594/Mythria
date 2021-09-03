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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashSet;

public class SpellInventoryScreen extends Screen {
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new MythriaResourceLocation("textures/gui/spell_button.png");
    public static final ResourceLocation INVENTORY_BACKGROUND = new MythriaResourceLocation("textures/gui/container/ability_inventory.png");
    /**
     * The old x position of the mouse pointer
     */
    private float oldMouseX;
    /**
     * The old y position of the mouse pointer
     */
    private float oldMouseY;
    private final SpellBookGui spellBookGui = new SpellBookGui();
    private boolean removeRecipeBookGui;
    private boolean widthTooNarrow;
    private boolean buttonClicked;
    private int guiLeft;
    private int guiTop;
    //** The X size of the inventory window in pixels. */
    protected int xSize = 176;
    /**
     * The Y size of the inventory window in pixels.
     */
    protected int ySize = 166;
    protected int titleX;
    protected int titleY;
    private Profile profile;

    private HashSet<SpellSlot> spellSlots;
    private SpellSlot hoveredSlot = null;
    private Ability cursorAbility = null;

    public SpellInventoryScreen() {
        super(new TranslationTextComponent("screen.spell_inventory"));
        this.titleX = 8;
        this.titleY = 6;
        spellSlots = new HashSet<>();
    }

    protected void init() {
        super.init();
        profile = ProfileProvider.getProfile(minecraft.player);
        this.widthTooNarrow = this.width < 379;
        this.spellBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow);
        this.removeRecipeBookGui = true;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.guiLeft = this.spellBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
        this.children.add(this.spellBookGui);
        this.setFocusedDefault(this.spellBookGui);
        this.addButton(new ImageButton(this.guiLeft + 104, this.height / 2 - 22, 20, 18, 0, 0,
                19, RECIPE_BUTTON_TEXTURE, (button) -> {
            this.spellBookGui.initSearchBar(this.widthTooNarrow);
            this.spellBookGui.toggleVisibility();
            this.guiLeft = this.spellBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
            ((ImageButton) button).setPosition(this.guiLeft + 104, this.height / 2 - 22);
            this.buttonClicked = true;
        }));

        for (int i = 0; i < 9; i++) {
            spellSlots.add(new SpellSlot(i, guiLeft + 8 + i * 18, guiTop + 142));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                spellSlots.add(new SpellSlot(x + (y * 9) + 9, guiLeft + 8 + x * 18, guiTop + 84 + y * 18));
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
        //this.hasActivePotionEffects = !this.spellBookGui.isVisible();
        if (this.spellBookGui.isVisible() && this.widthTooNarrow) {
            this.spellBookGui.render(matrixStack, mouseX, mouseY, partialTicks);
        } else {
            this.spellBookGui.render(matrixStack, mouseX, mouseY, partialTicks);
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            this.spellBookGui.func_230477_a_(matrixStack, this.guiLeft, this.guiTop, false, partialTicks);
        }


        this.renderSlots(matrixStack, mouseX, mouseY);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        this.spellBookGui.func_238924_c_(matrixStack, this.guiLeft, this.guiTop, mouseX, mouseY);


        this.font.func_243248_b(matrixStack, this.title, (float) this.titleX, (float) this.titleY, 4210752);

        if (cursorAbility != null) {
            renderAbilityTexture(matrixStack, cursorAbility.getAbilityTexturePath(), mouseX - 8, mouseY - 8);
        }

        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }

    private void renderSlots(MatrixStack matrixStack, int mouseX, int mouseY) {
        for (SpellSlot spellSlot : spellSlots) {
            Ability ability = profile.getBoundAbility(getRelativeAbilitySlotIndex(spellSlot.getIndex()));
            if (ability == null) continue;
            renderAbilityTexture(matrixStack, ability.getAbilityTexturePath(), spellSlot.getX(), spellSlot.getY());
        }

        hoveredSlot = null;
        for (SpellSlot spellSlot : spellSlots) {
            if (spellSlot.isHovered(mouseX, mouseY)) {
                hoveredSlot = spellSlot;

                RenderSystem.disableDepthTest();
                int j1 = spellSlot.getX();
                int k1 = spellSlot.getY();
                RenderSystem.colorMask(true, true, true, false);
                int slotColor = -2130706433;
                this.fillGradient(matrixStack, j1, k1, j1 + 16, k1 + 16, slotColor, slotColor);
                RenderSystem.colorMask(true, true, true, true);
                RenderSystem.enableDepthTest();
            }
        }
    }

    private void renderAbilityTexture(MatrixStack matrixStack, ResourceLocation abilityTexturePath, int x, int y) {
        minecraft.getTextureManager().bindTexture(abilityTexturePath);
        matrixStack.push();
        matrixStack.scale(1 / 16f, 1 / 16f, 1);
        this.blit(matrixStack, x * 16, y * 16, 0, 0, 256, 256);
        matrixStack.pop();
    }

    protected void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        InventoryScreen.drawEntityOnScreen(i + 51, j + 75, 30, (float) (i + 51) - this.oldMouseX, (float) (j + 75 - 50) - this.oldMouseY, this.minecraft.player);
    }

    private void renderHoveredTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (hoveredSlot != null) {
            Ability ability = profile.getBoundAbility(getRelativeAbilitySlotIndex(hoveredSlot.getIndex()));
            if (ability == null) return;
            renderTooltip(matrixStack,
                    new TranslationTextComponent("abilities." + ability.getRegistryName().getPath() + ".name"), mouseX, mouseY);
        }
    }

    private boolean isSlotHovered(int slot, int mouseX, int mouseY) {
        for (SpellSlot spellSlot : spellSlots) {
            if (spellSlot.getIndex() == slot && spellSlot.isHovered(mouseX, mouseY)) return true;
        }
        return false;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.spellBookGui.mouseClicked(mouseX, mouseY, button)) {
            this.setListener(this.spellBookGui);
            return true;
        } else {
            if (this.widthTooNarrow && this.spellBookGui.isVisible()) {
                return false;
            }
            if (hoveredSlot != null) {
                return slotClicked(button);
            }
            return true;
        }
    }

    private boolean slotClicked(int button) {
        switch (button) {
            case 0:
                swapAbilities();
                break;
            case 1:
                removeAbility();
                break;
            case 2:
                pickAbility();
                break;
        }
        return true;
    }

    private void pickAbility() {
        cursorAbility = profile.getBoundAbility(getRelativeAbilitySlotIndex(hoveredSlot.getIndex()));
    }

    private void removeAbility() {
        profile.setBoundAbility(getRelativeAbilitySlotIndex(hoveredSlot.getIndex()), null);
    }

    private void swapAbilities() {
        int slotIndex = getRelativeAbilitySlotIndex(hoveredSlot.getIndex());
        Ability abilityOld = profile.getBoundAbility(slotIndex);
        profile.setBoundAbility(slotIndex, cursorAbility);
        cursorAbility = abilityOld;
    }

    private int getRelativeAbilitySlotIndex(int index) {
        return MythriaUtil.wrapInt(profile.getAbilityPreset() * 9 + index, 0, 35);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void onClose() {
        if (this.removeRecipeBookGui) {
            this.spellBookGui.removed();
        }

        super.onClose();
    }

    public SpellBookGui getSpellBookGui() {
        return this.spellBookGui;
    }
}
