package stefano.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class AthleticTimer extends BukkitRunnable {
    ArrayList<String> athleticPlayerList;

    public static int AthleticTime;

    private World world;

    public AthleticTimer() {
        AthleticTime = 0;
        this.world = Bukkit.getWorld("stefanovarentino");
    }
    @Override
    public void run() {
        if (AthleticTime > 600) {
            cancel();
            return;
        }
        for (Player player: world.getPlayers()) {
            player.setLevel(AthleticTime);
        }
        AthleticTime++;
    }
}
