package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Managers.CombatManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketAttack {
    private EnumAttackType type;
    private int entityID;
    private int hand;
    private boolean abilityMode;

    public CPacketAttack(int entityID, int hand, EnumAttackType type, boolean abilityMode) {
        this.entityID = entityID;
        this.hand = hand;
        this.type = type;
        this.abilityMode = abilityMode;
    }

    public CPacketAttack(PacketBuffer buf) {
        entityID = buf.readInt();
        type = EnumAttackType.values()[buf.readInt()];
        hand = buf.readInt();
        abilityMode = buf.readBoolean();
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(entityID);
        buf.writeInt(type.ordinal());
        buf.writeInt(hand);
        buf.writeBoolean(abilityMode);
    }

    public static void handle(CPacketAttack msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            World world = contextSupplier.get().getSender().world;
            Entity entity = world.getEntityByID(msg.entityID);
            if (entity != null) {
                if (entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity || entity instanceof ArrowEntity
                        || entity == contextSupplier.get().getSender()) {
                    contextSupplier.get().getNetworkManager().getNetHandler()
                            .onDisconnect(new TranslationTextComponent("multiplayer.disconnect.invalid_entity_attacked"));
                    return;
                }
            }
            if (msg.abilityMode) {

            } else {
                CombatManager.attackEntity(contextSupplier.get().getSender(), entity, Hand.values()[msg.hand], msg.type,
                        false, false);
            }
        });
    }
}
