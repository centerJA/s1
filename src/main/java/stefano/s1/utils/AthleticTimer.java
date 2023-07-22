package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import stefano.s1.S1;

import java.util.ArrayList;

public class AthleticTimer  {
    ArrayList<String> athleticPlayerList;
    Player player;

    private BukkitRunnable timerTask;

    private boolean isStopped;

    public static int athleticTime;

    private World world;

//    public AthleticTimer(Player player) {
//        athleticTime = 0;
//        this.world = Bukkit.getWorld("stefanovarentino");
//        this.player = player;
//        this.isStopped = false;
//    }
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
        timerTask.runTaskTimer(S1.getPlugin(S1.class), 0, 20);
        athleticTime = 0;
    }
    public void stopTimer(Player player) {
//        if (timerTask != null) {
//            timerTask.cancel();
//            timerTask = null;
//        }
        getTaskId(player).cancel();
    }

    public static BukkitTask getTaskId(Player player) {
        BukkitTask taskId = S1.getPlugin(S1.class).getServer().getScheduler().runTaskTimer(S1.getPlugin(S1.class), new Runnable() {
            @Override
            public void run() {
                if (athleticTime > 600) {

                    return;
                }
                player.setLevel(athleticTime);
                athleticTime++;
            }
        }, 0, 10);
        return taskId;
    }

//    @Override
//    public void run() {
//        if (isStopped) {
//            cancel();
//            return;
//        }
//        if (AthleticTime > 600) {
//            cancel();
//            return;
//        }
//        this.player.setLevel(AthleticTime);
//        AthleticTime++;
//    }
//    public void stopStopwatch() {
//        isStopped = true;
//    }
}
