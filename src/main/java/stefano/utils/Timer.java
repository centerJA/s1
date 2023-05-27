package stefano.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    private int nowTime;
    private World world;
    public Timer() {
        this.nowTime = 20;
        this.world = Bukkit.getWorld("stefanovarentino");

    }
    @Override
    public void run() {
        if (nowTime < 0) {
            cancel();
            return;
        }
        for (Player player: world.getPlayers()) {
            player.sendMessage(ChatColor.YELLOW + "<かくれんぼ> " + nowTime + "秒");
        }
        nowTime--;

    }
}
