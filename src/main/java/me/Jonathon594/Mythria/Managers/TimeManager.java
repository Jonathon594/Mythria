package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.Date;
import me.Jonathon594.Mythria.DataTypes.Month;
import me.Jonathon594.Mythria.Enum.BirthSign;
import me.Jonathon594.Mythria.Event.NewDayEvent;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.SPacketTimeManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class TimeManager {
    private final static List<String> DayNames = new ArrayList<>();
    private final static List<Month> Months = new ArrayList<>();
    private static Date currentDate;
    private static int daysPerYear = 0;

    public static Date getCurrentDate() {
        return currentDate;
    }

    public static List<String> getDayNames() {
        return DayNames;
    }

    public static int getDaysPerYear() {
        return daysPerYear;
    }

    public static List<Month> getMonths() {
        return Months;
    }

    public static void init() {
        currentDate = new Date(1);

        DayNames.add("Ras");
        DayNames.add("Mayark");
        DayNames.add("Tosalias");
        DayNames.add("Judios");
        DayNames.add("Amara");
        DayNames.add("Kaias");
        DayNames.add("Oast");

        Months.add(new Month("Infio", 31, BirthSign.AQUARIUS));
        Months.add(new Month("Amo", 30, BirthSign.PISCES));
        Months.add(new Month("Ver", 28, BirthSign.ARIES));
        Months.add(new Month("Inder", 31, BirthSign.TAURUS));
        Months.add(new Month("Granum", 30, BirthSign.GEMINI));
        Months.add(new Month("Ignis", 30, BirthSign.CANCER));
        Months.add(new Month("Sitis", 31, BirthSign.LEO));
        Months.add(new Month("Casus", 30, BirthSign.VIRGO));
        Months.add(new Month("Vicis", 31, BirthSign.LIBRA));
        Months.add(new Month("Neto", 30, BirthSign.SCORPIO));
        Months.add(new Month("Gelu", 31, BirthSign.SAGITTARIUS));
        Months.add(new Month("Exeo", 31, BirthSign.CAPRICORN));

        for (final Month m : Months)
            daysPerYear += m.getLength();
    }

    public static void onTick(final TickEvent.ServerTickEvent event) {
        if(event.phase.equals(TickEvent.Phase.START)) return;
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        final World w = currentServer.getWorld(World.OVERWORLD);
        final long time = w.getDayTime() % 24000;
        if (time == 0) {
            System.out.println(w.getDayTime());
            currentDate.IncDay();
            MythriaPacketHandler.sendToAll(new SPacketTimeManager(currentDate.getMGD()));
            final NewDayEvent nde = new NewDayEvent();
            MinecraftForge.EVENT_BUS.post(nde);
            for (final PlayerEntity p : currentServer.getPlayerList()
                    .getPlayers()) {
                p.sendMessage(new StringTextComponent(currentDate.getDateString()), Util.DUMMY_UUID);
            }
        }
    }
}
