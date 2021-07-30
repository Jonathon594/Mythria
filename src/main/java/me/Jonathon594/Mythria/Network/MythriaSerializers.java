package me.Jonathon594.Mythria.Network;

import me.Jonathon594.Mythria.Enum.ControlMode;
import me.Jonathon594.Mythria.Enum.Gender;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Skin.SkinPart;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;

public class MythriaSerializers {
    public static final IDataSerializer<SkinPart> SKIN = new IDataSerializer<SkinPart>() {
        public void write(PacketBuffer buf, SkinPart value) {
            buf.writeString(value == null ? "" : value.getRegistryName().toString());
        }

        public SkinPart read(PacketBuffer buf) {
            return MythriaRegistries.SKIN_PARTS.getValue(new ResourceLocation(buf.readString()));
        }

        public SkinPart copyValue(SkinPart value) {
            return value;
        }
    };

    public static final IDataSerializer<Gender> GENDER = new IDataSerializer<Gender>() {
        public void write(PacketBuffer buf, Gender value) {
            buf.writeString(value.name());
        }

        public Gender read(PacketBuffer buf) {
            return Gender.valueOf(buf.readString());
        }

        public Gender copyValue(Gender value) {
            return value;
        }
    };

    public static final IDataSerializer<ControlMode> CONTROL_MODE = new IDataSerializer<ControlMode>() {
        public void write(PacketBuffer buf, ControlMode value) {
            buf.writeString(value.name());
        }

        public ControlMode read(PacketBuffer buf) {
            return ControlMode.valueOf(buf.readString());
        }

        public ControlMode copyValue(ControlMode value) {
            return value;
        }
    };

    public static void init() {
        DataSerializers.registerSerializer(SKIN);
        DataSerializers.registerSerializer(GENDER);
        DataSerializers.registerSerializer(CONTROL_MODE);
    }
}
