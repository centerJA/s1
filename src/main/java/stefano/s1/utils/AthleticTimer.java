package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import stefano.s1.S1;

import java.util.ArrayList;
import java.util.HashMap;

public class AthleticTimer  {
    ArrayList<String> athleticPlayerList;
    Player player;

    private BukkitRunnable timerTask;

    private boolean isStopped;

    public static int athleticTime;

    private World world;

    public static HashMap<Player, BukkitTask> tasks = new HashMap<>();


    public void startTimer(Player player) {
        if (timerTask != null) {
            timerTask.cancel();
        }
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (athleticTime > 600) {
                    AthleticTimer.getTaskId(player).cancel();
                    return;
                }
                player.setLevel(athleticTime);
                athleticTime++;
            }
        };
        tasks.put(player, timerTask.runTaskTimer(S1.getPlugin(S1.class), 0, 20));
        athleticTime = 0;
    }
    public static void stopTimer(Player player) {
        if (tasks.get(player) != null) {
            tasks.get(player).cancel();
        }
    }

    public static BukkitTask getTaskId(Player player) {
        BukkitTask taskId = S1.getPlugin(S1.class).getServer().getScheduler().runTaskTimer(S1.getPlugin(S1.class), new Runnable() {
            @Override
            public void run() {
                if (athleticTime > 1200) {

                    return;
                }
                player.setLevel(athleticTime);
                athleticTime++;
            }
        }, 0, 10);
        return taskId;
    }

}
