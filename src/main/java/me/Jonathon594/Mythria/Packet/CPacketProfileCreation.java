package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.MythriaConst;
import me.Jonathon594.Mythria.DataTypes.SkinPart;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Managers.SpawnManager;
import me.Jonathon594.Mythria.Managers.StatManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketProfileCreation extends NBTPacket {
    public CPacketProfileCreation(CompoundNBT compoundNBT) {
        super(compoundNBT);
    }

    public CPacketProfileCreation(PacketBuffer packetBuffer) {
        super(packetBuffer);
    }

    public static void handle(CPacketProfileCreation msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final ServerPlayerEntity serverPlayer = contextSupplier.get().getSender();
        final Profile profile = ProfileProvider.getProfile(serverPlayer);
        Profile temp = new Profile();
        CompoundNBT nbt = msg.getNbt();
        CompoundNBT profileNBT = nbt.getCompound("Profile");
        temp.fromNBT(profileNBT);

        contextSupplier.get().enqueueWork(() -> {
            if (profile.getCreated()) {
                serverPlayer.sendMessage(new StringTextComponent(MythriaConst.ALREADY_PROFILE), Util.DUMMY_UUID);
                return;
            }

            profile.fromNBT(profileNBT);
            profile.getPlayerSkills().clear();
            profile.getSkillLevels().clear();
            profile.getAbilities().clear();
            profile.fillHashMaps();

            for (SkinPart.Type type : SkinPart.Type.values()) {
                profile.setSkinData(type, temp.getSkinData(type));
            }

            profile.setGenetic(temp.getGenetic());
            profile.setCreated(true);
            StatManager.applyInitialStats(profile, serverPlayer);
            profile.applyRandomStatModifiers();
            MythriaUtil.unlockDefaultRecipes(serverPlayer);
            serverPlayer.getFoodStats().setFoodLevel(20);
            profile.unlockPerkType(PerkType.SURVIVAL);
            SpawnManager.spawnInWorld(serverPlayer, profile);
            profile.sendDataPacket();
            profile.copySkinToMythriaPlayer(MythriaPlayerProvider.getMythriaPlayer(serverPlayer));
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
