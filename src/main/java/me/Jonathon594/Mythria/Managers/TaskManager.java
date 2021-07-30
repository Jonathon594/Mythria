package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Managers.Tasks.AbstractTask;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskManager {
    private static ArrayList<AbstractTask> tasks = new ArrayList<>();

    public static void onTick(TickEvent.ServerTickEvent event) {
        if(event.phase.equals(TickEvent.Phase.END)) return;
        Iterator<AbstractTask> it = tasks.iterator();
        while (it.hasNext()) {
            AbstractTask task = it.next();
            if (task.shouldExecute()) {
                task.execute();
                it.remove();
                continue;
            }
            task.update();
        }
    }

    public static void addScheduleTask(AbstractTask task) {
        tasks.add(task);
    }
}
