package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Enum.AttackClass;
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
    private final EnumAttackType type;
    private final int entityID;
    private final int hand;
    private final AttackClass attackClass;

    public CPacketAttack(int entityID, int hand, EnumAttackType type, AttackClass attackClass) {
        this.entityID = entityID;
        this.hand = hand;
        this.type = type;
        this.attackClass = attackClass;
    }

    public CPacketAttack(PacketBuffer buf) {
        entityID = buf.readInt();
        type = EnumAttackType.values()[buf.readInt()];
        hand = buf.readInt();
        attackClass = AttackClass.values()[buf.readInt()];
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(entityID);
        buf.writeInt(type.ordinal());
        buf.writeInt(hand);
        buf.writeInt(attackClass.ordinal());
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
            CombatManager.attackEntity(contextSupplier.get().getSender(), entity, Hand.values()[msg.hand], msg.type, msg.attackClass,
                    false, false);
        });
    }
}
