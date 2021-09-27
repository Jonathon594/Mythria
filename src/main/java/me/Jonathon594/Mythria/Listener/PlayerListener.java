package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.ChatChannel;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.Managers.Crafting.ConstructionManager;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.SPacketProfileCache;
import me.Jonathon594.Mythria.Perk.Perks;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import me.Jonathon594.Mythria.Worlds.MythriaWorlds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class PlayerListener {
    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event) {
        final PlayerEntity p = event.getPlayer();
        final Profile profile = ProfileProvider.getProfile(p);
        Block block = event.getState().getBlock();
        double cost = MaterialManager.getStaminaCostForBreaking(event.getState(), p.world, event.getPos());
        profile.addConsumable(Consumable.STAMINA, -cost);
        ArrayList<Block> treeBlocks = TreeFellingManager.HandleTreeChop(p, event.getPos());
        if (treeBlocks.size() > 0) {
            for (Block b : treeBlocks) {
                addExperienceForBreaking((ServerPlayerEntity) p, profile, b);
            }
        } else {
            addExperienceForBreaking((ServerPlayerEntity) p, profile, block);
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(final BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof PlayerEntity)) return;
        final PlayerEntity p = (PlayerEntity) entity;
        double cost = MaterialManager.getStaminaCostForBreaking(event.getPlacedBlock(), event.getWorld(), event.getPos());
        if (!(entity instanceof PlayerEntity)) return;
        final Profile profile = ProfileProvider.getProfile(p);
        final List<Perk> list = MaterialManager.PERKS_FOR_PLACING.get(event.getPlacedBlock().getBlock());
        if (list != null) {
            for (final Perk pa : list) {
                if (profile.getPlayerSkills().contains(pa)) {
                    for (final Map.Entry<Skill, Integer> s : pa.getRequiredSkills().entrySet())
                        profile.addSkillExperience(s.getKey(), EXPConst.BLOCK_PLACE * (s.getValue() / 10.0 + 1), (ServerPlayerEntity) p, s.getValue());
                }
            }
        }
        profile.addConsumable(Consumable.STAMINA, -cost);
        if (ConstructionManager.isReinforced(event.getPlacedBlock().getBlock()))
            ConstructionManager.addRecentlyPlacedBlock(p, event.getPlacedBlock().getBlock(), event.getPos());
    }

    @SubscribeEvent
    public static void onBreakSpeed(final PlayerEvent.BreakSpeed event) {
        float originalSpeed = event.getOriginalSpeed();
        PlayerEntity p = event.getPlayer();
        Profile profile = ProfileProvider.getProfile(p);
        if (p.world.getDimensionKey().equals(MythriaWorlds.SPAWN_KEY)) {
            event.setCanceled(true);
            return;
        }

        float speed = event.getOriginalSpeed() / 10f;
        if (ConstructionManager.isReinforced(event.getState().getBlock())) {
            speed /= 50;
        }

        final List<Perk> list = MaterialManager.PERKS_FOR_BREAKING.get(event.getState().getBlock());
        if (list != null) {
            HashMap<Skill, Integer> levelModifiers = new HashMap<>();
            boolean able = list.size() == 0;
            for (final Perk pa : list) {
                if (profile.getPlayerSkills().contains(pa)) able = true;
                for (Skill skill : pa.getRequiredSkills().keySet()) {
                    if (!levelModifiers.containsKey(skill)) levelModifiers.put(skill, profile.getSkillLevel(skill));
                }
            }
            if (!able) {
                event.setCanceled(true);
                return;
            }
            float highestMult = 1f;
            for (int level : levelModifiers.values()) {
                float mult = level / 100f + 1f;
                highestMult = Math.max(highestMult, mult);
            }
            speed *= highestMult;
        }

        double cost = MaterialManager.getStaminaCostForBreaking(event.getState(), p.world, event.getPos());
        if (profile.getConsumable(Consumable.STAMINA) < cost) {
            event.setCanceled(true);
            return;
        }
        event.setNewSpeed(Math.min(originalSpeed, speed));
    }

    @SubscribeEvent
    public static void onCloseContainer(final PlayerContainerEvent.Close event) {
        PlayerEntity player = event.getPlayer();
        if (player.getEntityWorld().isRemote) {
        }
    }

    @SubscribeEvent
    public static void onCraftItem(final PlayerEvent.ItemCraftedEvent event) {
        // If client, return;
        PlayerEntity player = event.getPlayer();
        if (player.getEntityWorld().isRemote)
            return;
        final Profile p = ProfileProvider.getProfile(player);
        final List<Perk> list = MaterialManager.PERKS_FOR_CRAFTING
                .get(event.getCrafting().getItem());
        if (list != null)
            for (final Perk pa : list)
                if (p.getPlayerSkills().contains(pa))
                    for (final Map.Entry<Skill, Integer> s : pa.getRequiredSkills().entrySet())
                        p.addSkillExperience(s.getKey(), EXPConst.ITEM_CRAFT * (s.getValue() / 10.0 + 1), (ServerPlayerEntity) player, s.getValue());
    }

    @SubscribeEvent
    public static void onItemPickup(final PlayerEvent.ItemPickupEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.getEntityWorld().isRemote) {
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(final PlayerInteractEvent.LeftClickBlock event) {
        if (event.getWorld().isRemote) return;
        BlessingManager.onPunchBlock(event);
    }

    @SubscribeEvent
    public static void onPlayerOpenContainer(PlayerContainerEvent.Open event) {
        PlayerEntity player = event.getPlayer();
        LimitedInventoryManager.onOpenContainer(player, event.getContainer());
        FoodManager.updateFoodItems(event);
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        Profile profile = ProfileProvider.getProfile(player);
        ItemStack is = player.getHeldItem(event.getHand());
        if (is.isEmpty()) return;
        Item item = is.getItem();
        if (!(item instanceof BlockItem)) return;
        Block block = ((BlockItem) item).getBlock();
        List<Perk> perks = MaterialManager.PERKS_FOR_PLACING.get(block);
        if (perks == null) return;
        boolean able = perks.size() == 0;
        for (Perk perk : perks) {
            if (profile.getPlayerSkills().contains(perk)) {
                able = true;
                break;
            }
        }
        if (!able) {
            event.setCancellationResult(ActionResultType.PASS);
            event.setCanceled(true);
            if (!player.world.isRemote)
                player.sendMessage(new StringTextComponent(MythriaConst.CANT_PLACE), Util.DUMMY_UUID);
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        if (item instanceof ArmorItem) {
            ArmorItem armor = (ArmorItem) item;
            if (!LimitedInventoryManager.isArmorSlotOpen(event.getPlayer(), armor.getEquipmentSlot().getIndex())) {
                event.setCanceled(true);
                event.setCancellationResult(ActionResultType.FAIL);
            }
        }
    }

    @SubscribeEvent
    public static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        final ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        final Profile p = ProfileProvider.getProfile(player);
        player.func_242111_a(MythriaWorlds.SPAWN_KEY, new BlockPos(0, 64, 0), 0.0f, true, false);
        MythriaUtil.grantRecipeAdvancements(player);
        player.resetRecipes(player.server.getRecipeManager().getRecipes().stream().filter(iRecipe -> {
            if (iRecipe.getId().getNamespace() != Mythria.MODID) return true;
            ItemStack recipeOutput = iRecipe.getRecipeOutput();
            Item item = recipeOutput.getItem();
            if (!Perks.requiresPerk(item)) return false;
            boolean flag = false;
            for (Perk perk : MaterialManager.PERKS_FOR_CRAFTING.get(item)) {
                if (p.hasPerk(perk)) flag = true;
            }
            return flag;
        }).collect(Collectors.toList()));
        ChatManager.setChatChannel(player, ChatChannel.LOCAL);
        //MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);
        //mythriaPlayer.setPlayer(player);
        MythriaUtil.unlockDefaultRecipes(player);
        MythriaUtil.addRecipesFromPerks(player, p.getPerks());

        if (p.getCreated()) {
            //CompoundNBT renderData = MythriaRenderManager.createRenderData(p, player);
            //MythriaRenderManager.setRenderData(player.getUniqueID(), renderData);
            //MythriaRenderManager.sendSingleRenderDataToAll(player.getUniqueID());

            MythriaPacketHandler.sendTo(new SPacketProfileCache(p.toNBT()), player);
        } else {
            MythriaUtil.teleportPlayerToSpawnDimension(player);
        }
        //MythriaRenderManager.sendAllRenderDataToSingle(player);
        //SexManager.sendSexData(player);
        //NPCManager.onPlayerLoggedIn(player, p);
        //NPCManager.onPlayerLoggedOut(player);
        //SurvivalManager.onLoggedIn(player, p);

        p.copySkinToMythriaPlayer(MythriaPlayerProvider.getMythriaPlayer(player));

        updatePlayerCapabilities(p, player);
    }

    @SubscribeEvent()
    public static void playerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getPlayer().getEntityWorld().isRemote)
            return;
        final ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        Profile profile = ProfileProvider.getProfile(player);
        //NPCManager.onPlayerLoggedOut(player);
        profile.setLastDisconnect(System.currentTimeMillis());
    }

    @SubscribeEvent
    public static void playerRespawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
        final ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        player.getCapability(ProfileProvider.PROFILE_CAP, null);
    }

    private static void updatePlayerCapabilities(Profile profile, ServerPlayerEntity player) {
        boolean canFly = profile.hasFlag(AttributeFlag.FAE_FLIGHT) || player.isCreative() || player.isSpectator();

        player.abilities.allowFlying = canFly;
        player.sendPlayerAbilities();
    }

    protected static void addExperienceForBreaking(ServerPlayerEntity p, Profile profile, Block block) {
        final List<Perk> list = MaterialManager.PERKS_FOR_BREAKING.get(block);
        if (list != null) {
            for (final Perk pa : list)
                if (profile.getPlayerSkills().contains(pa)) {
                    for (final Map.Entry<Skill, Integer> s : pa.getRequiredSkills().entrySet())
                        profile.addSkillExperience(s.getKey(), EXPConst.BLOCK_BREAK * (s.getValue() / 10.0 + 1), p, s.getValue());
                }
        }
    }
}
