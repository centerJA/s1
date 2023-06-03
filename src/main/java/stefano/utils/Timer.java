package stefano.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    private int nowTime;
    private Player player;
    private World world;
    public Timer(Player player) {
        this.nowTime = 20;
        this.world = Bukkit.getWorld("stefanovarentino");
        this.player = player;
    }
    @Override
    public void run() {
        if (nowTime < 0) {
            cancel();
            return;
        }
        player.sendMessage(ChatColor.YELLOW + "<pvp>" + nowTime + "秒");
        nowTime--;
    }
}
