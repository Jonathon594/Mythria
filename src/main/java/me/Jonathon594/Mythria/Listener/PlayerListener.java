package me.Jonathon594.Mythria.Listener;

import com.google.common.collect.Lists;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.Managers.Crafting.ConstructionManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.SPacketProfileCache;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import me.Jonathon594.Mythria.Worlds.MythriaWorlds;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class PlayerListener {
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

    @SubscribeEvent
    public static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        final ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        player.func_242111_a(MythriaWorlds.SPAWN_KEY, new BlockPos(0, 64, 0), 0.0f, true, false);
        MythriaUtil.grantRecipeAdvancements(player);
        player.resetRecipes(Lists.newArrayList(player.server.getRecipeManager().getRecipes()));
        ChatManager.setChatChannel(player, ChatChannel.LOCAL);
        final Profile p = ProfileProvider.getProfile(player);
        //MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(player);
        //mythriaPlayer.setPlayer(player);
        MythriaUtil.unlockDefaultRecipes(player);

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

    private static void updatePlayerCapabilities(Profile profile, ServerPlayerEntity player) {
        boolean canFly = false;
        if (profile.hasFlag(AttributeFlag.FAE_FLIGHT) || player.isCreative() || player.isSpectator()) {
            canFly = true;
        }

        player.abilities.allowFlying = canFly;
        player.sendPlayerAbilities();
    }

    @SubscribeEvent
    public static void onPlayerOpenContainer(PlayerContainerEvent.Open event) {
        PlayerEntity player = event.getPlayer();
        LimitedInventoryManager.onOpenContainer(player, event.getContainer());
        FoodManager.updateFoodItems(event);
    }

    @SubscribeEvent
    public static void onCloseContainer(final PlayerContainerEvent.Close event) {
        PlayerEntity player = event.getPlayer();
        if (player.getEntityWorld().isRemote) {
        }
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
                    for (final Map.Entry<MythicSkills, Integer> s : pa.getRequiredSkills().entrySet())
                        p.addSkillExperience(s.getKey(), EXPConst.ITEM_CRAFT * (s.getValue() / 10.0 + 1), (ServerPlayerEntity) player, s.getValue());
    }

    @SubscribeEvent
    public static void onBreakSpeed(final net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        PlayerEntity p = event.getPlayer();
        Profile profile = ProfileProvider.getProfile(p);
        float factor = 0f;
        if (MythriaUtil.isOre(event.getState().getBlock())) {
            int mining = profile.getSkillLevel(MythicSkills.MINING);
            factor = MathHelper.clamp((float) mining / 100f, 0, 1);
        }
        if (event.getState().getMaterial().equals(Material.ROCK)) {
            if (profile.hasFlag(AttributeFlag.DWARF_DIGGING)) factor = 1;
        }
        int strength = profile.getAttributeLevel(Attribute.STRENGTH);
        float strengthFactor = MathHelper.clamp((float) strength / 200f, 0f, 0.5f);
        float finalFactor = Math.max(strengthFactor, factor);
        int breakSpeedMultiplier = 10;

        if (ConstructionManager.isReinforced(event.getState().getBlock())) {
            breakSpeedMultiplier = 500;
        }

        event.setNewSpeed(event.getOriginalSpeed() / (1 + ((breakSpeedMultiplier - 1) * (1 - finalFactor))));
    }

    @SubscribeEvent
    public static void onBlockPlace(final BlockEvent.EntityPlaceEvent event) {
        // If client, return;
        if (event.getWorld().isRemote()) return;
        Entity entity = event.getEntity();
        if (!(entity instanceof PlayerEntity)) return;
        final PlayerEntity p = (PlayerEntity) entity;
        if (p.isCreative())
            return;
        if (p.world.getDimensionKey().equals(MythriaWorlds.SPAWN_KEY)) {
            event.setCanceled(true);
            return;
        }
        final Profile profile = ProfileProvider.getProfile(p);
        double cost = MaterialManager.getStaminaCostForBreaking(event.getPlacedBlock(), event.getWorld(), event.getPos());
        if (profile.getConsumable(Consumable.STAMINA) < cost) {
            event.setCanceled(true);
            return;
        }

        MaterialManager.onBlockPlace(event);
        if (event.isCanceled())
            return;

        StatManager.chargeConsumable(p, cost, Consumable.STAMINA);
        //BlockUtils.physicsCheck(event.getWorld().getWorld(), event.getPos(), event.getPlacedBlock().getBlock(), 10,
        //        true);

        if (ConstructionManager.isReinforced(event.getPlacedBlock().getBlock()))
            ConstructionManager.addRecentlyPlacedBlock(p, event.getPlacedBlock().getBlock(), event.getPos());
    }

    @SubscribeEvent
    public static void onItemToss(final ItemTossEvent event) {
        if (event.getPlayer().getEntityWorld().isRemote) {
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event) {
        // If client, return;
        if (event.getWorld().isRemote())
            return;
        final PlayerEntity p = event.getPlayer();
        if (p.isCreative())
            return;
        if (p.world.getDimensionKey().equals(MythriaWorlds.SPAWN_KEY)) {
            event.setCanceled(true);
            return;
        }
        final Profile profile = ProfileProvider.getProfile(p);

        double cost = MaterialManager.getStaminaCostForBreaking(event.getState(), event.getWorld(), event.getPos());
        if (profile.getConsumable(Consumable.STAMINA) < cost) {
            event.setCanceled(true);
            return;
        }
        MaterialManager.onBlockBreak(event, p, profile);
        if (event.isCanceled())
            return;
        if (event.isCanceled())
            return;

        StatManager.chargeConsumable(event.getPlayer(), cost, Consumable.STAMINA);
        TreeFellingManager.HandleTreeChop(p, event.getPos());
        //BlockUtils.physicsCheck(event.getWorld().getWorld(), event.getPos(), event.getState().getBlock(), 10,
        //        true);
    }
}
