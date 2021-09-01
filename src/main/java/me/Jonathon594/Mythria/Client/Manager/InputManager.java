package me.Jonathon594.Mythria.Client.Manager;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Keybindings;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Interface.IWeapon;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketParry;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.InputEvent;

public class InputManager {
    public static final int HEAVY_ATTACK_THRESHOLD = 10;
    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean deltaAttack = false;
    private static boolean deltaUse = false;
    private static boolean deltaParry = false;

    public static void onClickMouse(InputEvent.ClickInputEvent event) {
        if (event.isPickBlock()) return;
        boolean cancel = true;
        ClientPlayerEntity player = mc.player;
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);
        RayTraceResult result = mc.objectMouseOver;

        if (mythriaPlayer.getControlMode().equals(ControlMode.NORMAL)) {
            if (result.getType().equals(RayTraceResult.Type.BLOCK) && mythriaPlayer.getAttackingMainhand() == 0) {
                InputIntent inputIntent = mythriaPlayer.getInputIntent(Hand.MAIN_HAND);
                if (inputIntent.equals(InputIntent.NONE) || inputIntent.equals(InputIntent.MINE)) {
                    mythriaPlayer.setInputIntent(Hand.MAIN_HAND, InputIntent.MINE);
                    cancel = false;
                }
            }
            InputIntent inputIntent = mythriaPlayer.getInputIntent(Hand.OFF_HAND);
            if (mythriaPlayer.getCombatMode().equals(CombatMode.NORMAL) && event.isUseItem() && mythriaPlayer.getAttackingOffhand() == 0 &&
                    (inputIntent.equals(InputIntent.NONE) || inputIntent.equals(InputIntent.USE))) {
                mythriaPlayer.setInputIntent(Hand.OFF_HAND, InputIntent.USE);
                cancel = false;
            }
        }

