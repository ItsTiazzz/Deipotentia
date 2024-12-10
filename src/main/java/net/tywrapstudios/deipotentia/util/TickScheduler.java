package net.tywrapstudios.deipotentia.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.tywrapstudios.blossombridge.api.logging.LoggingHandler;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.config.DeiConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TickScheduler {
    private static final List<ScheduledTask> tasks = new CopyOnWriteArrayList<>();
    private static final LoggingHandler<DeiConfig> LOGGER = new LoggingHandler<>(TickScheduler.class.getName(), Deipotentia.CONFIG_MANAGER);

    public static void schedule(int delayTicks, Runnable action) {
        synchronized (tasks) {
            tasks.add(new ScheduledTask(delayTicks, action));
        }
        LOGGER.debug("New task scheduled, tasks left: " + tasks.size());
    }

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(TickScheduler::onServerTick);
    }

    public static void onServerTick(MinecraftServer server) {
        List<ScheduledTask> toRemove = new ArrayList<>();

        // Iterate through tasks to check and execute
        for (ScheduledTask task : tasks) {
            task.ticksLeft--;
            if (task.ticksLeft <= 0) {
                task.action.run();
                toRemove.add(task); // Mark for removal
                LOGGER.debug("Task ran and marked for removal: " + task);
            }
        }

        // Remove completed tasks after iteration
        tasks.removeAll(toRemove);
    }

    // Internal class to store tasks
    private static class ScheduledTask {
        int ticksLeft;
        Runnable action;

        ScheduledTask(int ticksLeft, Runnable action) {
            this.ticksLeft = ticksLeft;
            this.action = action;
        }
    }
}

