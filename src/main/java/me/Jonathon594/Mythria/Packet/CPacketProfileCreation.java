package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.ISkinPartGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.DataTypes.Origins.Origin;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Managers.SpawnManager;
import me.Jonathon594.Mythria.Managers.StatManager;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinPart;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CPacketProfileCreation {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final int month;
    private final int day;
    private final GeneticType geneticType;
    private final Gender gender;
    private final SkinPart hair;
    private final SkinPart eyes;
    private final SkinPart clothes;
    private final SkinPart skin;
    private final Origin origin;
    private SkinPart unique;

    public CPacketProfileCreation(String firstName, String middleName, String lastName, int month,
                                  int day, GeneticType geneticType, Gender gender, SkinPart hair, SkinPart eyes, SkinPart clothes,
                                  SkinPart skin, @Nullable SkinPart unique, Origin origin) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.month = month;
        this.day = day;
        this.geneticType = geneticType;
        this.gender = gender;
        this.hair = hair;
        this.eyes = eyes;
        this.clothes = clothes;
        this.skin = skin;
        this.unique = unique;
        this.origin = origin;
    }

    public CPacketProfileCreation(PacketBuffer packetBuffer) {
        firstName = packetBuffer.readString(32767);
        middleName = packetBuffer.readString(32767);
        lastName = packetBuffer.readString(32767);
        month = packetBuffer.readInt();
        day = packetBuffer.readInt();
        geneticType = MythriaRegistries.GENETICS.getValue(new ResourceLocation(packetBuffer.readString(32767)));
        gender = Gender.valueOf(packetBuffer.readString(32767));
        hair = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(packetBuffer.readString(32767)));
        eyes = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(packetBuffer.readString(32767)));
        clothes = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(packetBuffer.readString(32767)));
        skin = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(packetBuffer.readString(32767)));
        origin = MythriaRegistries.ORIGINS.getValue(new ResourceLocation(packetBuffer.readString(32767)));

        String s = packetBuffer.readString(32767);
        if (!s.isEmpty()) unique = MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(s));
    }

    public static void encode(CPacketProfileCreation msg, PacketBuffer packetBuffer) {
        packetBuffer.writeString(msg.getFirstName());
        packetBuffer.writeString(msg.getMiddleName());
        packetBuffer.writeString(msg.getLastName());
        packetBuffer.writeInt(msg.getMonth());
        packetBuffer.writeInt(msg.getDay());
        packetBuffer.writeString(msg.getGeneticType().getRegistryName().toString());
        packetBuffer.writeString(msg.getGender().name());
        packetBuffer.writeString(msg.getHair().getRegistryName().toString());
        packetBuffer.writeString(msg.getEyes().getRegistryName().toString());
        packetBuffer.writeString(msg.getClothes().getRegistryName().toString());
        packetBuffer.writeString(msg.getSkin().getRegistryName().toString());
        packetBuffer.writeString(msg.getOrigin().getRegistryName().toString());
        packetBuffer.writeString(msg.getUnique() == null ? "" : msg.getUnique().getRegistryName().toString());
    }

    public SkinPart getClothes() {
        return clothes;
    }

    public int getDay() {
        return day;
    }

    public SkinPart getEyes() {
        return eyes;
    }

    public String getFirstName() {
        return firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public GeneticType getGeneticType() {
        return geneticType;
    }

    public SkinPart getHair() {
        return hair;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getMonth() {
        return month;
    }

    public Origin getOrigin() {
        return origin;
    }

    public SkinPart getSkin() {
        return skin;
    }

    public SkinPart getUnique() {
        return unique;
    }

    public static void handle(CPacketProfileCreation msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            final ServerPlayerEntity serverPlayer = contextSupplier.get().getSender();
            Profile profile = ProfileProvider.getProfile(serverPlayer);
            profile.init();

            profile.setFirstName(msg.getFirstName());
            profile.setMiddleName(msg.getMiddleName());
            profile.setLastName(msg.getLastName());
            Genetic genetic = msg.getGeneticType().createGenetic();
            profile.setBirthDay(MythriaUtil.getDateFromAgeMonthDay(
                    msg.getOrigin().getStartingAge(genetic), msg.getMonth(), msg.getDay()));
            profile.setClothing(msg.getClothes());
            genetic.getHair().setSkinPart(msg.getHair());
            genetic.getEyes().setSkinPart(msg.getEyes());
            genetic.getSkin().setSkinPart(msg.getSkin());
            if (msg.getUnique() != null) {
                for (Gene gene : genetic.getExtraGenes()) {
                    if (!(gene instanceof ISkinPartGene)) continue;
                    ISkinPartGene skinPartGene = (ISkinPartGene) gene;
                    if (skinPartGene.getSkinPart().getType().equals(msg.getGeneticType().getSpecialSkinPartType())) {
                        skinPartGene.setSkinPart(msg.getUnique());
                    }
                }
            }
            profile.setGenetic(genetic);
            profile.setGender(msg.getGender());
            profile.setCreated(true);
            StatManager.applyInitialStats(profile, serverPlayer);
            profile.applyRandomStatModifiers();
            MythriaUtil.unlockDefaultRecipes(serverPlayer);
            serverPlayer.getFoodStats().setFoodLevel(20);
            profile.unlockPerkType(PerkType.SURVIVAL);
            SpawnManager.spawnInWorld(serverPlayer, profile);
            msg.getOrigin().apply(serverPlayer, profile);
            profile.sendDataPacket();
            profile.copySkinToMythriaPlayer(MythriaPlayerProvider.getMythriaPlayer(serverPlayer));
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