        if (cancel) {
            event.setCanceled(true);
            event.setSwingHand(false);
        }
    }

    public static void onClientTick() {
        GameSettings gameSettings = mc.gameSettings;

        ClientPlayerEntity player = mc.player;
        if (player == null) return;
        Profile profile = ProfileProvider.getProfile(player);
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);
        RayTraceResult result = mc.objectMouseOver;
        boolean lookingAtBlock = result.getType().equals(RayTraceResult.Type.BLOCK);

        boolean attack = gameSettings.keyBindAttack.isKeyDown();
        boolean useItem = gameSettings.keyBindUseItem.isKeyDown();
        boolean parry = Keybindings.PARRY.isKeyDown();

        boolean attackPressed = attack && !deltaAttack;
        boolean usePressed = useItem && !deltaUse;
        boolean parryPressed = parry && !deltaParry;
        boolean attackReleased = !attack && deltaAttack;
        boolean useReleased = !useItem && deltaUse;
        boolean parryReleased = !parry && deltaParry;

        boolean isAttackReady = player.getCooledAttackStrength(0) == 1;
        switch (mythriaPlayer.getControlMode()) {
            case NORMAL:
                Hand hand = Hand.MAIN_HAND;
                int attackingMainhand = mythriaPlayer.getAttackingMainhand();
                if (attack && attackingMainhand < HEAVY_ATTACK_THRESHOLD) {
                    if (attackPressed && mythriaPlayer.getInputIntent(hand).equals(InputIntent.NONE) && !lookingAtBlock && isAttackReady) {
                        mythriaPlayer.setInputIntent(hand, InputIntent.ATTACK);
                        if(!canHeavyAttack(player, hand)) {
                            attack(result, hand, AttackClass.LIGHT);
                            return;
                        }
                    }
                    if (mythriaPlayer.getInputIntent(hand).equals(InputIntent.ATTACK)) {
                            mythriaPlayer.setAttackingMainhand(attackingMainhand + 1);
                    }
                } else if (attackReleased || attackingMainhand >= HEAVY_ATTACK_THRESHOLD) {
                    if (mythriaPlayer.getInputIntent(hand).equals(InputIntent.ATTACK)) {
                        attack(result, hand, attackingMainhand >= HEAVY_ATTACK_THRESHOLD ? AttackClass.HEAVY : AttackClass.LIGHT);
                    }
                    if (attackReleased) {
                        mythriaPlayer.setAttackingMainhand(0);
                    }
                    mythriaPlayer.setInputIntent(hand, InputIntent.NONE);
                }

                boolean isDual = mythriaPlayer.getCombatMode().equals(CombatMode.DUAL);
                hand = Hand.OFF_HAND;
                int attackingOffhand = mythriaPlayer.getAttackingOffhand();
                if (useItem && isDual && attackingOffhand < HEAVY_ATTACK_THRESHOLD) {
                    if (usePressed && mythriaPlayer.getInputIntent(hand).equals(InputIntent.NONE) && !lookingAtBlock && isAttackReady) {
                        mythriaPlayer.setInputIntent(hand, InputIntent.ATTACK);
                        if(!canHeavyAttack(player, hand)) {
                            attack(result, hand, AttackClass.LIGHT);
                            return;
                        }
                    }
                    if (mythriaPlayer.getInputIntent(hand).equals(InputIntent.ATTACK))
                        mythriaPlayer.setAttackingOffhand(attackingOffhand + 1);
                } else if (useReleased || attackingOffhand >= HEAVY_ATTACK_THRESHOLD) {
                    if (mythriaPlayer.getInputIntent(hand).equals(InputIntent.ATTACK) && isDual) {
                        attack(result, Hand.OFF_HAND, attackingOffhand >= HEAVY_ATTACK_THRESHOLD ? AttackClass.HEAVY : AttackClass.LIGHT);
                    }
                    if (useReleased) {
                        mythriaPlayer.setAttackingOffhand(0);
                    }
                    mythriaPlayer.setInputIntent(hand, InputIntent.NONE);
                }

                if (parryPressed && attackingMainhand == 0 && attackingOffhand == 0 &&
                        mythriaPlayer.getInputIntent(Hand.OFF_HAND) == InputIntent.NONE &&
                        mythriaPlayer.getInputIntent(Hand.MAIN_HAND) == InputIntent.NONE) {
                    MythriaPacketHandler.sendToServer(new CPacketParry(true));
                }
                if (parryReleased && mythriaPlayer.isParrying()) {
                    MythriaPacketHandler.sendToServer(new CPacketParry(false));
                }
                break;
            case ABILITY:
                break;
        }

        //Last
        deltaAttack = attack;
        deltaUse = useItem;
        deltaParry = parry;
    }

    private static boolean canHeavyAttack(ClientPlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(itemStack.isEmpty()) return true;
        Item item = itemStack.getItem();
        if(item instanceof IWeapon) return ((IWeapon) item).canHeavyAttack();
        return false;
    }

    public static void onToggleCombatMode() {
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(mc.player);
        switch (mythriaPlayer.getCombatMode()) {
            case DUAL:
                mythriaPlayer.setCombatMode(CombatMode.NORMAL);
                break;
            case NORMAL:
                mythriaPlayer.setCombatMode(CombatMode.DUAL);
                break;
        }
    }

    public static void onToggleControlMode() {
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(mc.player);
        switch (mythriaPlayer.getControlMode()) {
            case NORMAL:
                mythriaPlayer.setControlMode(ControlMode.ABILITY);
                break;
            case ABILITY:
                mythriaPlayer.setControlMode(ControlMode.NORMAL);
                break;
        }
    }

    private static void attack(RayTraceResult result, Hand hand, AttackClass attackClass) {
        if (mc.player.getCooledAttackStrength(0) < 1) return;
        if (result.getType().equals(RayTraceResult.Type.ENTITY)) {
            ClientCombatManager.meleeAttack((EntityRayTraceResult) result, hand, attackClass);
        } else {
            final ItemStack itemStack = hand == Hand.MAIN_HAND ? mc.player.getHeldItemMainhand()
                    : mc.player.getHeldItemOffhand();
            mc.player.getAttributeManager().reapplyModifiers(itemStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
            mc.player.swingArm(hand);
            net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(mc.player); //delegate to forgehook.
            mc.player.resetCooldown();
        }

    }
}
